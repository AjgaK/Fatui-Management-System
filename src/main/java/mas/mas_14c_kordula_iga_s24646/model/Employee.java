package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import mas.mas_14c_kordula_iga_s24646.repository.AgentRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an Employee in the system.
 * An employee can be associated with either an Agent or a Harbinger but not both simultaneously.
 */
@Entity
@Data
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Date of joining is mandatory.")
    private LocalDate joiningDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MissionEmployee> missionEmployees = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "harbinger_id")
    @ToString.Exclude
    private Harbinger harbinger;

    @OneToOne
    @JoinColumn(name = "agent _id")
    @ToString.Exclude
    private Agent agent;

    /**
     * Constructor for creating an Employee with specific details.
     *
     * @param id               the ID of the employee
     * @param joiningDate      the date the employee joined
     * @param missionEmployees the set of missions the employee is associated with
     * @param harbinger        the harbinger associated with the employee
     * @param agent            the agent associated with the employee
     */
    public Employee(Long id, LocalDate joiningDate, Set<MissionEmployee> missionEmployees, Harbinger harbinger, Agent agent) {
        this.joiningDate = joiningDate;
        this.missionEmployees = missionEmployees;
        if(agent == null && harbinger != null) {
            this.harbinger = harbinger;
        } else if(agent != null && harbinger == null) {
            this.agent = agent;
        } else if(agent != null && harbinger != null) {
            throw new IllegalArgumentException("Employee can't have 2 types at the same time.");
        }
    }

    /**
     * Changes the role of the employee to either a new Agent or a new Harbinger.
     *
     * @param newAgent     the new agent role to be assigned
     * @param newHarbinger the new harbinger role to be assigned
     */
    public void changeRole(Agent newAgent, Harbinger newHarbinger) {
        if(newAgent != null && newHarbinger != null) {
            throw new IllegalArgumentException("Employee can't have 2 types at the same time.");
        } else if(this.agent != null && newAgent != null) {
            throw new IllegalArgumentException("Employee already has this role.");
        } else if(this.harbinger != null && newHarbinger != null) {
            throw new IllegalArgumentException("Employee already has this role.");
        } else if(newAgent == null && newHarbinger == null){
            throw new IllegalArgumentException("Employee has to have a type.");
        }

        if(this.agent == null) {
            this.agent = newAgent;
            this.harbinger = null;
        } else {
            this.harbinger = newHarbinger;
            this.agent = null;
        }
    }
}
