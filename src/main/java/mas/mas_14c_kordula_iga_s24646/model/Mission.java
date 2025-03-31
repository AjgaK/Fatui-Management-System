package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Mission in the system.
 * A mission has a start date, deadline, description, fund, and is associated with a region.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Start date is mandatory.")
    private LocalDate startDate;

    @NotNull(message = "Deadline date is mandatory.")
    private LocalDate deadline;

    @NotBlank(message = "Description is mandatory.")
    @Size(min = 1, max = 500)
    private String description;

    @Min(1)
    private double fund;

    private static double missionBudgetCap = 1000000000;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TaskReport> taskReports = new HashSet<>();

    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Resource> resources = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    @NotNull
    private Region region;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MissionEmployee> missionEmployees = new HashSet<>();

    /**
     * Calculates the total fund required for the mission based on the employees involved.
     *
     * @return the calculated fund amount
     */
    public double calculateFund() {
        double multiplier = 0.0;
        int harbingerCount = 0;
        for(MissionEmployee missionEmployee : missionEmployees) {
            Employee employee = missionEmployee.getEmployee();
            Harbinger harbinger = employee.getHarbinger();
            Agent agent = employee.getAgent();
            if(harbinger != null) {
                if(harbinger.getNumber() >= 5) {
                    multiplier += 0.8;
                } else {
                    multiplier += 0.4;
                }
                harbingerCount++;
            } else if(agent != null) {
                Set<AgentTypeAgent> agentTypeConns = agent.getAgentTypeAgents();
                for(AgentTypeAgent agentTypeConnection : agentTypeConns) {
                    if(agentTypeConnection.getAgentType().getName().equals("Special Agent")) {
                        multiplier += 0.1;
                    }
                    if(agentTypeConnection.getAgentType().getName().equals("Senior Agent")){
                        multiplier += 0.1;
                    }
                }
            }
        }
        if(harbingerCount > 2) {
            multiplier -= 0.2;
        }
        return 500000 * multiplier;
    }
}
