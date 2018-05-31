package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityReference;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sample extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String text;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MetadataTemplate describedBy;

    @OneToMany(mappedBy = "forA")
    @JsonIdentityReference(alwaysAsId = true)
    private List<MetadataValue> has = new ArrayList<>();

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private TagHierarchy taggedBy;

    public TagHierarchy getTaggedBy() {
        return taggedBy;
    }

    public void setTaggedBy(TagHierarchy taggedBy) {
        this.taggedBy = taggedBy;
    }

    public Sample() { }

    public Sample(String text) {
        this.text=text;
    }

    public Integer getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text=text;
    }

    public MetadataTemplate getDescribedBy() { return describedBy; }

    public void setDescribedBy(MetadataTemplate describedBy) { this.describedBy = describedBy; }

    public List<MetadataValue> getHas() {
        return has;
    }

    public void setHas(List<MetadataValue> has) {
        this.has = has;
    }

    public String toString() {
        return this.text;
    }
}
