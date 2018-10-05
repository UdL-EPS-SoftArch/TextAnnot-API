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
    String name, type;

    String category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private MetadataTemplate definedAt;

    @Override
    public Integer getId() {
        return id;
    }

    public MetadataField(){
        this.name = "";
    }

    public MetadataField(String name, String type){
        this.name = name;
        this.type = type;
    }
}
