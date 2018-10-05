package cat.udl.eps.entsoftarch.textannot.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MetadataValue extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String value;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private	Sample forA;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private	MetadataField values;

    public MetadataValue(){}

    public MetadataValue(String value){
        this.value = value;
    }

    @Override
    public Integer getId() { return id; }

    public String getFieldName() { return this.values != null? this.values.getName() : ""; }

    public String getFieldCategory() { return this.values != null? this.values.getCategory() : ""; }

}
