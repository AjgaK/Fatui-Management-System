package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Task in the system.
 * A task has a description, a deadline, a status, and can have multiple task reports.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Description is mandatory.")
    @Size(min = 1, max = 500)
    private String description;

    @NotNull(message = "Deadline date is mandatory.")
    private LocalDate deadline;

    @NotBlank(message = "Status is mandatory.")
    private String status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TaskReport> taskReports = new HashSet<>();
}
