package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MetadataTemplate {

    @Id
    private String name;


    public MetadataTemplate(String name){
        this.name = name;
    }

    public MetadataTemplate(){
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
