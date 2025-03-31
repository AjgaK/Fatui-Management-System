package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a Leader in the system.
 * A leader has skills and is associated with a specific region.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Leader extends Person{

    @OneToMany(mappedBy = "leader", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Skill> skills = new HashSet<>();

    @NotNull(message = "Start date is mandatory.")
    private LocalDate startDate;

    @Nullable
    private LocalDate endDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false, updatable = false)
    private Region region;

    /**
     * Retrieves the most important skills of the leader.
     *
     * @return a string containing the most important skills
     */
    public String getMostImportantSkills() {
        String skillString = "";
        for(Skill skill : skills) {
            skillString += skill.getName() + "; ";
        }
        return skillString;
    }
}
