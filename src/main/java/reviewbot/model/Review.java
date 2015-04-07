package reviewbot.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Entity
@Table(name="reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer bookId;

    @NotNull
    @Column(name = "body", length = 20000)
    private String body;

    @Size(min = 1, max=3)
    private String rating;

    @Column
    @Type(type="date")
    //@Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    @Size(min = 1, max=1)
    private String crossposted;

    private Integer wp_postId;

    @Column(name = "notes", length = 20000)
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getCrossposted() {
        return crossposted;
    }

    public void setCrossposted(String crossposted) {
        this.crossposted = crossposted;
    }

    public Integer getWp_postId() {
        return wp_postId;
    }

    public void setWp_postId(Integer wp_postId) {
        this.wp_postId = wp_postId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
