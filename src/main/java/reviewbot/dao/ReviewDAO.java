package reviewbot.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import reviewbot.model.Review;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.spec.ECField;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class ReviewDAO extends AbstractDAO<Review>{



    @SuppressWarnings("unchecked")
    public List getAll() {
        return _entityManager.createQuery("from Review").getResultList();
    }

    public List<Review> getByRange(Integer length, Integer offset) {

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

    public Review getById(long id) {
        return _entityManager.find(Review.class, id);
    }

    public void create(Review review) {
        _entityManager.persist(review);
    }

    public void update(Review review) {
        _entityManager.merge(review);
    }

    public void delete(Review review) {
        if(_entityManager.contains(review))
            _entityManager.remove(review);
        else
            _entityManager.remove(_entityManager.merge(review));
    }

}
