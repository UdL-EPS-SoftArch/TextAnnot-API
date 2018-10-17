package cat.udl.eps.entsoftarch.textannot.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagHierarchy extends UriEntity<Integer> {

    /**
     * Identifier of TagHierarchy needs to be unique, otherwise it will generate conflicts.
     */

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     *  The name given to the TagHierarchy. It can't be blank.
     *
     */

    @NotBlank
    @Column(unique = true)
    private String name;
}
