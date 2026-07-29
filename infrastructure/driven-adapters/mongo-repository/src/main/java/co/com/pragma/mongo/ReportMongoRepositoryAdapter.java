package co.com.pragma.mongo;

import co.com.pragma.model.report.BootcampReport;
import co.com.pragma.model.report.gateways.ReportRepository;
import co.com.pragma.mongo.entity.ReportEntity;
import co.com.pragma.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReportMongoRepositoryAdapter extends
        AdapterOperations<BootcampReport, ReportEntity, String, ReportMongoRepository>
        implements ReportRepository {

    public ReportMongoRepositoryAdapter(ReportMongoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, BootcampReport.class));
    }
}
