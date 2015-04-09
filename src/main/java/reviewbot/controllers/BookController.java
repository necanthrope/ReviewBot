/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewbot.dao.BookDAO;
import reviewbot.entity.Book;

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
    public List<Book> getBookList(
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

    @RequestMapping(value="/book", method=RequestMethod.GET, produces="application/json")
    public Book getBook(@RequestParam(value="id", defaultValue="0") String idStr) {

        Book book;

        if(idStr == null) {
            book =  new Book();
            return book;
        }

        book = _bookDAO.readOne(new Long(Integer.parseInt(idStr)));
        return book;

    }



}
