/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.entity;

import reviewbot.entity.metadata.*;

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

    @ManyToOne
    @JoinColumn(name="book_id")
    public Book book;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="genre_id", unique=true, nullable=false, insertable=true, updatable=true)
    private Genre genre;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="subgenre_id", unique=true, nullable=false, insertable=true, updatable=true)
    private Subgenre subgenre;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="theme_id", unique=true, nullable=false, insertable=true, updatable=true)
    private Theme theme;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="award_id", unique=true, nullable=false, insertable=true, updatable=true)
    private Award award;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="format_id", unique=true, nullable=false, insertable=true, updatable=true)
    private Format format;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="misc_id", unique=true, nullable=false, insertable=true, updatable=true)
    private Misc misc;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Subgenre getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(Subgenre subgenre) {
        this.subgenre = subgenre;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Misc getMisc() {
        return misc;
    }

    public void setMisc(Misc misc) {
        this.misc = misc;
    }

}
