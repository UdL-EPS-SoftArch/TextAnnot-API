package cat.udl.eps.entsoftarch.textannot.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MetadataTemplate {

    @Id @NotBlank
    private String name;

    @OneToMany
    private List<MetadataField> mtFs;

    @OneToMany
    private List<Sample> samples;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
