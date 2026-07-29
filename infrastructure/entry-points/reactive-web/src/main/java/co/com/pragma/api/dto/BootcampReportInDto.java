package co.com.pragma.api.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BootcampReportInDto(Long bootcampId, String name, String description, LocalDate launchDate,
        Integer durationInWeeks, Integer capabilityCount, Integer technologyCount, Integer enrolledPersonCount) {
}
