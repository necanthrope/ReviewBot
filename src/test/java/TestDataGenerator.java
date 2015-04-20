/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

import reviewbot.entity.Book;
import reviewbot.entity.Genre;
import reviewbot.entity.Subgenre;
import reviewbot.entity.Theme;

/**
 * Created by jtidwell on 4/19/2015.
 */
public class TestDataGenerator {

    public static Book createBook() {
        Book book = new Book();
        book.setTitle(Double.toString((Math.random() * 20)));
        book.setAuthor(Double.toString((Math.random() * 10)));
        book.setPublisher(Double.toString((Math.random() * 10)));
        book.setIsbn(Double.toString(Math.floor((Math.random() * 100000000))));
        book.setYear(Double.toString(Math.floor((Math.random() * 100 ))));

        return book;
    }

    public static Genre createGenre(Book book) {
        Genre genre = new Genre();
        genre.setName(Double.toString((Math.random() * 20)));
        genre.setDescription(Double.toString((Math.random() * 100)));

        return genre;
    }

    public static Subgenre createSubgenre(Book book) {
        Subgenre subgenre = new Subgenre();
        subgenre.setName(Double.toString((Math.random() * 20)));
        subgenre.setDescription(Double.toString((Math.random() * 100)));

        return subgenre;
    }

    public static Theme createTheme(Book book) {
        Theme theme = new Theme();
        theme.setName(Double.toString((Math.random() * 20)));
        theme.setDescription(Double.toString((Math.random() * 100)));

        return theme;
    }
}
