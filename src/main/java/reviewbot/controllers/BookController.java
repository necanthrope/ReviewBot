package reviewbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reviewbot.dao.BookDAO;
import reviewbot.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookDAO _bookDAO;

    @RequestMapping(method=RequestMethod.GET)
    public List<Book> listBook() {

        List<Book> books = new ArrayList<Book>();

        try {
            books = _bookDAO.getAll();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        for(Book book:books) {
            System.out.println("Book: " + book.getTitle());
        }

        return books;
    }

}
