package reviewbot.dto;

import java.util.Date;

/**
 * Created by jtidwell on 4/22/2015.
 */
public class ReviewDTO {

    private Integer id;
    private Integer userId;
    private Integer bookId;
    private String body;
    private String rating;
    private Date create_date;
    private String crossposted;
    private Integer wp_postId;
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
