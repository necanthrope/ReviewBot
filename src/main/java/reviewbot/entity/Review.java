/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.entity;

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
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "book_id")
    private Integer bookId;

    @NotNull
    @Column(name = "body", columnDefinition = "longtext")
    private String body;

    @Size(min = 1, max=3)
    private String rating;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_date;

    @Size(min = 1, max=1)
    private String crossposted;

    @Column(name = "wp_post_id")
    private Integer wp_postId;

    @Column(name = "notes", columnDefinition= "longtext")
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
