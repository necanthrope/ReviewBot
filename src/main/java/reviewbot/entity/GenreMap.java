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

    private Integer id;

    private Integer book_id;
    private Integer genre_id;
    private Integer subgenre_id;
    private Integer theme_id;
    private Integer misc_id;
    private Integer format_id;
    private Integer award_id;

    public Integer getAward_id() {
        return award_id;
    }

    public void setAward_id(Integer award_id) {
        this.award_id = award_id;
    }

    public Integer getId() {
        return id;
    }

   public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }

    public Integer getSubgenre_id() {
        return subgenre_id;
    }

    public void setSubgenre_id(Integer subgenre_id) {
        this.subgenre_id = subgenre_id;
    }

    public Integer getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Integer theme_id) {
        this.theme_id = theme_id;
    }

    public Integer getMisc_id() {
        return misc_id;
    }

    public void setMisc_id(Integer misc_id) {
        this.misc_id = misc_id;
    }

    public Integer getFormat_id() {
        return format_id;
    }

    public void setFormat_id(Integer format_id) {
        this.format_id = format_id;
    }



}
