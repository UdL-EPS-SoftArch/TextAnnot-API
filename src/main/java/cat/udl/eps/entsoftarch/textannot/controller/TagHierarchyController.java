package cat.udl.eps.entsoftarch.textannot.controller;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import cat.udl.eps.entsoftarch.textannot.exception.TagHierarchyDuplicateException;
import cat.udl.eps.entsoftarch.textannot.exception.TagHierarchyValidationException;
import cat.udl.eps.entsoftarch.textannot.exception.TagTreeException;
import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@BasePathAwareController
public class TagHierarchyController {

    private TagHierarchyRepository tagHierarchyRepository;
    private TagRepository tagRepository;

    public TagHierarchyController(TagHierarchyRepository tagHierarchyRepository, TagRepository tagRepository) {
        this.tagHierarchyRepository = tagHierarchyRepository;
        this.tagRepository = tagRepository;
    }

    @PostMapping(value = "/quickTagHierarchyCreate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PersistentEntityResource quickTagHierarchyCreate(
            @RequestBody TagHierarchyJson body,
            PersistentEntityResourceAssembler resourceAssembler) {

        if (isNullOrEmpty(body.getName()))
            throw new TagHierarchyValidationException();


        if (tagHierarchyRepository.findByName(body.getName()).isPresent())
            throw new TagHierarchyDuplicateException();

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

    @PostMapping(value = "/quickTagHierarchyCreate", consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PersistentEntityResource quickTagHierarchyCreateCSV(
        @RequestParam("tagHierarchy") String tagHierarchyName,
        ServletServerHttpRequest request,
        PersistentEntityResourceAssembler resourceAssembler) throws IOException {

        if (isNullOrEmpty(tagHierarchyName))
            throw new TagHierarchyValidationException();

        if (tagHierarchyRepository.findByName(tagHierarchyName).isPresent())
            throw new TagHierarchyDuplicateException();

        HashMap<String, Tag> processedTags = new HashMap<>();

        TagHierarchy tagHierarchy = new TagHierarchy();
        tagHierarchy.setName(tagHierarchyName);

        CSVFormat excelCSV = CSVFormat.newFormat(';').withRecordSeparator('\n').withFirstRecordAsHeader();
        CSVParser parser = CSVParser.parse(request.getBody(), StandardCharsets.UTF_8, excelCSV);
        parser.getRecords().forEach(record -> {
            Tag parent = null;
            //TODO: currently ignoring last column with tagging examples
            for(int i = 0; i < record.size()-1; i++) {
                String tagName = record.get(i);
                if (isNullOrEmpty(tagName))
                    continue;
                if (!processedTags.containsKey(tagName)) {
                    Tag tag = new Tag(tagName);
                    tag.setParent(parent);
                    tag.setTagHierarchy(tagHierarchy);
                    processedTags.put(tagName, tag);
                    parent = tag;
                }
                else {
                    parent = processedTags.get(tagName);
                }
            }
        });
        tagHierarchyRepository.save(tagHierarchy);
        tagRepository.saveAll(processedTags.values());
        return resourceAssembler.toResource(tagHierarchy);
    }
}
