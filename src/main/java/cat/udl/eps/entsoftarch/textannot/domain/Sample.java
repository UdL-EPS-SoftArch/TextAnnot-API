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
    
    public Sample() { }
    
    /**
     * Constructor for the class.
     * @param text the text of the sample
     */
    public Sample(String text) {
        this.text=text;
    }

    /**
     * Returns the ID for this entity.
     * @return the id for this entity
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the text of this sample.
     * @return the text of this sample
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text of this sample.
     * @param text the text for this sample.
     */
    public void setText(String text) {
        this.text=text;
    }
    
    /**
     * Returns the string equivalent of this entity.
     * @return the string equivalent of this entity.
     */
    public String toString() {
        return this.text;
    }


}
