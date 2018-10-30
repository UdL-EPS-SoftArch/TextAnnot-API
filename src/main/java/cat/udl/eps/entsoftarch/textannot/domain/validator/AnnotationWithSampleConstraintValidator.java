package cat.udl.eps.entsoftarch.textannot.domain.validator;

import cat.udl.eps.entsoftarch.textannot.domain.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnnotationWithSampleConstraintValidator implements ConstraintValidator<AnnotationWithSampleConstraint, Annotation> {

    @Override
    public void initialize(AnnotationWithSampleConstraint range) {
    }

    @Override
    public boolean isValid(Annotation annotation, ConstraintValidatorContext constraintValidatorContext) {
        return annotation.getSample() != null;
    }
}
