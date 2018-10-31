package cat.udl.eps.entsoftarch.textannot.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TagHierarchyJson {
    private String name;
    private List<TagJson> roots;
}