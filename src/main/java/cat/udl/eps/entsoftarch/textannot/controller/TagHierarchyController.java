package cat.udl.eps.entsoftarch.textannot.controller;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@BasePathAwareController
public class TagHierarchyController {

    private TagHierarchyRepository tagHierarchyRepository;
    private TagRepository tagRepository;

    public TagHierarchyController(TagHierarchyRepository tagHierarchyRepository, TagRepository tagRepository) {
        this.tagHierarchyRepository = tagHierarchyRepository;
        this.tagRepository = tagRepository;
    }

    @RequestMapping(value = "/quickTagHierarchyCreate", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity quickTagHierarchyCreate(@RequestBody Map<String, Object> body) throws ValidationException {
        if (isNameContentNullOrBlack(body) || body.get("roots") == null) {
            throw new ValidationException("TagHierarchy Object must contains defined name and root.");
        }

        List<Tag> treeHierarchy = new ArrayList<>();
        TagHierarchy tagHierarchy = new TagHierarchy();
        tagHierarchy.setName(body.get("name").toString());

        for (Map<String, Object> root: (List<Map<String, Object>> ) body.get("roots")) {
            createTag(root, null, tagHierarchy, treeHierarchy);
        }

        tagHierarchyRepository.save(tagHierarchy);
        for (Tag child: treeHierarchy) {
            tagRepository.save(child);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    private void createTag(Map<String, Object> root, Tag parent, TagHierarchy tagHierarchy, List<Tag> treeHierarchy) throws ValidationException {
        if (isNameContentNullOrBlack(root)) {
            throw new ValidationException("TagHierarchy son Objects must contains a defined name.");
        }
        Tag tag = new Tag(root.get("name").toString());
        tag.setParent(parent);
        tag.setTagHierarchy(tagHierarchy);

        if(treeHierarchy.stream().anyMatch((Tag t) -> t.getName().equals(tag.getName()))) {
            throw new ValidationException("Cycle found in TagHierarchy while adding Tag " + tag.getName());
        }

        treeHierarchy.add(tag);

        List<Map<String, Object>> children = (List<Map<String, Object>> )root.get("children");
        if (children == null)
            return;

        for (Map<String, Object> child: children) {
            createTag(child, tag, tagHierarchy, treeHierarchy);
        }
    }

    private boolean isNameContentNullOrBlack(Map<String, Object> body) {
        return body.get("name") == null || body.get("name") == "";
    }
}
