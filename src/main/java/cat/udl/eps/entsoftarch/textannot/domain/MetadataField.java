package cat.udl.eps.entsoftarch.textannot.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MetadataField extends UriEntity<Integer> {

    @NotBlank
    /**
     * The String name indicates the name of a metadata field and must not be blank.
     * The String type indicates the kind of a metadata field and must not be blank.
     */
    String name="", type;

    /**
     * The String category indicates the category of a metadata field.
     */
    String category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * Identifier of annotation needs to be unique, otherwise it will generate conflicts.
     */
    private Integer id;

    @ManyToOne
    /**
     * Linking MetadataField with Metadata Template.
     */
    private MetadataTemplate definedAt;

    public MetadataField() {
        this.name = "";
    }

    public MetadataField(String name, String type){
        this.name = name;
        this.type = type;
    }
}
