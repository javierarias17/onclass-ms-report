package co.com.pragma.usecase.registerbootcampreport;

import co.com.pragma.model.exceptions.FieldsValidationException;
import co.com.pragma.model.report.BootcampReport;
import co.com.pragma.model.report.command.RegisterBootcampReportCommand;
import co.com.pragma.model.report.gateways.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterBootcampReportUseCaseTest {

    private static final Long BOOTCAMP_ID = 10L;
    private static final String VALID_NAME = "Java Backend Bootcamp";
    private static final String VALID_DESCRIPTION = "Bootcamp de backend con Java";
    private static final LocalDate VALID_LAUNCH_DATE = LocalDate.of(2026, 8, 1);
    private static final Integer VALID_DURATION_IN_WEEKS = 12;
    private static final Integer VALID_CAPABILITY_COUNT = 2;
    private static final Integer VALID_TECHNOLOGY_COUNT = 3;
    private static final Integer NO_ENROLLED_PERSONS_YET = 0;

    @Mock
    private ReportRepository reportRepository;

    private RegisterBootcampReportUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegisterBootcampReportUseCase(reportRepository);
    }

    @Test
    void When_ReportDataIsValid_Expect_ReportToBeSavedWithRegisteredAtTimestamp() {
        // Arrange
        RegisterBootcampReportCommand command = validCommand();

        when(reportRepository.save(any(BootcampReport.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectNextMatches(report -> report.getBootcampId().equals(BOOTCAMP_ID)
                        && report.getName().equals(VALID_NAME)
                        && report.getCapabilityCount().equals(VALID_CAPABILITY_COUNT)
                        && report.getTechnologyCount().equals(VALID_TECHNOLOGY_COUNT)
                        && report.getEnrolledPersonCount().equals(NO_ENROLLED_PERSONS_YET))
                .verifyComplete();

        ArgumentCaptor<BootcampReport> captor = ArgumentCaptor.forClass(BootcampReport.class);
        verify(reportRepository).save(captor.capture());
        assertNotNull(captor.getValue().getRegisteredAt());
    }

    @Test
    void Expect_FieldsValidationException_When_BootcampIdIsNull() {
        // Arrange
        RegisterBootcampReportCommand command = new RegisterBootcampReportCommand(null, VALID_NAME, VALID_DESCRIPTION,
                VALID_LAUNCH_DATE, VALID_DURATION_IN_WEEKS, VALID_CAPABILITY_COUNT, VALID_TECHNOLOGY_COUNT,
                NO_ENROLLED_PERSONS_YET);

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(FieldsValidationException.class)
                .verify();

        verify(reportRepository, never()).save(any());
    }

    @Test
    void Expect_FieldsValidationException_When_NameIsBlank() {
        // Arrange
        RegisterBootcampReportCommand command = new RegisterBootcampReportCommand(BOOTCAMP_ID, " ", VALID_DESCRIPTION,
                VALID_LAUNCH_DATE, VALID_DURATION_IN_WEEKS, VALID_CAPABILITY_COUNT, VALID_TECHNOLOGY_COUNT,
                NO_ENROLLED_PERSONS_YET);

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(FieldsValidationException.class)
                .verify();

        verify(reportRepository, never()).save(any());
    }

    @Test
    void Expect_FieldsValidationException_When_LaunchDateIsNull() {
        // Arrange
        RegisterBootcampReportCommand command = new RegisterBootcampReportCommand(BOOTCAMP_ID, VALID_NAME, VALID_DESCRIPTION,
                null, VALID_DURATION_IN_WEEKS, VALID_CAPABILITY_COUNT, VALID_TECHNOLOGY_COUNT, NO_ENROLLED_PERSONS_YET);

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(FieldsValidationException.class)
                .verify();

        verify(reportRepository, never()).save(any());
    }

    @Test
    void Expect_FieldsValidationException_When_CapabilityCountIsNull() {
        // Arrange
        RegisterBootcampReportCommand command = new RegisterBootcampReportCommand(BOOTCAMP_ID, VALID_NAME, VALID_DESCRIPTION,
                VALID_LAUNCH_DATE, VALID_DURATION_IN_WEEKS, null, VALID_TECHNOLOGY_COUNT, NO_ENROLLED_PERSONS_YET);

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .expectError(FieldsValidationException.class)
                .verify();

        verify(reportRepository, never()).save(any());
    }

    private static RegisterBootcampReportCommand validCommand() {
        return new RegisterBootcampReportCommand(BOOTCAMP_ID, VALID_NAME, VALID_DESCRIPTION, VALID_LAUNCH_DATE,
                VALID_DURATION_IN_WEEKS, VALID_CAPABILITY_COUNT, VALID_TECHNOLOGY_COUNT, NO_ENROLLED_PERSONS_YET);
    }
}
