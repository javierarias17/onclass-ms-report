package co.com.pragma.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BootcampReport {
    private String id;
    private Long bootcampId;
    private String name;
    private String description;
    private LocalDate launchDate;
    private Integer durationInWeeks;
    private Integer capabilityCount;
    private Integer technologyCount;
    private Integer enrolledPersonCount;
    private Instant registeredAt;
}
