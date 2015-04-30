/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository.metadata;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.metadata.AwardDTO;
import reviewbot.dto.metadata.GenreDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Genre;
import reviewbot.repository.AbstractRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Repository
@Transactional
public class GenreRepository extends AbstractRepository<Genre> {

    @Override
    public Genre create(Genre genre) {
        getCurrentSession().save(genre);
        return genre;
    }

    @Override
    public List<Genre> readAll() {
        return _entityManager.createQuery("from Genre").getResultList();
    }

    @Override
    public List<Genre> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<Genre> genreEntities = (List<Genre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Genre");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        return genreEntities;

    }

    @Override
    public Genre readOne(Integer id) {
        return (Genre) getCurrentSession().get(Genre.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Genre> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Genre> genreEntities = (List<Genre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Genre.class);
                criteria.add(Restrictions.in("id",ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
        return genreEntities;
    }

    @Override
    public Genre update(Genre inGenre) {
        Genre genre = (Genre) getCurrentSession().get(Genre.class, inGenre.getId());

        genre.setName(inGenre.getName());
        genre.setDescription(inGenre.getDescription());

        getCurrentSession().merge(genre);
        return genre;
    }

    @Override
    public void delete(Integer id) {
        Genre genre = (Genre) getCurrentSession().get(Genre.class, id);
        if (genre != null) {
            getCurrentSession().delete(genre);
            getCurrentSession().flush();
        }
    }

}
