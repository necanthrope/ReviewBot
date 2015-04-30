/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.ReviewDTO;
import reviewbot.entity.Review;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class ReviewRepository extends AbstractRepository<Review> {

    @Override
    public Review create(Review review) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> readAll() {
        return null;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Review> readRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        List<Review> reviewEntities =  (List<Review>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = session.createQuery("from Review");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        return reviewEntities;

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
    public Review update(Review inReview) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        return;
    }

}
