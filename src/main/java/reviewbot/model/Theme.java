package reviewbot.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Entity
@Table(name="themes")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long theme;

    @Size(min = 1, max=30)
    private String name;

    @Column(name = "description", length = 20000)
    private String description;


    public Long getTheme() {
        return theme;
    }

    public void setTheme(Long theme) {
        this.theme = theme;
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
