/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository.metadata;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Subgenre;
import reviewbot.repository.AbstractRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class SubgenreRepository extends AbstractRepository<Subgenre> {

    @Override
    public Subgenre create(Subgenre subgenre) {
        getCurrentSession().save(subgenre);
        return subgenre;
    }

    @Override
    public List<Subgenre> readAll() {
        return _entityManager.createQuery("from Subgenre").getResultList();
    }

    @Override
    public List<Subgenre> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<Subgenre> subgenreEntities = (List<Subgenre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Subgenre");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        List<Subgenre> subgenres = new ArrayList<Subgenre>();
        for (Subgenre subgenre : subgenreEntities) {
            subgenres.add(subgenre);
        }
        return subgenres;

    }


    @Override
    public Subgenre readOne(Integer id) {
        return readOneEntity(id);
    }

    public Subgenre readOneEntity(Integer id) {

        if (id == null)
            return new Subgenre();
        return (Subgenre) getCurrentSession().get(Subgenre.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subgenre> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Subgenre> subgenreEntities = (List<Subgenre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Subgenre.class);
                criteria.add(Restrictions.in("subgenre", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
        List<Subgenre> subgenres = new ArrayList<Subgenre>();
        for (Subgenre subgenre : subgenreEntities) {
            subgenres.add(subgenre);
        }
        return subgenres;
    }



    @Override
    public Subgenre update(Subgenre inSubgenre) {
        Subgenre subgenre = (Subgenre) getCurrentSession().get(Subgenre.class, inSubgenre.getSubgenre());

        subgenre.setName(inSubgenre.getName());
        subgenre.setDescription(inSubgenre.getDescription());

        getCurrentSession().merge(subgenre);
        return subgenre;
    }

    @Override
    public void delete(Integer id) {
        Subgenre subgenre = (Subgenre) getCurrentSession().get(Subgenre.class, id);
        if (subgenre != null) {
            getCurrentSession().delete(subgenre);
            getCurrentSession().flush();
        }
    }
    
}
