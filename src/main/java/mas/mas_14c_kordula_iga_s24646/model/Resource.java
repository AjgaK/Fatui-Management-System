package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a Resource in the system.
 * A resource is associated with a mission and has a type and quantity.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Type is mandatory.")
    private String type;

    @Min(1)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    @NotNull
    private Mission mission;
}
