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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Sample extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 16 * 1024) // 16KB
    @Size(max = 16 * 1024)
    private String text;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MetadataTemplate describedBy;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private TagHierarchy taggedBy;

    @OneToMany(mappedBy = "forA")
    @JsonIdentityReference(alwaysAsId = true)
    private List<MetadataValue> has = new ArrayList<>();

    public Sample(String text) {
        this.text=text;
    }

    public Sample() {
    }
}
