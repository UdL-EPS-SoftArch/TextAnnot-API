package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class XmlSample extends Sample{

    private String content;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    public XmlSample() {}

    public XmlSample(String text, String content) {
        super(text);
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
