/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

import reviewbot.dto.BookDTO;
import reviewbot.dto.metadata.*;

/**
 * Created by jtidwell on 4/19/2015.
 */
public class TestDataGenerator {

    public static BookDTO createBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(Double.toString((Math.random() * 20)));
        bookDTO.setAuthor(Double.toString((Math.random() * 10)));
        bookDTO.setPublisher(Double.toString((Math.random() * 10)));
        bookDTO.setIsbn(Double.toString(Math.floor((Math.random() * 100000000))));
        bookDTO.setYear(Double.toString(Math.floor((Math.random() * 100 ))));

        return bookDTO;
    }

    public static GenreDTO createGenre() {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName(Double.toString((Math.random() * 20)));
        genreDTO.setDescription(Double.toString((Math.random() * 100)));

        return genreDTO;
    }

    public static SubgenreDTO createSubgenre() {
        SubgenreDTO subgenreDTO = new SubgenreDTO();
        subgenreDTO.setName(Double.toString((Math.random() * 20)));
        subgenreDTO.setDescription(Double.toString((Math.random() * 100)));

        return subgenreDTO;
    }

    public static ThemeDTO createTheme() {
        ThemeDTO themeDTO = new ThemeDTO();
        themeDTO.setName(Double.toString((Math.random() * 20)));
        themeDTO.setDescription(Double.toString((Math.random() * 100)));

        return themeDTO;
    }

    public static AwardDTO createAward() {
        AwardDTO awardDTO = new AwardDTO();
        awardDTO.setName(Double.toString((Math.random() * 20)));
        awardDTO.setDescription(Double.toString((Math.random() * 100)));

        return awardDTO;
    }

    public static FormatDTO createFormat() {
        FormatDTO formatDTO = new FormatDTO();
        formatDTO.setName(Double.toString((Math.random() * 20)));
        formatDTO.setDescription(Double.toString((Math.random() * 100)));

        return formatDTO;
    }

    public static MiscDTO createMisc() {
        MiscDTO miscDTO = new MiscDTO();
        miscDTO.setName(Double.toString((Math.random() * 20)));
        miscDTO.setDescription(Double.toString((Math.random() * 100)));

        return miscDTO;
    }

}
