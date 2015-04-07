package reviewbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewbot.dao.BookDAO;
import reviewbot.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@RestController
public class BookController {

    @Autowired
    private BookDAO _bookDAO;

    /**
     * Returns all books in the database.
     * @return  A JSON list of book objects
     */
    @RequestMapping(value="/books", method=RequestMethod.GET, produces="application/json")
    public List<Book> listBooks(
            @RequestParam(value="length", defaultValue="all") String length,
            @RequestParam(value="offset", defaultValue="all") String offset
    ) {

        List<Book> books = new ArrayList<Book>();

        if (length.equals("all") && offset.equals("all")) {
            try {
                books = _bookDAO.getAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Integer lengthInt;
        Integer offsetInt;

        try {
            lengthInt = Integer.parseInt(length);
            offsetInt = Integer.parseInt(offset);
            books = _bookDAO.getByRange(lengthInt, offsetInt);
        }
        catch(Exception e) {
            e.printStackTrace();
        }




        return books;
    }



}
