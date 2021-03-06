package reviewbot.entity.metadata;

import javax.persistence.*;

/**
 * Created by jtidwell on 4/24/2015.
 */
@Entity
@Table(name = "misc")
public class Misc {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "misc")
    private Integer misc;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description", columnDefinition="text")
    private String description;

    public Integer getMisc() {
        return misc;
    }

    public void setMisc(Integer misc) {
        this.misc = misc;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Misc that = (Misc) o;

        if (misc != that.misc) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = misc;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
