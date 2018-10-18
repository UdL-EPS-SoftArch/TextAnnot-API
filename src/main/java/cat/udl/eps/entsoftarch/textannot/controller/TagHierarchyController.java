package cat.udl.eps.entsoftarch.textannot.controller;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.exception.TagHierarchyValidationException;
import cat.udl.eps.entsoftarch.textannot.exception.TagTreeException;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public PersistentEntityResource quickTagHierarchyCreate(
            @RequestBody Map<String,Object> body,
            PersistentEntityResourceAssembler resourceAssembler) throws TagHierarchyValidationException {
        if (isNameContentNullOrBlack(body) || body.get("roots") == null) {
            throw new TagHierarchyValidationException();
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

        return resourceAssembler.toResource(tagHierarchy);
    }

    private void createTag(Map<String, Object> root, Tag parent, TagHierarchy tagHierarchy, List<Tag> treeHierarchy) throws TagHierarchyValidationException {
        if (isNameContentNullOrBlack(root)) {
            throw new TagHierarchyValidationException();
        }
        Tag tag = new Tag(root.get("name").toString());
        tag.setParent(parent);
        tag.setTagHierarchy(tagHierarchy);

        if(treeHierarchy.stream().anyMatch((Tag t) -> t.getName().equals(tag.getName()))) {
            throw new TagTreeException();
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