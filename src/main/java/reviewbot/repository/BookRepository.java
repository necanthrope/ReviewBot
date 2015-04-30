/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.BookDTO;
import reviewbot.dto.metadata.*;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/6/2015.
 */
@Repository
@Transactional
public class BookRepository extends AbstractRepository<Book> {

    @Override
    @Transactional
    public Book create(Book book) {
        getCurrentSession().save(book);
        return book;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> readAll() {
        Criteria criteria = getCurrentSession().createCriteria(Book.class);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("id"));

        List<Book> bookEntities = (List<Book>)criteria.list();
        List<Book> books = new ArrayList<Book>();

        for (Book book : bookEntities) {
            books.add(book);
        }
        return books;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> readRange(Integer length, Integer offset) {

        final Integer len = length;
        final Integer offs = offset;

        // Solution by MattC@stackoverflow
        // https://stackoverflow.com/questions/11038234/pagination-with-hibernate-criteria-and-distinct-root-entity/23618190#23618190

        Criteria criteria = getCurrentSession().createCriteria(Book.class);
        Projection idCountProjection = Projections.countDistinct("id");
        criteria.setProjection(idCountProjection);
        int totalResultCount = ((Long)criteria.uniqueResult()).intValue();

        criteria.setProjection(Projections.distinct(Projections.property("id")));
        if (offs != null)
            criteria.setFirstResult(offs);
        if (len != null)
            criteria.setMaxResults(len);
        List uniqueSubList = criteria.list();

        criteria.setProjection(null);
        criteria.setFirstResult(0);
        criteria.setMaxResults(Integer.MAX_VALUE);
        criteria.add(Restrictions.in("id", uniqueSubList));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return (List<Book>)criteria.list();
    }

    @Override
    public Book readOne(Integer id) {
        return (Book) getCurrentSession().get(Book.class, id);
    }

    @Override
    public List<Book> readList(Integer[] ids) {
        return null;
    }

    /* TODO : update metadata types
     */
    @Override
    public Book update(Book book) {
        getCurrentSession().merge(book);
        return book;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Book book = (Book) getCurrentSession().get(Book.class, id);
        if (book != null) {
            getCurrentSession().delete(book);
            getCurrentSession().flush();
        }
    }


}
