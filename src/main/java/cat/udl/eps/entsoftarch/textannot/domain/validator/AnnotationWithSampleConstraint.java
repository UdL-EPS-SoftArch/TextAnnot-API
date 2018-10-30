package cat.udl.eps.entsoftarch.textannot.domain.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = AnnotationWithSampleConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationWithSampleConstraint {
    String message() default "Annotation without sample";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
