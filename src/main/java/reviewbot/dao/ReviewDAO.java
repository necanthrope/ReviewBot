/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.entity.Review;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class ReviewDAO extends AbstractDAO<Integer, Integer, Review>{

    @Override
    public void create(Review review) {
        _entityManager.persist(review);
    }

    @Override
    public Review readOne(Integer id) {
        return _entityManager.find(Review.class, id);
    }

    @Override
    public List<Review> readList(Integer[] ids) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> readRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        return (List<Review>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = session.createQuery("from Review");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> readAll() {
        return _entityManager.createQuery("from Review").getResultList();
    }

    @Override
    public void update(Review review) {
        _entityManager.merge(review);
    }

    @Override
    public void delete(Review review) {
        if(_entityManager.contains(review))
            _entityManager.remove(review);
        else
            _entityManager.remove(_entityManager.merge(review));
    }

}
