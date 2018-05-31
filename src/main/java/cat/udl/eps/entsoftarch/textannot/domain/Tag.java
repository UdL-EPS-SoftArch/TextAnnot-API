package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIdentityReference(alwaysAsId = true)
    private Tag parent;

    @OneToMany(mappedBy = "parent")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Tag> child = new ArrayList<>();

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private TagHierarchy definedIn;

//    @OneToMany(mappedBy = "uses")
//    @JsonIdentityReference(alwaysAsId = true)
//    private List<Annotation> usedIn = new ArrayList<>();


    public TagHierarchy getDefinedIn() {
        return definedIn;
    }

    public void setDefinedIn(TagHierarchy definedIn) {
        this.definedIn = definedIn;
    }

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag getParent() {
        return parent;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public List<Tag> getChild() {
        return child;
    }

    public void setChild(List<Tag> child) {
        this.child = child;
    }
}
