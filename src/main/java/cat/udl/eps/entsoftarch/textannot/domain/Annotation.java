package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cat.udl.eps.entsoftarch.textannot.domain.validator.RangeConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@RangeConstraint
public class Annotation extends UriEntity<Integer> {

    @Id
    @GeneratedValue
    private Integer id;

    @NonNull
    private Integer start;

    @NonNull
    private Integer end;

    private Boolean reviewed = false;
}
