package co.com.pragma.kafka.consumer;

import co.com.pragma.kafka.consumer.dto.BootcampReportEventDto;
import co.com.pragma.kafka.consumer.mapper.BootcampReportEventMapper;
import co.com.pragma.usecase.registerbootcampreport.RegisterBootcampReportUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;
import tools.jackson.databind.json.JsonMapper;

@Component
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaReceiver<String, String> kafkaReceiver;
    private final RegisterBootcampReportUseCase registerBootcampReportUseCase;
    private final BootcampReportEventMapper bootcampReportEventMapper;
    private final JsonMapper jsonMapper = new JsonMapper();

    @EventListener(ApplicationStartedEvent.class)
    public Flux<Void> listenMessages() {
        return kafkaReceiver.receive()
                .publishOn(Schedulers.newBoundedElastic(Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE, Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, "kafka"))
                .flatMap(this::processRecord)
                .doOnError(error -> log.error("Error processing kafka record", error))
                .retry()
                .repeat();
    }

    private Mono<Void> processRecord(ReceiverRecord<String, String> record) {
        log.info("Record received {}", record.value());
        BootcampReportEventDto event = jsonMapper.readValue(record.value(), BootcampReportEventDto.class);
        return registerBootcampReportUseCase.execute(bootcampReportEventMapper.toRegisterBootcampReportCommand(event))
                .doOnSuccess(report -> record.receiverOffset().acknowledge())
                .then();
    }
}
