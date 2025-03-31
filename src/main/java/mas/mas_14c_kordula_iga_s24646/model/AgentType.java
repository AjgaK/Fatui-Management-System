package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a type of Agent.
 * An agent type defines the role and characteristics that an agent can have.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory.")
    @Size(min = 1, max = 500)
    private String name;

    @OneToMany(mappedBy = "agentType", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AgentTypeAgent> agentTypeAgents = new HashSet<>();
}
