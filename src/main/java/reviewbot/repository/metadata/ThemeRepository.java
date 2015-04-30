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
import reviewbot.dto.metadata.ThemeDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Theme;
import reviewbot.repository.AbstractRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class ThemeRepository extends AbstractRepository<Theme> {
    @Override
    public Theme create(Theme theme) {
        getCurrentSession().save(theme);
        return theme;
    }

    @Override
    public List<Theme> readAll() {
        return _entityManager.createQuery("from Theme").getResultList();
    }

    @Override
    public List<Theme> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<Theme> themeEntities = (List<Theme>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Theme");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        return themeEntities;

    }

    @Override
    public Theme readOne(Integer id) {
        return (Theme) getCurrentSession().get(Theme.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Theme> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Theme> themeEntities = (List<Theme>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Theme.class);
                criteria.add(Restrictions.in("id",ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        return themeEntities;
    }

    @Override
    public Theme update(Theme inTheme) {
        Theme theme = (Theme) getCurrentSession().get(Theme.class, inTheme.getTheme());

        theme.setName(inTheme.getName());
        theme.setDescription(inTheme.getDescription());

        getCurrentSession().merge(theme);
        return theme;
    }

    @Override
    public void delete(Integer id) {
        Theme theme = (Theme) getCurrentSession().get(Theme.class, id);
        if (theme != null) {
            getCurrentSession().delete(theme);
            getCurrentSession().flush();
        }
    }


}
