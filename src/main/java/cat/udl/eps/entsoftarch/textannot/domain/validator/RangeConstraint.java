package cat.udl.eps.entsoftarch.textannot.domain.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = RangeConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RangeConstraint {
    String message() default "Invalid range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
