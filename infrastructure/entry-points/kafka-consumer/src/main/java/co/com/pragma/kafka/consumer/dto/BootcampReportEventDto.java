package co.com.pragma.kafka.consumer.dto;

import java.time.LocalDate;

public record BootcampReportEventDto(Long bootcampId, String name, String description, LocalDate launchDate,
        Integer durationInWeeks, Integer capabilityCount, Integer technologyCount, Integer enrolledPersonCount) {
}
