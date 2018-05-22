package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Tag extends UriEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    public Tag(){}

    public Tag(String name){
        this.name = name;
    }

    @Override
    public Integer getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }


}
