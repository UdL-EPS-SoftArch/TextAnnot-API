package cat.udl.eps.entsoftarch.textannot.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class MetadataValue extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String value;

    public MetadataValue(){}

    public MetadataValue(String value){
        this.value = value;
    }

    //*CREATE RELATION WITH "METADATAFIELD".
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MetadataField values;
}
