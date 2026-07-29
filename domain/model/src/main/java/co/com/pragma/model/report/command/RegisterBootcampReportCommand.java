package co.com.pragma.model.report.command;

import java.time.LocalDate;

public record RegisterBootcampReportCommand(Long bootcampId, String name, String description, LocalDate launchDate,
        Integer durationInWeeks, Integer capabilityCount, Integer technologyCount, Integer enrolledPersonCount) {
}
