package co.com.pragma.api;

import co.com.pragma.api.dto.BootcampReportInDto;
import co.com.pragma.api.mapper.BootcampReportDtoMapper;
import co.com.pragma.usecase.registerbootcampreport.RegisterBootcampReportUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler implements IHandlerDocs {

    private final RegisterBootcampReportUseCase registerBootcampReportUseCase;
    private final BootcampReportDtoMapper bootcampReportDtoMapper;

    @Override
    public Mono<ServerResponse> listenRegisterBootcampReport(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BootcampReportInDto.class)
                .defaultIfEmpty(BootcampReportInDto.builder().build())
                .map(bootcampReportDtoMapper::toRegisterBootcampReportCommand)
                .flatMap(registerBootcampReportUseCase::execute)
                .map(bootcampReportDtoMapper::toBootcampReportOutDto)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response));
    }
}
