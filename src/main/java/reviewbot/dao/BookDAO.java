/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.dao;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
    public void create(Book book) {
        _entityManager.persist(book);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> readAll() {
        return _entityManager.createQuery("from Book").getResultList();
    }

    public Book getByTitle(String title) {
        return (Book) _entityManager.createQuery(
                "from Book where title = :title")
                .setParameter("title", title)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> readRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        //return (List<Book>) getHibernateTemplate().execute(new HibernateCallback() {
        //    public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Book");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
        //    }
        //});
    }

    @SuppressWarnings("unchecked")
    public List<Book> readRangeWithMeta(Integer length, Integer offset) {
        final Integer len = length;
        final Integer offs = offset;
        return (List<Book>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria =
                        session.createCriteria(Book.class, "books");
                        //.createAlias("books.genreMap","genre_map");
                        //.add(Restrictions.eqProperty("books.id","books.genre_map.book_id"));


                criteria.addOrder(Order.asc("title"));
                return criteria.list();
            }
        });
    }

    @Override
    public Book readOne(Integer id) {
        return (Book) _entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> readList(Integer[] ids) {
        return null;
    }

    @Override
    public void update(Book book) {
        _entityManager.merge(book);
    }

    @Override
    public void delete(Book book) {
        if (_entityManager.contains(book))
            _entityManager.remove(book);
        else
            _entityManager.remove(_entityManager.merge(book));

    }

}
