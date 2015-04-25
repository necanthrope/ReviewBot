/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.metadata.AwardDTO;
import reviewbot.dto.metadata.GenreDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Genre;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Repository
@Transactional
public class GenreRepository extends AbstractRepository<Integer, Integer, Genre, GenreDTO>{

    @Override
    public GenreDTO create(GenreDTO genreDTO) {
        Genre genre = wrap(genreDTO);
        getCurrentSession().save(genre);
        return unwrap(genre);
    }

    @Override
    public List<GenreDTO> readAll() {
        List<Genre> genreEntities = _entityManager.createQuery("from Genre").getResultList();
        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        for (Genre genre : genreEntities) {
            genreDTOs.add(unwrap(genre));
        }
        return genreDTOs;
    }

    @Override
    public List<GenreDTO> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<Genre> genreEntities = (List<Genre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Genre");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        for (Genre genre : genreEntities) {
            genreDTOs.add(unwrap(genre));
        }
        return genreDTOs;

    }

    @Override
    public GenreDTO readOne(Integer id) {
        return unwrap(readOneEntity(id));
    }

    public Genre readOneEntity(Integer id) {

        if (id == null)
            return new Genre();
        return (Genre) getCurrentSession().get(Genre.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GenreDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Genre> genreEntities = (List<Genre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Genre.class);
                criteria.add(Restrictions.in("id",ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        for (Genre genre : genreEntities) {
            genreDTOs.add(unwrap(genre));
        }
        return genreDTOs;
    }

    @Override
    public void update(GenreDTO genreDTO) {
        Genre genre = (Genre) getCurrentSession().get(Genre.class, genreDTO.getId());

        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());

        getCurrentSession().merge(genre);
    }

    @Override
    public void delete(Integer id) {
        Genre genre = (Genre) getCurrentSession().get(Genre.class, id);
        if (genre != null) {
            getCurrentSession().delete(genre);
            getCurrentSession().flush();
        }
    }

    @Override
    protected Genre wrap(GenreDTO genreDTO) {
        Genre genre = new Genre();

        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());

        return genre;
    }

    @Override
    protected GenreDTO unwrap(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        genreDTO.setDescription(genre.getDescription());

        return genreDTO;
    }

    public GenreMap wrapMapping (Book book, GenreDTO genreDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setGenre(readOneEntity(genreDTO.getId()));
        return genreMap;
    }
}
