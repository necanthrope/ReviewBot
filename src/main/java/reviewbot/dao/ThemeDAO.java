/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.entity.Theme;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class ThemeDAO  extends AbstractDAO<Integer, Long, Theme>{
    @Override
    public void create(Theme theme) {
        
    }

    @Override
    public Theme readOne(Long id) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Theme> readList(Long[] idsIn) {
        final Long[] ids = idsIn;
        return (List<Theme>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Theme.class);
                criteria.add(Restrictions.in("theme", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
    }

    @Override
    public List<Theme> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<Theme> readAll() {
        return null;
    }

    @Override
    public void update(Theme theme) {

    }

    @Override
    public void delete(Theme theme) {

    }
}
