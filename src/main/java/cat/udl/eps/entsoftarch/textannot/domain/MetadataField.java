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
    String category, name, type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "valued")
    @JsonIdentityReference(alwaysAsId = true)
    private List<MetadataValue> values = new ArrayList<>();

    @ManyToOne
    private MetadataTemplate definedIn;

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
}
