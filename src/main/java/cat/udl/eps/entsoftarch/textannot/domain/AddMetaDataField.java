package cat.udl.eps.entsoftarch.textannot.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;


@Entity
public class AddMetaDataField extends UriEntity<Long> {

    @NotBlank
    String name, type;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Override
    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
