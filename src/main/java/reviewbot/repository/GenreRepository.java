/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.GenreDTO;
import reviewbot.entity.Genre;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Repository
@Transactional
public class GenreRepository extends AbstractRepository<Integer, Integer, Genre, GenreDTO> {

    @Override
    public GenreDTO create(GenreDTO genreDTO) {
        return null;
    }

    @Override
    public GenreDTO readOne(Integer id) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GenreDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Genre> genres = (List<Genre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Genre.class);
                criteria.add(Restrictions.in("id",ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        for (Genre genre : genres) {
            genreDTOs.add(unwrap(genre));
        }
        return genreDTOs;
    }

    @Override
    public List<GenreDTO> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<GenreDTO> readAll() {
        return null;
    }

    @Override
    public void update(GenreDTO genre) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    protected Genre wrap(GenreDTO genreDTO) {
        return null;
    }

    @Override
    protected GenreDTO unwrap(Genre genre) {
        return null;
    }

}
