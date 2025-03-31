package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity representing an Agent in the system.
 * An agent can have various roles and may supervise other agents.
 */
@Entity
@Data
@NoArgsConstructor
@Builder
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Role is mandatory.")
    @Size(min = 1, max = 50)
    private String role;

    @Min(0)
    private int seniorYearsOfExperience;

    @Size(min = 1, max = 50)
    private String specialSkill;

    @OneToOne(mappedBy = "agent", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Employee employee;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AgentTypeAgent> agentTypeAgents = new HashSet<>();

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Agent> supervisedAgents = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Agent supervisor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servedHarbinger_id", nullable = false)
    @NotNull
    private Harbinger servedHarbinger;

    /**
     * Constructor for creating an Agent with specific details.
     *
     * @param id                    the ID of the agent
     * @param role                  the role of the agent
     * @param seniorYearsOfExperience the years of experience for senior agents
     * @param specialSkill          the special skill of the agent
     * @param employee              the associated employee
     * @param agentTypeAgents       the types associated with the agent
     * @param supervisedAgents      the agents supervised by this agent
     * @param supervisor            the supervisor of this agent
     * @param harbinger             the harbinger served by this agent
     */
    public Agent(Long id, String role, int seniorYearsOfExperience, String specialSkill, Employee employee, Set<AgentTypeAgent> agentTypeAgents, Set<Agent> supervisedAgents, Agent supervisor, Harbinger harbinger) {
        this.role = role;
        this.employee = employee;
        this.agentTypeAgents = agentTypeAgents;
        this.servedHarbinger = harbinger;
    }

    /**
     * Sets the types of the agent along with other attributes based on type.
     *
     * @param agentTypeAgents       the set of agent type connections
     * @param seniorYearsOfExperience the years of experience for senior agents
     * @param specialSkill          the special skill of the agent
     * @param supervisedAgents      the agents supervised by this agent
     * @param supervisor            the supervisor of this agent
     */
    public void setAgentTypeAgents(Set<AgentTypeAgent> agentTypeAgents, int seniorYearsOfExperience, String specialSkill, Set<Agent> supervisedAgents, Agent supervisor) {
        this.agentTypeAgents = agentTypeAgents;
        for(AgentTypeAgent agentTypeAgent : agentTypeAgents) {
            if(agentTypeAgent.getAgentType().getName() == "Senior Agent") {
                this.seniorYearsOfExperience = seniorYearsOfExperience;
                this.supervisedAgents = supervisedAgents;
            }
            if(agentTypeAgent.getAgentType().getName() == "Special Agent") {
                this.specialSkill = specialSkill;
            }
            if(agentTypeAgent.getAgentType().getName() == "Normal") {
                this.supervisor = supervisor;
            }
        }
    }

    /**
     * Validates the supervised agents, removing those not joined within the last year.
     */
    public void checkSupervised() {
        Set<Agent> validSupervisedAgents = new HashSet<>();
        LocalDate now = LocalDate.now();
        for (Agent agent : supervisedAgents) {
            if (agent.getJoiningDate() != null && agent.getJoiningDate().isAfter(now.minusDays(365))) {
                validSupervisedAgents.add(agent);
            } else {
                agent.setSupervisor(null);
            }
        }

        this.supervisedAgents = validSupervisedAgents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Gets the joining date of the agent from the associated employee.
     *
     * @return the joining date of the agent
     */
    public LocalDate getJoiningDate() {
        return employee.getJoiningDate();
    }
}
