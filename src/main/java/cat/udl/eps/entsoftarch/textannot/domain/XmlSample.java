package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class XmlSample extends Sample{

    private String text;

    @NotNull
    @Column(length = 16 * 1024) // 16KB
    @Size(max = 16 * 1024)
    private String content;

    public XmlSample() {}

    public XmlSample(String text, String content) {
        super(text);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content.charAt(0) == '\uFEFF') // Remove invalid char
            this.content = content.substring(1);
        else
            this.content = content;
    }
}
