package cat.udl.eps.entsoftarch.textannot.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class MetadataTemplate {

    @Id @NotBlank
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime startDate;

    @OneToMany
    private List<MetadataField> mtFs;

    @OneToMany
    private List<Sample> samples;

    @OneToMany(mappedBy = "describedBy")
    private List<Sample> listOfSamples;


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

    public List<MetadataField> getMtFs() {
        return mtFs;
    }

    public void setMtFs(List<MetadataField> mtFs) {
        this.mtFs = mtFs;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public List<Sample> getListOfSamples() {
        return listOfSamples;
    }

    public void setListOfSamples(List<Sample> samples) {
        this.listOfSamples = samples;
    }

}
