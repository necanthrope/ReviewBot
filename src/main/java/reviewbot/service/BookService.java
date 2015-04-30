/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.BookDTO;
import reviewbot.dto.metadata.*;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.User;
import reviewbot.repository.*;
import reviewbot.service.metadata.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class BookService extends AbstractService<Book, BookDTO>{

    @Autowired
    private BookRepository _bookRepository;

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private UserService _userService;

    @Autowired
    private GenreService _genreService;

    @Autowired
    private SubgenreService _subgenreService;

    @Autowired
    private ThemeService _themeService;

    @Autowired
    private AwardService _awardService;

    @Autowired
    private FormatService _formatService;

    @Autowired
    private MiscService _miscService;

    @Override
    public BookDTO create(BookDTO bookDTO) {

        Book book = wrap(bookDTO);
        List<GenreMap> genreMapEntities = new ArrayList<GenreMap>();
        book.setGenreMaps(genreMapEntities);

        // Generate and add genre entities to genre map entity
        if (bookDTO.getGenres() != null) {
            for (GenreDTO genreDTO : bookDTO.getGenres()) {
                genreMapEntities.add(_genreService.wrapMapping(book, genreDTO));
            }
        }

        // Generate and add subgenre entities to genre map entity
        if (bookDTO.getSubgenres() != null) {
            for (SubgenreDTO subgenreDTO : bookDTO.getSubgenres()) {
                genreMapEntities.add(_subgenreService.wrapMapping(book, subgenreDTO));
            }
        }

        // Generate and add theme entities to genre map entity
        if (bookDTO.getThemes() != null) {
            for (ThemeDTO themeDTO : bookDTO.getThemes()) {
                genreMapEntities.add(_themeService.wrapMapping(book, themeDTO));
            }
        }

        // Generate and add award entities to genre map entity
        if (bookDTO.getAwards() != null) {
            for (AwardDTO awardDTO : bookDTO.getAwards()) {
                genreMapEntities.add(_awardService.wrapMapping(book, awardDTO));
            }
        }

        // Generate and add format entities to genre map entity
        if (bookDTO.getFormats() != null) {
            for (FormatDTO formatDTO : bookDTO.getFormats()) {
                genreMapEntities.add(_formatService.wrapMapping(book, formatDTO));
            }
        }

        // Generate and add misc entities to genre map entity
        if (bookDTO.getMisc() != null) {
            for (MiscDTO miscDTO : bookDTO.getMisc()) {
                genreMapEntities.add(_miscService.wrapMapping(book, miscDTO));
            }
        }

        return unwrap(_bookRepository.create(book));
    }

    @Override
    public BookDTO readOne(Long id) {
        return unwrap(_bookRepository.readOne(id.intValue()));
    }

    @Override
    public List<BookDTO> readList(Long[] ids) {
        Integer[] idsInt = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsInt[i] = ids[i].intValue();
        }
        return unwrapList(_bookRepository.readList(idsInt));
    }

    @Override
    public List<BookDTO> readRange(Integer offset, Integer length) {
        return unwrapList(_bookRepository.readRange(offset, length));
    }

    @Override
    public List<BookDTO> readAll() {
        return unwrapList(_bookRepository.readAll());
    }

    @Override
    public BookDTO update(BookDTO dto) {

        Book book = _bookRepository.readOne(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setIsbn(dto.getIsbn());
        book.setYear(dto.getYear());
        book.setMasterId(dto.getMasterId());
        book.setFree(dto.getFree());
        return unwrap(_bookRepository.update(book));
    }

    @Override
    public void delete(Integer id) {
        _bookRepository.delete(id);
    }

    @Override
    public Book wrap(BookDTO bookDTO) {
        Book book = new Book();

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setIsbn(bookDTO.getIsbn());
        book.setYear(bookDTO.getYear());
        book.setMasterId(bookDTO.getMasterId());
        book.setFree(bookDTO.getFree());

        User user = _userRepository.readOne(bookDTO.getUser().getId());

        book.setUsers(
                //_userRepository.readOne(bookDTO.getUser().getId()));
                user);

        return book;
    }

    private List<BookDTO> unwrapList(List<Book> bookEntities) {
        List<BookDTO> books = new ArrayList<BookDTO>();
        for (Book book : bookEntities) {
            books.add(unwrap(book));
        }
        return books;
    }

    @Override
    public BookDTO unwrap(Book book) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setYear(book.getYear());
        bookDTO.setMasterId(book.getMasterId());
        bookDTO.setFree(book.getFree());
        bookDTO.setUser(_userService.unwrap(book.getUsers()));

        bookDTO.setGenres(new ArrayList<GenreDTO>());
        bookDTO.setSubgenres(new ArrayList<SubgenreDTO>());
        bookDTO.setThemes(new ArrayList<ThemeDTO>());
        bookDTO.setFormats(new ArrayList<FormatDTO>());
        bookDTO.setAwards(new ArrayList<AwardDTO>());
        bookDTO.setMisc(new ArrayList<MiscDTO>());

        if (book.getGenreMap() != null) {
            for (GenreMap genreMap : book.getGenreMap()) {
                if (genreMap.getGenre() != null)
                    bookDTO.getGenres().add(_genreService.unwrap(genreMap.getGenre()));

                if (genreMap.getSubgenre() != null)
                    bookDTO.getSubgenres().add(_subgenreService.unwrap(genreMap.getSubgenre()));

                if (genreMap.getTheme() != null)
                    bookDTO.getThemes().add(_themeService.unwrap(genreMap.getTheme()));

                if (genreMap.getAward() != null)
                    bookDTO.getAwards().add(_awardService.unwrap(genreMap.getAward()));

                if (genreMap.getFormat() != null)
                    bookDTO.getFormats().add(_formatService.unwrap(genreMap.getFormat()));

                if (genreMap.getMisc() != null)
                    bookDTO.getMisc().add(_miscService.unwrap(genreMap.getMisc()));
            }
        }

        return bookDTO;
    }

}
