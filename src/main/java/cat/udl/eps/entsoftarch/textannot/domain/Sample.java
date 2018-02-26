package cat.udl.eps.entsoftarch.textannot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sample {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String text;

    public Sample(String text) {
        this.text=text;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text=text;
    }

}
