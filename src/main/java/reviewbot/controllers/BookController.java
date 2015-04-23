/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reviewbot.dto.BookDTO;
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
    private BookRepository _bookRepo;

    /**
     * Creates a book object in the db, then returns that book with the ID set.
     * @param bookDTO
     * @return saved book
     */
    @RequestMapping(value="/createBook", method=RequestMethod.POST)
    public @ResponseBody BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return _bookRepo.create(bookDTO);
    }


    /**
     * Returns all books in the database.
     * @return  A JSON list of book objects
     */
    @RequestMapping(value="/readBooks", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<BookDTO> readBooks(
            @RequestParam(value="length", defaultValue="all") String lengthStr,
            @RequestParam(value="offset", defaultValue="all") String offsetStr
    ) {

        List<BookDTO> bookDTOs = new ArrayList<BookDTO>();

        if (lengthStr.equals("all") && offsetStr.equals("all")) {
            try {
                bookDTOs = _bookRepo.readAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Integer length;
        Integer offset;

        try {
            length = Integer.parseInt(lengthStr);
            offset = Integer.parseInt(offsetStr);
            bookDTOs = _bookRepo.readRange(length, offset);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return bookDTOs;
    }

    @RequestMapping(value="/readBook", method=RequestMethod.GET, produces="application/json")
    public BookDTO readBook(@RequestParam(value="id", defaultValue="0") String idStr) {

        if(idStr == null) {
            return new BookDTO();
        }
        return _bookRepo.readOne(new Integer(Integer.parseInt(idStr)));

    }

    @RequestMapping(value="/updateBook", method=RequestMethod.POST)
    public void updateBook(@RequestBody BookDTO bookDTO) {
        _bookRepo.update(bookDTO);
    }

    @RequestMapping(value="/deleteBook", method=RequestMethod.GET)
    public void deleteBook(@RequestParam(value="id") String idStr) {

        if(idStr == null)
            return;

        _bookRepo.delete(Integer.parseInt(idStr));

    }

}
