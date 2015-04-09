/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.entity.Genre;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Repository
@Transactional
public class GenreDAO extends AbstractDAO<Integer, Integer, Genre> {

    @Override
    public void create(Genre genre) {

    }

    @Override
    public Genre readOne(Integer id) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Genre> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        return (List<Genre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Genre.class);
                criteria.add(Restrictions.in("id",ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
    }

    @Override
    public List<Genre> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<Genre> readAll() {
        return null;
    }

    @Override
    public void update(Genre genre) {

    }

    @Override
    public void delete(Genre genre) {

    }

}
