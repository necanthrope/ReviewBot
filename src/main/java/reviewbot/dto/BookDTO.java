package reviewbot.dto;

import reviewbot.dto.metadata.GenreDTO;
import reviewbot.dto.metadata.SubgenreDTO;
import reviewbot.dto.metadata.ThemeDTO;

import java.util.List;

/**
 * Created by jtidwell on 4/22/2015.
 */
public class BookDTO {

    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String year;
    private String masterId;
    private String free;
    private UserDTO user;

    private List<GenreDTO> genres;
    private List<SubgenreDTO> subgenres;
    private List<ThemeDTO> themes;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO userDTO) {
        this.user = userDTO;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    public List<SubgenreDTO> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(List<SubgenreDTO> subgenres) {
        this.subgenres = subgenres;
    }

    public List<ThemeDTO> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeDTO> themes) {
        this.themes = themes;
    }

}
