package reviewbot.entity.metadata;

import javax.persistence.*;

/**
 * Created by jtidwell on 4/24/2015.
 */
@Entity
@Table(name = "formats")
public class Format {

    @Id
    @Column(name = "format")
    private int format;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description", columnDefinition="text")
    private String description;

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
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

        Format that = (Format) o;

        if (format != that.format) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = format;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
