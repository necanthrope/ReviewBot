package reviewbot.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reviewbot.model.Book;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class BookDAO {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Book book) {
        getSession().save(book);
    }

    public void delete(Book book) {
        getSession().delete(book);
    }

    @SuppressWarnings("unchecked")
    public List getAll() {
        return getSession().createQuery("from Book").list();
    }

    public Book getByTitle(String title) {
        return (Book) getSession().createQuery(
                "from Book where title = :title")
                .setParameter("title", title)
                .uniqueResult();
    }

    public Book getById(long id) {
        return (Book) getSession().load(Book.class, id);
    }

    public void update(Book book) {
        getSession().update(book);
    }

}
