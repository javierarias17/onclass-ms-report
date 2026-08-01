package co.com.pragma.kafka.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

@Configuration
public class KafkaConfig {

    @Bean
    public ReceiverOptions<String, String> kafkaReceiverOptions(
            @Value(value = "${adapters.kafka.consumer.topic}") String topic,
            KafkaProperties kafkaProperties) throws UnknownHostException {
        // Set id based on hostname, customize here another properties
        kafkaProperties.setClientId(InetAddress.getLocalHost().getHostName());
        ReceiverOptions<String, String> basicReceiverOptions =
                ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public KafkaReceiver<String, String> kafkaReceiver(ReceiverOptions<String, String> kafkaReceiverOptions) {
        return KafkaReceiver.create(kafkaReceiverOptions);
    }
}
