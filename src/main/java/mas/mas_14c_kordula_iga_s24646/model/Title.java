package mas.mas_14c_kordula_iga_s24646.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a Title in the system.
 * A title is associated with a person and has a name.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory.")
    @Size(min = 1, max = 500)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @NotNull
    private Person person;
}
