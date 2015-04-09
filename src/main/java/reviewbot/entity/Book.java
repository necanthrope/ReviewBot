/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Entity
@Table(name="books")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    //private long id;
    private Integer id;

		@Column(name="user_id")
    private Integer userId;

    @NotNull
    @Size(min = 1, max=255)
    private String title;

    @NotNull
    @Size(min = 1, max=255)
    private String author;

    @Size(min = 1, max=255)
    private String publisher;

    @Size(min = 1, max=13)
    private String isbn;

    private Byte genre;

    private Byte subgenre;

    @Size(min = 1, max=4)
    private String year;

    @Size(min = 1, max=127)
		@Column(name="master_id")
    private String masterId;

    @Size(min = 1, max=5)
    private String free;


    @OneToMany(fetch = FetchType.EAGER)//,
            //cascade = CascadeType.ALL, targetEntity = GenreMap.class)
    @JoinTable(
            name="genre_map",
            joinColumns=@JoinColumn(name="id"),
            inverseJoinColumns = @JoinColumn( name="book_id")

    )
    private List<GenreMap> genreMap;



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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Byte getGenre() {
        return genre;
    }

    public void setGenre(Byte genre) {
        this.genre = genre;
    }

    public Byte getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(Byte subgenre) {
        this.subgenre = subgenre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public List<GenreMap> getGenreMap() {
        return genreMap;
    }

    public void setGenreMaps(List<GenreMap> genreMaps) {
        this.genreMap = genreMaps;
    }

    @Override
    public String toString(){
        return "{ID="+id+",Title="+title+",Publisher="+publisher+"}";
    }
}
