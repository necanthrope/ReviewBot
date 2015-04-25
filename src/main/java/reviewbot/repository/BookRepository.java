/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.BookDTO;
import reviewbot.dto.metadata.GenreDTO;
import reviewbot.dto.metadata.SubgenreDTO;
import reviewbot.dto.metadata.ThemeDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class BookRepository extends AbstractRepository<Integer, Integer, Book, BookDTO> {

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private GenreRepository _genreRepository;

    @Autowired
    private SubgenreRepository _subgenreRepository;

    @Autowired
    private ThemeRepository _themeRepository;

    @Autowired
    private AwardRepository _awardRepository;

    @Autowired
    private FormatRepository _formatRepository;

    @Autowired
    private MiscRepository _miscRepository;

    @Override
    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Book book = wrap(bookDTO);
        List<GenreMap> genreMapEntities = new ArrayList<GenreMap>();
        book.setGenreMaps(genreMapEntities);

        // Generate and add genre entities to genre map entity
        if (bookDTO.getGenres() != null) {
            for (GenreDTO genreDTO : bookDTO.getGenres()) {
                genreMapEntities.add(_genreRepository.wrapMapping(book, genreDTO));
            }
        }

        // Generate and add subgenre entities to genre map entity
        if (bookDTO.getSubgenres() != null) {
            for (SubgenreDTO subgenreDTO : bookDTO.getSubgenres()) {
                genreMapEntities.add(_subgenreRepository.wrapMapping(book, subgenreDTO));
            }
        }

        // Generate and add theme entities to genre map entity
        if (bookDTO.getThemes() != null) {
            for (ThemeDTO themeDTO : bookDTO.getThemes()) {
                genreMapEntities.add(_themeRepository.wrapMapping(book, themeDTO));
            }
        }

        getCurrentSession().save(book);
        return unwrap(book);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BookDTO> readAll() {
        List<Book> bookEntities = _entityManager.createQuery("from Book").getResultList();
        List<BookDTO> bookDTOs = new ArrayList<BookDTO>();
        for (Book book : bookEntities) {
            bookDTOs.add(unwrap(book));
        }
        return bookDTOs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BookDTO> readRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        List<Book> bookEntities = (List<Book>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Book");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        List<BookDTO> bookDTOs = new ArrayList<BookDTO>();
        for (Book book : bookEntities) {
            bookDTOs.add(unwrap(book));
        }
        return bookDTOs;

    }

    @Override
    public BookDTO readOne(Integer id) {
        return unwrap((Book) getCurrentSession().get(Book.class, id));
    }

    @Override
    public List<BookDTO> readList(Integer[] ids) {
        return null;
    }

    @Override
    public void update(BookDTO bookDTO) {
        Book book = (Book) getCurrentSession().get(Book.class, bookDTO.getId());

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setIsbn(bookDTO.getIsbn());
        book.setYear(bookDTO.getYear());
        book.setMasterId(bookDTO.getMasterId());
        book.setFree(bookDTO.getFree());

        getCurrentSession().merge(book);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Book book = (Book) getCurrentSession().get(Book.class, id);
        if (book != null) {
            getCurrentSession().delete(book);
            getCurrentSession().flush();
        }
    }

    @Override
    protected Book wrap(BookDTO bookDTO) {
        Book book = new Book();

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setIsbn(bookDTO.getIsbn());
        book.setYear(bookDTO.getYear());
        book.setMasterId(bookDTO.getMasterId());
        book.setFree(bookDTO.getFree());
        book.setUsers(_userRepository.wrap(
                _userRepository.readOne(bookDTO.getUser().getId())));

        return book;
    }

    @Override
    protected BookDTO unwrap(Book book) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setYear(book.getYear());
        bookDTO.setMasterId(book.getMasterId());
        bookDTO.setFree(book.getFree());
        bookDTO.setUser(_userRepository.unwrap(book.getUsers()));

        List<GenreDTO> genres = new ArrayList<GenreDTO>();
        bookDTO.setGenres(genres);

        List<SubgenreDTO> subgenres = new ArrayList<SubgenreDTO>();
        bookDTO.setSubgenres(subgenres);

        List<ThemeDTO> themes = new ArrayList<ThemeDTO>();
        bookDTO.setThemes(themes);

        if (book.getGenreMap() != null) {
            for (GenreMap genreMap : book.getGenreMap()) {
                if (genreMap.getGenre() != null)
                    genres.add(_genreRepository.unwrap(genreMap.getGenre()));

                if (genreMap.getSubgenre() != null)
                    subgenres.add(_subgenreRepository.unwrap(genreMap.getSubgenre()));

                if (genreMap.getTheme() != null)
                    themes.add(_themeRepository.unwrap(genreMap.getTheme()));
            }
        }

        bookDTO.setGenres(genres);

        return bookDTO;
    }


}
