package cat.udl.eps.entsoftarch.textannot.domain.validator;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeConstraintValidator implements ConstraintValidator<RangeConstraint, Annotation> {

    @Override
    public void initialize(RangeConstraint range) {
    }

    @Override
    public boolean isValid(Annotation annotation, ConstraintValidatorContext context) {
        return annotation.getStart() != null &&
                annotation.getEnd() != null &&
                0 <= annotation.getStart() &&
                annotation.getStart() < annotation.getEnd();
    }
}
