package cat.udl.eps.entsoftarch.textannot.controller;

import cat.udl.eps.entsoftarch.textannot.repository.TagHierarchyRepository;
import cat.udl.eps.entsoftarch.textannot.repository.TagRepository;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@BasePathAwareController
public class TagHierarchyController {

    private TagHierarchyRepository tagHierarchyRepository;
    private TagRepository tagRepository;

    public TagHierarchyController(TagHierarchyRepository tagHierarchyRepository) {
        this.tagHierarchyRepository = tagHierarchyRepository;
    }

    @RequestMapping(value = "/quickTagHierarchyCreate", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity quickTagHierarchyCreate(@RequestBody Map<String, Object> body) throws IOException {


        return new ResponseEntity(HttpStatus.CREATED);
    }
}
