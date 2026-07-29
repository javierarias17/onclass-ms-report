package co.com.pragma.usecase.registerbootcampreport;

import co.com.pragma.model.common.FieldConstants;
import co.com.pragma.model.common.ValidationMessageConstants;
import co.com.pragma.model.common.validator.FieldValidator;
import co.com.pragma.model.exceptions.FieldsValidationException;
import co.com.pragma.model.report.BootcampReport;
import co.com.pragma.model.report.command.RegisterBootcampReportCommand;
import co.com.pragma.model.report.gateways.ReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RegisterBootcampReportUseCase {

    private final ReportRepository reportRepository;

    public Mono<BootcampReport> execute(RegisterBootcampReportCommand command) {
        Map<String, String> errors = collectFieldFormatErrors(command);

        if (!errors.isEmpty())
            return Mono.error(new FieldsValidationException(errors));

        return reportRepository.save(BootcampReport.builder()
                .bootcampId(command.bootcampId())
                .name(command.name())
                .description(command.description())
                .launchDate(command.launchDate())
                .durationInWeeks(command.durationInWeeks())
                .capabilityCount(command.capabilityCount())
                .technologyCount(command.technologyCount())
                .enrolledPersonCount(command.enrolledPersonCount())
                .registeredAt(Instant.now())
                .build());
    }

    private Map<String, String> collectFieldFormatErrors(RegisterBootcampReportCommand command) {
        Map<String, String> errors = new LinkedHashMap<>();
        FieldValidator.validateNotNull(command.bootcampId(), FieldConstants.BOOTCAMP_ID,
                ValidationMessageConstants.MSG_BOOTCAMP_ID_REQUIRED, errors);
        FieldValidator.validateNotBlank(command.name(), FieldConstants.NAME,
                ValidationMessageConstants.MSG_NAME_REQUIRED, errors);
        FieldValidator.validateNotBlank(command.description(), FieldConstants.DESCRIPTION,
                ValidationMessageConstants.MSG_DESCRIPTION_REQUIRED, errors);
        FieldValidator.validateNotNull(command.launchDate(), FieldConstants.LAUNCH_DATE,
                ValidationMessageConstants.MSG_LAUNCH_DATE_REQUIRED, errors);
        FieldValidator.validateNotNull(command.durationInWeeks(), FieldConstants.DURATION_IN_WEEKS,
                ValidationMessageConstants.MSG_DURATION_IN_WEEKS_REQUIRED, errors);
        FieldValidator.validateNotNull(command.capabilityCount(), FieldConstants.CAPABILITY_COUNT,
                ValidationMessageConstants.MSG_CAPABILITY_COUNT_REQUIRED, errors);
        FieldValidator.validateNotNull(command.technologyCount(), FieldConstants.TECHNOLOGY_COUNT,
                ValidationMessageConstants.MSG_TECHNOLOGY_COUNT_REQUIRED, errors);
        FieldValidator.validateNotNull(command.enrolledPersonCount(), FieldConstants.ENROLLED_PERSON_COUNT,
                ValidationMessageConstants.MSG_ENROLLED_PERSON_COUNT_REQUIRED, errors);
        return errors;
    }
}
