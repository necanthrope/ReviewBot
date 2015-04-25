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
import reviewbot.entity.BookEntity;
import reviewbot.entity.GenreMapEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class BookRepository extends AbstractRepository<Integer, Integer, BookEntity, BookDTO> {

    @Autowired
    UserRepository _userRepository;

    @Autowired
    GenreRepository _genreRepository;

    @Override
    @Transactional
    public BookDTO create(BookDTO bookDTO) {
        BookEntity bookEntity = wrap(bookDTO);

        List<GenreMapEntity> genreMapEntities = new ArrayList<GenreMapEntity>();
        if (bookDTO.getGenres() != null) {
            for (GenreDTO genreDTO : bookDTO.getGenres()) {
                genreMapEntities.add(wrapMapping(bookEntity, genreDTO));
            }
        }

        bookEntity.setGenreMaps(genreMapEntities);
        getCurrentSession().save(bookEntity);
        return unwrap(bookEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BookDTO> readAll() {
        List<BookEntity> bookEntities = _entityManager.createQuery("from Book").getResultList();
        List<BookDTO> bookDTOs = new ArrayList<BookDTO>();
        for (BookEntity bookEntity : bookEntities) {
            bookDTOs.add(unwrap(bookEntity));
        }
        return bookDTOs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BookDTO> readRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        List<BookEntity> bookEntities = (List<BookEntity>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Book");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        List<BookDTO> bookDTOs = new ArrayList<BookDTO>();
        for (BookEntity bookEntity : bookEntities) {
            bookDTOs.add(unwrap(bookEntity));
        }
        return bookDTOs;

    }

    @Override
    public BookDTO readOne(Integer id) {
        return unwrap((BookEntity) getCurrentSession().get(BookEntity.class, id));
    }

    @Override
    public List<BookDTO> readList(Integer[] ids) {
        return null;
    }

    @Override
    public void update(BookDTO bookDTO) {
        BookEntity bookEntity = (BookEntity) getCurrentSession().get(BookEntity.class, bookDTO.getId());

        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setPublisher(bookDTO.getPublisher());
        bookEntity.setIsbn(bookDTO.getIsbn());
        bookEntity.setYear(bookDTO.getYear());
        bookEntity.setMasterId(bookDTO.getMasterId());
        bookEntity.setFree(bookDTO.getFree());

        getCurrentSession().merge(bookEntity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        BookEntity bookEntity = (BookEntity) getCurrentSession().get(BookEntity.class, id);
        if (bookEntity != null) {
            getCurrentSession().delete(bookEntity);
            getCurrentSession().flush();
        }
    }

    @Override
    protected BookEntity wrap(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();

        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setPublisher(bookDTO.getPublisher());
        bookEntity.setIsbn(bookDTO.getIsbn());
        bookEntity.setYear(bookDTO.getYear());
        bookEntity.setMasterId(bookDTO.getMasterId());
        bookEntity.setFree(bookDTO.getFree());
        bookEntity.setUsers(_userRepository.wrap(
                _userRepository.readOne(bookDTO.getUser().getId())));

        return bookEntity;
    }

    @Override
    protected BookDTO unwrap(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(bookEntity.getId());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setPublisher(bookEntity.getPublisher());
        bookDTO.setIsbn(bookEntity.getIsbn());
        bookDTO.setYear(bookEntity.getYear());
        bookDTO.setMasterId(bookEntity.getMasterId());
        bookDTO.setFree(bookEntity.getFree());
        bookDTO.setUser(_userRepository.unwrap(bookEntity.getUsers()));

        List<GenreDTO> genres = new ArrayList<GenreDTO>();

        if (bookEntity.getGenreMapEntity() != null) {
            for (GenreMapEntity genreMapEntity : bookEntity.getGenreMapEntity()) {
                genres.add(_genreRepository.unwrap(genreMapEntity.getGenreEntity()));
            }
        }

        bookDTO.setGenres(genres);

        return bookDTO;
    }

    private GenreMapEntity wrapMapping (BookEntity bookEntity, GenreDTO genreDTO) {
        GenreMapEntity genreMapEntity = new GenreMapEntity();
        genreMapEntity.setBookEntity(bookEntity);
        genreMapEntity.setGenreEntity(_genreRepository.readOneEntity(genreDTO.getId()));
        return genreMapEntity;
    }

}
