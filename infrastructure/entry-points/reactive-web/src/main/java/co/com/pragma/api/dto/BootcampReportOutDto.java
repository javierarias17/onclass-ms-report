package co.com.pragma.api.dto;

import java.time.Instant;
import java.time.LocalDate;

public record BootcampReportOutDto(String id, Long bootcampId, String name, String description, LocalDate launchDate,
        Integer durationInWeeks, Integer capabilityCount, Integer technologyCount, Integer enrolledPersonCount,
        Instant registeredAt) {
}
