package cat.udl.eps.entsoftarch.textannot.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class MetadataValue extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Column(length = 512) // 512B
    @Size(max = 512)
    private String value;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private	Sample forA;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private	MetadataField valued;

    public MetadataValue(String value){
        this.value = value;
    }

    public MetadataValue() {
    }

    public String getFieldName() { return this.valued != null? this.valued.getName() : ""; }

    public String getFieldCategory() { return this.valued != null? this.valued.getCategory() : ""; }
}
