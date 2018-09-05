package cat.udl.eps.entsoftarch.textannot.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
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
    private	MetadataField valued;

    public MetadataValue(){}

    public MetadataValue(String value){
        this.value = value;
    }

    @Override
    public Integer getId() { return id; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public Sample getForA() { return forA; }

    public void setForA(Sample forA) { this.forA = forA; }

    public MetadataField getValued() { return valued; }

    public void setValued(MetadataField valued) { this.valued = valued; }

    public String getFieldName() { return this.valued.getName(); }

    public String getFieldCategory() { return this.valued.getCategory(); }
}
