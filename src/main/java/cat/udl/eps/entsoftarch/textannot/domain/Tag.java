package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends UriEntity<Integer> {

    /**
     * Identifier of Tag needs to be unique, otherwise it will generate conflicts.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *  The name given to the Tag. It can't be blank.
     *
     */

    @NotBlank
    private String name;

    /**
     * Linking Tag with TagHierarchy.
     */
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private TagHierarchy tagHierarchy;

    /**
     * Creating a parent child relationship.
     */
    @ManyToOne
    private Tag parent;


    public Tag(String name) {
        this.setName(name);
    }

    public Tag(String name, TagHierarchy tagHierarchy){
        this.setTagHierarchy(tagHierarchy);
        this.setName(name);
    }
}
