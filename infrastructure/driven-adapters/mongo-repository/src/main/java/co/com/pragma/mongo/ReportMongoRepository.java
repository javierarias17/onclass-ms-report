package co.com.pragma.mongo;

import co.com.pragma.mongo.entity.ReportEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ReportMongoRepository extends
        ReactiveMongoRepository<ReportEntity, String>,
        ReactiveQueryByExampleExecutor<ReportEntity> {
}
