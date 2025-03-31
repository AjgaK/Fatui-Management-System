package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Entity representing a many-to-many relationship between Agent and AgentType.
 * Links agents to their respective types, allowing for multiple types per agent.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentTypeAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agentType_id", nullable = false)
    @NotNull
    private AgentType agentType;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    @NotNull
    private Agent agent;
}
