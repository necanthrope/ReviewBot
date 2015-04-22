/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.entity.Subgenre;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class SubgenreRepository extends AbstractRepository<Integer, Integer, Subgenre> {

    @Override
    public Subgenre create(Subgenre subgenre) {
        return null;
    }

    @Override
    public Subgenre readOne(Integer id) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subgenre> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        return (List<Subgenre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Subgenre.class);
                criteria.add(Restrictions.in("subgenre", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
    }

    @Override
    public List<Subgenre> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<Subgenre> readAll() {
        return null;
    }

    @Override
    public void update(Subgenre subgenre) {

    }

    @Override
    public void delete(Integer id) {

    }
}
