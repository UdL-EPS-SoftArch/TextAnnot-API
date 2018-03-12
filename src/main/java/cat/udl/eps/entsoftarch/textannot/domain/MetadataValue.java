package cat.udl.eps.entsoftarch.textannot.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Data
public class MetadataValue extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String value;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private	Sample has;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private	MetadataField valued;

    public MetadataValue(){}

    public MetadataValue(String value){
        this.value = value;
    }

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MetadataField values;
}
