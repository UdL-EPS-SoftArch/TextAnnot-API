package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class XmlSample extends Sample {

    @NotNull
    @Column(length = 16 * 1024) // 16KB
    @Size(max = 16 * 1024)
    private String content;

    public void setContent(String content) {
        if (getText() == null) setText("");
        if (content.charAt(0) == '\uFEFF') // Remove invalid char
            this.content = content.substring(1);
        else
            this.content = content;
    }
}
