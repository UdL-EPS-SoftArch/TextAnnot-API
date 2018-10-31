package cat.udl.eps.entsoftarch.textannot.domain.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TagHierarchyConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagHierarchyConstraint {

    String message() default "Invalid tag hierarchy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
