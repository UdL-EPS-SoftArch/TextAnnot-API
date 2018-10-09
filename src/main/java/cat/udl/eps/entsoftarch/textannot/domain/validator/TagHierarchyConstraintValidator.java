package cat.udl.eps.entsoftarch.textannot.domain.validator;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagHierarchyConstraintValidator implements ConstraintValidator <TagHieratchyConstraint, Tag> {

    public void initialize(TagHierarchyConstraint tag) {
    }

    @Override
    public boolean isValid(Tag tag, ConstraintValidatorContext context) {
        return tag.getParent() == null || tag.getDefinedIn() != null &&
                tag.getParent().getDefinedIn() != null &&
                tag.getDefinedIn().equals(tag.getParent().getDefinedIn());
    }
}
