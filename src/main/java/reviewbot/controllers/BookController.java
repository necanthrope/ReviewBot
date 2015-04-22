/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reviewbot.repository.BookRepository;
import reviewbot.entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@RestController
public class BookController {

    @Autowired
    private BookRepository _bookDAO;

    /**
     * Creates a book object in the db, then returns that book with the ID set.
     * @param book
     * @return saved book
     */
    @RequestMapping(value="/createBook", method=RequestMethod.POST)
    public @ResponseBody Book createBook(@RequestBody Book book) {
        return _bookDAO.create(book);
    }


    /**
     * Returns all books in the database.
     * @return  A JSON list of book objects
     */
    @RequestMapping(value="/readBooks", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Book> getBookList(
            @RequestParam(value="length", defaultValue="all") String lengthStr,
            @RequestParam(value="offset", defaultValue="all") String offsetStr
    ) {

        List<Book> books = new ArrayList<Book>();

        if (lengthStr.equals("all") && offsetStr.equals("all")) {
            try {
                books = _bookDAO.readAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Integer length;
        Integer offset;

        try {
            length = Integer.parseInt(lengthStr);
            offset = Integer.parseInt(offsetStr);
            books = _bookDAO.readRange(length, offset);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    @RequestMapping(value="/readBook", method=RequestMethod.GET, produces="application/json")
    public Book getBook(@RequestParam(value="id", defaultValue="0") String idStr) {

        Book book;

        if(idStr == null) {
            book =  new Book();
            return book;
        }

        book = _bookDAO.readOne(new Integer(Integer.parseInt(idStr)));
        return book;

    }

    @RequestMapping(value="/updateBook", method=RequestMethod.POST)
    public void updateBook(@RequestBody Book book) {
        _bookDAO.update(book);
    }

    @RequestMapping(value="/deleteBook", method=RequestMethod.GET)
    public void deleteBook(@RequestParam(value="id") String idStr) {

        if(idStr == null)
            return;

        _bookDAO.delete(Integer.parseInt(idStr));

    }

}
