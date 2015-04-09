/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Entity
@Table(name="genre_map")
public class GenreMap implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private Long book_id;
    private Long genre_id;
    private Long subgenre_id;
    private Long theme_id;
    private Long misc_id;
    private Long format_id;
    private Long award_id;

    public Long getAward_id() {
        return award_id;
    }

    public void setAward_id(Long award_id) {
        this.award_id = award_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public Long getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Long genre_id) {
        this.genre_id = genre_id;
    }

    public Long getSubgenre_id() {
        return subgenre_id;
    }

    public void setSubgenre_id(Long subgenre_id) {
        this.subgenre_id = subgenre_id;
    }

    public Long getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Long theme_id) {
        this.theme_id = theme_id;
    }

    public Long getMisc_id() {
        return misc_id;
    }

    public void setMisc_id(Long misc_id) {
        this.misc_id = misc_id;
    }

    public Long getFormat_id() {
        return format_id;
    }

    public void setFormat_id(Long format_id) {
        this.format_id = format_id;
    }



}
