package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class XmlSample extends Sample {

    private final static int textSize = 16 * 1024;// 16KB

    /**
     * The content contained in the XmlSample. It is limited to 16KB and it can't be null.
     */
    @NotNull
    @Column(length = textSize)
    @Size(max = textSize)
    private String content;

    /**
     * Sets the content of the XmlSample. If text is null sets empty content.
     * If first character is invalid, it removes it.
     * @param content
     */
    public void setContent(String content) {
        if (getText() == null) setText("");
        if (content.charAt(0) == '\uFEFF') // Remove invalid char
            this.content = content.substring(1);
        else
            this.content = content;
    }
}
