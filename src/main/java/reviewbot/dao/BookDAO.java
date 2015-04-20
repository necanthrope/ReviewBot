/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.dao;

import org.hibernate.*;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.entity.Book;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class BookDAO extends AbstractDAO<Integer, Integer, Book>{

    @Override
    @Transactional
    public Book create(Book book) {
        getCurrentSession().save(book);
        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> readAll() {
        return _entityManager.createQuery("from Book").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> readRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        return (List<Book>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Book");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });
    }

    @Override
    public Book readOne(Integer id) {
        Book book;
        book = (Book) getCurrentSession().get(Book.class, id);
        return book;
    }

    @Override
    public List<Book> readList(Integer[] ids) {
        return null;
    }

    @Override
    public void update(Book book) {
        getCurrentSession().merge(book);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Book book = readOne(id);
        getCurrentSession().delete(book);
        getCurrentSession().flush();
    }

}
