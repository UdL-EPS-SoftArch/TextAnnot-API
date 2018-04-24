package cat.udl.eps.entsoftarch.textannot.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
public class MetadataTemplate extends UriEntity<String> {

    @Id @NotBlank
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDate;


    @OneToMany(mappedBy = "definedIn")
    private List<MetadataField> defines;


    @OneToMany(mappedBy = "describedBy")
    private List<Sample> describes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public List<MetadataField> getDefines() {
        return defines;
    }

    public void setDefines(List<MetadataField> defines) {
        this.defines = defines;
    }

    public List<Sample> getDescribes() {
        return describes;
    }

    public void setDescribes(List<Sample> describes) {
        this.describes = describes;
    }

    @Override
    public String getId() {
        return name;
    }
}
