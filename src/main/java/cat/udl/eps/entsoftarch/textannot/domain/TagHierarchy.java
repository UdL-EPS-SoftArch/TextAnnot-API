package cat.udl.eps.entsoftarch.textannot.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class TagHierarchy extends UriEntity<Integer> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer tagHierachyId;

    @NotBlank
    @Column(unique = true)
    private String name;

    @Override
    public Integer getId() {
        return this.tagHierachyId;
    }

}
