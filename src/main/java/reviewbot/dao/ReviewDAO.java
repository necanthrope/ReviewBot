package reviewbot.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reviewbot.model.Review;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class ReviewDAO {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Review review) {
        getSession().save(review);
    }

    public void delete(Review review) {
        getSession().delete(review);
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        return getSession().createQuery("from Review").list();
    }

    public Review getById(long id) {
        return (Review) getSession().load(Review.class, id);
    }

    public void update(Review review) {
        getSession().update(review);
    }


}
