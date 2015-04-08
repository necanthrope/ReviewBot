package reviewbot.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Entity
@Table(name="subgenres")
public class Subgenre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subgenre;

    @Size(min = 1, max=30)
    private String name;

    @Column(name = "description", length = 20000)
    private String description;


    public Long getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(Long subgenre) {
        this.subgenre = subgenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
