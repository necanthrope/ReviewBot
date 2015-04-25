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
import reviewbot.dto.GenreDTO;
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
    UserRepository _userRepository;

    @Autowired
    GenreRepository _genreRepository;

    @Override
    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        Book book = wrap(bookDTO);

        List<GenreMap> genreMapEntities = new ArrayList<GenreMap>();
        if (bookDTO.getGenres() != null) {
            for (GenreDTO genreDTO : bookDTO.getGenres()) {
                genreMapEntities.add(wrapMapping(book, genreDTO));
            }
        }

        book.setGenreMaps(genreMapEntities);
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

        if (book.getGenreMap() != null) {
            for (GenreMap genreMap : book.getGenreMap()) {
                genres.add(_genreRepository.unwrap(genreMap.getGenre()));
            }
        }

        bookDTO.setGenres(genres);

        return bookDTO;
    }

    private GenreMap wrapMapping (Book book, GenreDTO genreDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setGenre(_genreRepository.readOneEntity(genreDTO.getId()));
        return genreMap;
    }

}
