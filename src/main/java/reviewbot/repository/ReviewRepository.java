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
public class ReviewRepository extends AbstractRepository<Integer, Integer, Review, ReviewDTO> {

    @Override
    public ReviewDTO create(ReviewDTO review) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReviewDTO> readAll() {
        List<Review> reviewEntities = _entityManager.createQuery("from Review").getResultList();

        List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
        for (Review review : reviewEntities) {
            reviewDTOs.add(unwrap(review));
        }
        return reviewDTOs;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ReviewDTO> readRange(Integer length, Integer offset) {

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

        List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
        for (Review review : reviewEntities) {
            reviewDTOs.add(unwrap(review));
        }
        return reviewDTOs;

    }

    @Override
    public ReviewDTO readOne(Integer id) {
        return unwrap(_entityManager.find(Review.class, id));
    }

    @Override
    public List<ReviewDTO> readList(Integer[] ids) {
        return null;
    }

    @Override
    public void update(ReviewDTO reviewDTO) {
        _entityManager.merge(wrap(reviewDTO));
    }

    @Override
    public void delete(Integer id) {
        return;
    }

    @Override
    protected Review wrap(ReviewDTO reviewDTO) {
        Review review = new Review();



        return review;
    }

    @Override
    protected ReviewDTO unwrap(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();



        return reviewDTO;
    }

}
