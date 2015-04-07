package reviewbot.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import reviewbot.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class BookDAO extends HibernateDaoSupport{

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager _entityManager;

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }

    public void create(Book book) {
        _entityManager.persist(book);
    }

    public void delete(Book book) {
        if (_entityManager.contains(book))
            _entityManager.remove(book);
        else
            _entityManager.remove(_entityManager.merge(book));
        return;
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        return _entityManager.createQuery("from Book").getResultList();
    }

    public Book getByTitle(String title) {
        return (Book) _entityManager.createQuery(
                "from Book where title = :title")
                .setParameter("title", title)
                .getSingleResult();
    }

    public List<Book> getByRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        return (List<Book>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = session.createQuery("from Book");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });
    }

    public Book getById(long id) {
        return (Book) _entityManager.find(Book.class, id);
    }

    public void update(Book book) { _entityManager.merge(book); }

}
