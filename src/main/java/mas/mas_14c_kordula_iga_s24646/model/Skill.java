package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a Skill in the system.
 * A skill is associated with a leader and has a name and description.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory.")
    @Size(min = 1, max = 500)
    private String name;

    @NotBlank(message = "Description is mandatory.")
    @Size(min = 1, max = 500)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leader_id", nullable = false)
    @NotNull
    private Leader leader;
}
