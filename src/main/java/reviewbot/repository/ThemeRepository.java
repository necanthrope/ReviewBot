/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class ThemeRepository extends AbstractRepository<Integer, Integer, Theme, ThemeDTO> {
    @Override
    public ThemeDTO create(ThemeDTO themeDTO) {
        Theme theme = wrap(themeDTO);
        getCurrentSession().save(theme);
        return unwrap(theme);
    }

    @Override
    public List<ThemeDTO> readAll() {
        List<Theme> themeEntities = _entityManager.createQuery("from Theme").getResultList();
        List<ThemeDTO> themeDTOs = new ArrayList<ThemeDTO>();
        for (Theme theme : themeEntities) {
            themeDTOs.add(unwrap(theme));
        }
        return themeDTOs;
    }

    @Override
    public List<ThemeDTO> readRange(Integer offset, Integer length) {

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

        List<ThemeDTO> themeDTOs = new ArrayList<ThemeDTO>();
        for (Theme theme : themeEntities) {
            themeDTOs.add(unwrap(theme));
        }
        return themeDTOs;

    }

    @Override
    public ThemeDTO readOne(Integer id) {
        return unwrap(readOneEntity(id));
    }

    public Theme readOneEntity(Integer id) {

        if (id == null)
            return new Theme();
        return (Theme) getCurrentSession().get(Theme.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ThemeDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Theme> themeEntities = (List<Theme>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Theme.class);
                criteria.add(Restrictions.in("id",ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<ThemeDTO> themeDTOs = new ArrayList<ThemeDTO>();
        for (Theme theme : themeEntities) {
            themeDTOs.add(unwrap(theme));
        }
        return themeDTOs;
    }

    @Override
    public void update(ThemeDTO themeDTO) {
        Theme theme = (Theme) getCurrentSession().get(Theme.class, themeDTO.getId());

        theme.setName(themeDTO.getName());
        theme.setDescription(themeDTO.getDescription());

        getCurrentSession().merge(theme);
    }

    @Override
    public void delete(Integer id) {
        Theme theme = (Theme) getCurrentSession().get(Theme.class, id);
        if (theme != null) {
            getCurrentSession().delete(theme);
            getCurrentSession().flush();
        }
    }

    @Override
    protected Theme wrap(ThemeDTO themeDTO) {
        Theme theme = new Theme();

        theme.setName(themeDTO.getName());
        theme.setDescription(themeDTO.getDescription());

        return theme;
    }

    @Override
    protected ThemeDTO unwrap(Theme theme) {
        ThemeDTO themeDTO = new ThemeDTO();

        themeDTO.setId(theme.getTheme());
        themeDTO.setName(theme.getName());
        themeDTO.setDescription(theme.getDescription());

        return themeDTO;
    }

    public GenreMap wrapMapping (Book book, ThemeDTO themeDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setTheme(readOneEntity(themeDTO.getId()));
        return genreMap;
    }

}
