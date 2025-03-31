package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Region in the system.
 * A region has a name, element, description, and logo, and is associated with various entities like leaders, gods, missions, and harbingers.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory.")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "Element is mandatory.")
    @Pattern(regexp = "^(Pyro|Hydro|Anemo|Dendro|Geo|Cryo|Electro)$", message = "Element must be one of the predefined values")
    private String element;

    @NotBlank(message = "Description is mandatory.")
    @Size(min = 1, max = 500)
    private String description;

    @NotBlank(message = "Logo is mandatory.")
    private String logoPath;

    //composition
    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Leader> leaders = new HashSet<>();

    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<God> gods = new HashSet<>();

    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Mission> missions = new HashSet<>();

    @OneToMany(mappedBy = "regionFrom", cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Harbinger> harbingers = new HashSet<>();
}
