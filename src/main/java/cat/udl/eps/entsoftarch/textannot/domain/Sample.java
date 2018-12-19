package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Sample extends UriEntity<Integer>{

    private final static int textSize = 16 * 1024; // 16KB

    /**
     * Identifier of sample needs to be unique, otherwise it will generate conflicts.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    /**
     * The text contained in the Sample. It is limited to 16KB and it can't be null.
     */
    @NotNull
    @Column(length = textSize)
    @Size(max = textSize)
    private String text;


    /**
     * Linking Sample with MetadataTemplate;
     */
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MetadataTemplate describedBy;


    /**
     * Linking Sample with TagHierarchy.
     */
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private TagHierarchy taggedBy;

    public Sample(String text) {
        this.text=text;
    }

    public Sample() {
    }
}
