package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Sample extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(length = 16 * 1024) // 16KB
    @Size(max = 16 * 1024)
    private String text;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MetadataTemplate describedBy;

    @OneToMany(mappedBy = "forA")
    @JsonIdentityReference(alwaysAsId = true)
    private List<MetadataValue> has = new ArrayList<>();

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
        return "Sample " + this.id + " text: " + this.text;
    }
}
