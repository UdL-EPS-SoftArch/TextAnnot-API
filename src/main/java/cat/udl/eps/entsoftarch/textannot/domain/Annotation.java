package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class Annotation extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private Integer start;

    @NonNull
    private Integer end;

    @NonNull
    private Sample annotated;

    private Boolean reviewed;

    public Annotation() {
    }


    @Override
    public Integer getId() {
        return this.id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Boolean getReviewed() {
        return reviewed;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    public void setSample(Sample annotated) { this.annotated = annotated; }

    public Sample getSample() { return this.annotated; }

    @Override
    public String toString() {
        return "Annotation{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", reviewed=" + reviewed +
                '}';
    }
}
