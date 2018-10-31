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
import java.util.Optional;

@BasePathAwareController
public class TagHierarchyController {

    private TagHierarchyRepository tagHierarchyRepository;
    private TagRepository tagRepository;

    public TagHierarchyController(TagHierarchyRepository tagHierarchyRepository, TagRepository tagRepository) {
        this.tagHierarchyRepository = tagHierarchyRepository;
        this.tagRepository = tagRepository;
    }

    @PostMapping("/quickTagHierarchyCreate")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PersistentEntityResource quickTagHierarchyCreate(
            @RequestBody TagHierarchyJson body,
            PersistentEntityResourceAssembler resourceAssembler) {

        if (isNullOrEmpty(body.getName()))
            throw new TagHierarchyValidationException();

        List<Tag> treeHierarchy = new ArrayList<>();

        TagHierarchy tagHierarchy = new TagHierarchy();
        tagHierarchy.setName(body.getName());

        Optional.ofNullable(body.getRoots())
            .orElseThrow(TagHierarchyValidationException::new)
            .forEach(root -> createTag(root, null, tagHierarchy, treeHierarchy));

        tagHierarchyRepository.save(tagHierarchy);
        treeHierarchy.forEach(tagRepository::save);

        return resourceAssembler.toResource(tagHierarchy);
    }

    private void createTag(TagJson tagJson, Tag parent, TagHierarchy tagHierarchy, List<Tag> treeHierarchy) {
        if (isNullOrEmpty(tagJson.getName()))
            throw new TagHierarchyValidationException();

        Tag tag = new Tag(tagJson.getName());
        tag.setParent(parent);
        tag.setTagHierarchy(tagHierarchy);

        if(treeHierarchy.stream().anyMatch(t -> t.getName().equals(tag.getName())))
            throw new TagTreeException();

        treeHierarchy.add(tag);

        Optional.ofNullable(tagJson.getChildren())
            .ifPresent(children ->
                    children.forEach(child -> createTag(child, tag, tagHierarchy, treeHierarchy)));
    }

    private boolean isNullOrEmpty(String name) {
        return name == null || name.isEmpty();
    }
}
