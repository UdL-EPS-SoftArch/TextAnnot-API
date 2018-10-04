package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MetadataField extends UriEntity<Integer> {

    @NotBlank
    String name="", type;

    String category;

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

    public MetadataField() {
    }

    public MetadataField(String name, String type){
        this.name = name;
        this.type = type;

    }
}
