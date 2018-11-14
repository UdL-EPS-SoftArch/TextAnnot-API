package cat.udl.eps.entsoftarch.textannot.controller;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.exception.TagHierarchyValidationException;
import cat.udl.eps.entsoftarch.textannot.exception.TagTreeException;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @RequestMapping("/tagHierarchies/{id}/tags")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TagHierarchyJson tagHierarchyDetail(@PathVariable("id") Integer id) {

        Optional<TagHierarchy> tagHierarchyOptional = tagHierarchyRepository.findById(id);

        TagHierarchy tagHierarchy = tagHierarchyOptional
                .orElseThrow(ResourceNotFoundException::new);

        List<Tag> roots =
                tagRepository.findByTagHierarchy(tagHierarchy).stream()
                        .filter(this::isRoot)
                        .collect(Collectors.toList());

        TagHierarchyJson tagHierarchyJson = new TagHierarchyJson(tagHierarchy);
        tagHierarchyJson.setRoots(roots.stream().map(TagJson::new).collect(Collectors.toList()));

        tagHierarchyJson.getRoots().forEach(this::setChildren);
        return tagHierarchyJson;
    }

    private void setChildren(TagJson root) {
        List<TagJson> children =
                tagRepository.findByParentName(root.getName())
                        .stream()
                        .map(TagJson::new)
                        .collect(Collectors.toList());

        root.getChildren().addAll(children);

        children.forEach(this::setChildren);
    }

    private boolean isRoot(Tag tag) {
        return tag.getParent() == null;
    }

    @Data
    public static class TagHierarchyJson {
        private String name;
        private List<TagJson> roots;

        TagHierarchyJson() {}

        TagHierarchyJson(TagHierarchy tagHierarchy) {
            this.name = tagHierarchy.getName();
        }
    }

    @Data
    public static class TagJson {
        private String name;
        private List<TagJson> children;

        TagJson() {}

        TagJson(Tag tag) {
            this.name = tag.getName();
            children = new ArrayList<>();
        }
    }
}
