package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class MetadataField extends UriEntity<Integer> {

    @NotBlank
    String name, type;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "valued")
    @JsonIdentityReference(alwaysAsId = true)
    private List<MetadataValue> values = new ArrayList<>();

    @Override
    public Integer getId() {
        return id;
    }

    public MetadataField(){
        this.name="";
    }

    public MetadataField(String name, String type){
        this.name = name;
        this.type = type;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MetadataValue> getValues() {
        return values;
    }

    public void setValues(List<MetadataValue> values) {
        this.values = values;
    }
}
