package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TagHierarchy extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "taggedBy")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Sample> tags = new ArrayList<>();

    @OneToMany(mappedBy = "definedIn")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Tag> defines = new ArrayList<>();


    public List<Sample> getTags() {
        return tags;
    }

    public void setTags(List<Sample> tags) {
        this.tags = tags;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getDefines() {
        return defines;
    }

    public void setDefines(List<Tag> defines) {
        this.defines = defines;
    }
}
