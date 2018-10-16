package cat.udl.eps.entsoftarch.textannot.domain.validator;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagHierarchyConstraintValidator implements ConstraintValidator <TagHierarchyConstraint, Tag> {

    public void initialize(TagHierarchyConstraint tag) {
    }

    @Override
    public boolean isValid(Tag tag, ConstraintValidatorContext context) {
        return tag.getParent() == null || tag.getTagHierarchy() != null &&
                tag.getParent().getTagHierarchy() != null &&
                tag.getTagHierarchy().equals(tag.getParent().getTagHierarchy());
    }
}
