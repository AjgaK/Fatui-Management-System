package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity representing a God in the system.
 * A god has an element, age, and is associated with a specific region.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class God extends Person {

    @NotNull(message = "Element cannot be null.")
    @Pattern(regexp = "^(Pyro|Hydro|Anemo|Dendro|Geo|Cryo|Electro)$", message = "Element must be one of the predefined values")
    private String element;

    @Min(1)
    private int age;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    @NotNull
    private Region region;

    @OneToMany(mappedBy = "god", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Title> titles = new HashSet<>();

    /**
     * Retrieves the most important titles of the god.
     *
     * @return a string containing the most important titles
     */
    public String getMostImportantTitles() {
        String titleString = "";
        for(Title title : titles) {
            titleString += title.getName() + "; ";
        }
        return titleString;
    }
}
