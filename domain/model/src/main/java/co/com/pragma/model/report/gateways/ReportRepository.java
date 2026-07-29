package co.com.pragma.model.report.gateways;

import co.com.pragma.model.report.BootcampReport;
import reactor.core.publisher.Mono;

public interface ReportRepository {

    Mono<BootcampReport> save(BootcampReport report);
}
