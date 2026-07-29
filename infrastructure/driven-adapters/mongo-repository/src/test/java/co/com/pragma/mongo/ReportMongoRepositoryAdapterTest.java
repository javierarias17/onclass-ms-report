package co.com.pragma.mongo;

import co.com.pragma.model.report.BootcampReport;
import co.com.pragma.mongo.entity.ReportEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportMongoRepositoryAdapterTest {

    private static final Long BOOTCAMP_ID = 10L;

    @Mock
    private ReportMongoRepository repository;

    @Mock
    private ObjectMapper mapper;

    private ReportMongoRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new ReportMongoRepositoryAdapter(repository, mapper);
    }

    @Test
    void When_ReportIsSaved_Expect_RepositoryToBeCalledAndMappedToDomain() {
        // Arrange
        BootcampReport report = BootcampReport.builder()
                .bootcampId(BOOTCAMP_ID)
                .name("Java Backend Bootcamp")
                .description("Bootcamp de backend con Java")
                .launchDate(LocalDate.of(2026, 8, 1))
                .durationInWeeks(12)
                .capabilityCount(2)
                .technologyCount(3)
                .enrolledPersonCount(0)
                .registeredAt(Instant.now())
                .build();
        ReportEntity entity = ReportEntity.builder().bootcampId(BOOTCAMP_ID).build();
        ReportEntity savedEntity = ReportEntity.builder().id("mongo-id").bootcampId(BOOTCAMP_ID).build();
        BootcampReport savedReport = BootcampReport.builder().id("mongo-id").bootcampId(BOOTCAMP_ID).build();

        when(mapper.map(any(BootcampReport.class), eq(ReportEntity.class))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(mapper.map(savedEntity, BootcampReport.class)).thenReturn(savedReport);

        // Act & Assert
        StepVerifier.create(adapter.save(report))
                .expectNextMatches(result -> result.equals(savedReport))
                .verifyComplete();
    }
}
