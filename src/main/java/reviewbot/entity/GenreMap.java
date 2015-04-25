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

    //@Column(name="book_id")
    //private Integer bookId;

    //@Column(name="genre_id")
    //private Integer genreId;

    @Column(name="subgenre_id")
    private Integer subgenreId;

    @Column(name="theme_id")
    private Integer themeId;

    @Column(name="misc_id")
    private Integer miscId;

    @Column(name="format_id")
    private Integer formatId;

    @Column(name="award_id")
    private Integer awardId;

    @ManyToOne(cascade=CascadeType.ALL)
    public Book book;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="genre_id", unique=true, nullable=false, insertable=true, updatable=true)
    private Genre genre;

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    */

    //public Integer getGenreId() {
    //    return genreId;
    //}

    //public void setGenreId(Integer genreId) {
    //    this.genreId = genreId;
    //}

    public Integer getSubgenreId() {
        return subgenreId;
    }

    public void setSubgenreId(Integer subgenreId) {
        this.subgenreId = subgenreId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public Integer getMiscId() {
        return miscId;
    }

    public void setMiscId(Integer miscId) {
        this.miscId = miscId;
    }

    public Integer getFormatId() {
        return formatId;
    }

    public void setFormatId(Integer formatId) {
        this.formatId = formatId;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }



}
