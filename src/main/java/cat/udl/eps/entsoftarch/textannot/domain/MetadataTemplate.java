package cat.udl.eps.entsoftarch.textannot.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
public class MetadataTemplate extends UriEntity<String> {

    @Id @NotBlank
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDate;


    @OneToMany(mappedBy = "definedAt")
    private List<MetadataField> defines;

    @Override
    public String getId() {
        return this.name;
    }
}
