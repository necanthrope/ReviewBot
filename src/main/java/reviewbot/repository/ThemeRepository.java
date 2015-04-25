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
import reviewbot.dto.ThemeDTO;
import reviewbot.entity.ThemeEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class ThemeRepository extends AbstractRepository<Integer, Integer, ThemeEntity, ThemeDTO> {
    @Override
    public ThemeDTO create(ThemeDTO themeDTO) {
        return null;
    }

    @Override
    public ThemeDTO readOne(Integer id) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ThemeDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<ThemeEntity> themeEntities =  (List<ThemeEntity>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(ThemeEntity.class);
                criteria.add(Restrictions.in("theme", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<ThemeDTO> themeDTOs = new ArrayList<ThemeDTO>();
        for (ThemeEntity themeEntity : themeEntities) {
            themeDTOs.add(unwrap(themeEntity));
        }
        return themeDTOs;
    }

    @Override
    public List<ThemeDTO> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<ThemeDTO> readAll() {
        return null;
    }

    @Override
    public void update(ThemeDTO themeDTO) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    protected ThemeEntity wrap(ThemeDTO themeDTO) {
        ThemeEntity themeEntity = new ThemeEntity();

        themeEntity.setName(themeDTO.getName());
        themeEntity.setDescription(themeDTO.getDescription());

        return themeEntity;
    }

    @Override
    protected ThemeDTO unwrap(ThemeEntity themeEntity) {
        ThemeDTO themeDTO = new ThemeDTO();

        themeDTO.setId(themeEntity.getTheme());
        themeDTO.setName(themeEntity.getName());
        themeDTO.setDescription(themeEntity.getDescription());

        return themeDTO;
    }
}
