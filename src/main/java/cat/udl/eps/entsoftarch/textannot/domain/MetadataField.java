package cat.udl.eps.entsoftarch.textannot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Data
@Entity
public class MetadataField extends UriEntity<Integer> {

    @NotBlank
    String name, type;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


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

}
