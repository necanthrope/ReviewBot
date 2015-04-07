package reviewbot.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Entity
@Table(name="books")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //private long id;
    private Long id;

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

    private Integer genre;

    private Integer subgenre;

    @Size(min = 1, max=4)
    private String year;

    @Size(min = 1, max=127)
    private String masterId;

    @Size(min = 1, max=5)
    private String free;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public Integer getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(Integer subgenre) {
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

    @Override
    public String toString(){
        return "{ID="+id+",Title="+title+",Publisher="+publisher+"}";
    }
}
