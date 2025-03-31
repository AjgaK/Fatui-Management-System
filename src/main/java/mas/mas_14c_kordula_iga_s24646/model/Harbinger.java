package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Entity representing a Harbinger in the system.
 * A harbinger has a unique number, title, and is associated with a specific region.
 */
@Entity
@Data
@NoArgsConstructor
@Builder
public class Harbinger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is mandatory.")
    @Size(min = 1, max = 50)
    private String title;

    @Min(1)
    @Max(11)
    private int number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "regionFrom_id", nullable = false)
    @NotNull
    @EqualsAndHashCode.Exclude
    private Region regionFrom;

    private int performancePoints;

    public static Map<Integer, Harbinger> numbers = new HashMap<>();

    @OneToOne(mappedBy = "harbinger", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Employee employee;

    @OneToMany(mappedBy = "servedHarbinger", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Agent> servingAgents = new HashSet<>();

    /**
     * Constructor for creating a Harbinger with specific details.
     *
     * @param id               the ID of the harbinger
     * @param title            the title of the harbinger
     * @param number           the unique number of the harbinger
     * @param regionFrom       the region the harbinger is from
     * @param performancePoints the performance points of the harbinger
     * @param employee         the employee associated with the harbinger
     * @param servingAgents    the agents serving the harbinger
     */
    public Harbinger(Long id, String title, int number, Region regionFrom, int performancePoints, Employee employee, Set<Agent> servingAgents) {
        this.title = title;
        setNumber(number);
        this.regionFrom = regionFrom;
        this.performancePoints = performancePoints;
        this.employee = employee;
        this.servingAgents = servingAgents;
    }

    /**
     * Gets the joining date of the harbinger from the associated employee.
     *
     * @return the joining date of the harbinger
     */
    public LocalDate getJoiningDate() {
        return employee.getJoiningDate();
    }

    /**
     * Sets the unique number of the harbinger.
     *
     * @param newNumber the new unique number for the harbinger
     * @throws IllegalArgumentException if the number is already in use
     */
    public void setNumber(int newNumber) {
        if(numbers.containsKey(newNumber)) {
            throw new IllegalArgumentException("Harbinger's number must be unique.");
        }
        this.number = newNumber;
        numbers.put(newNumber, this);
    }

    /**
     * Requests funds based on the harbinger's number.
     *
     * @return the amount of funds requested
     */
    public double requestFunds() {
        if(number >= 5) {
            return 300000;
        } else {
            return 100000;
        }
    }
}
