package cat.udl.eps.entsoftarch.textannot.domain;

import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class MetadataTemplate extends UriEntity<String> {

    @Id @NotBlank
    /**
     * Identifier of annotation needs to be unique, otherwise it will generate conflicts.
     * It must not be blank.
     */
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    /**
     * Indicates the date when the metadata template starts.
     */
    private ZonedDateTime startDate;

    @Override
    public String getId() {
        return this.name;
    }
}
