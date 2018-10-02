package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.NonNull;
import javax.persistence.ManyToOne;

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


    @ManyToOne
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

    public void setAnnotated(Sample annotated) { this.annotated = annotated; }

    public Sample getAnnotated(){ return this.annotated; }


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
