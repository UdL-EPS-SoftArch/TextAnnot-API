package cat.udl.eps.entsoftarch.textannot.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class MetadataTemplate {

    @Id @NotBlank
    private String name;

    @OneToMany(mappedBy = "describedBy")
    private List<Sample> listOfSamples;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sample> getListOfSamples() {
        return listOfSamples;
    }

    public void setListOfSamples(List<Sample> samples) {
        this.listOfSamples = samples;
    }

}
