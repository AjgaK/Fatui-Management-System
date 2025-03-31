package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the relationship between an Employee and a Mission.
 * Each MissionEmployee links an employee to a specific mission.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    @NotNull
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @NotNull
    private Employee employee;
}
