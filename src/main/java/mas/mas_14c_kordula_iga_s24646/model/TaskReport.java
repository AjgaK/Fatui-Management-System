package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing a Task Report in the system.
 * A task report is associated with a mission and a task and includes details such as the start date and additional details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nullable
    private LocalDate startDate;
    @Nullable
    private String details;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    @NotNull
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull
    private Task task;
}
