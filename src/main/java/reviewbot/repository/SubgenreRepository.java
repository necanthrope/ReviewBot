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
import reviewbot.dto.SubgenreDTO;
import reviewbot.entity.SubgenreEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class SubgenreRepository extends AbstractRepository<Integer, Integer, SubgenreEntity, SubgenreDTO> {

    @Override
    public SubgenreDTO create(SubgenreDTO subgenreDTO) {
        return null;
    }

    @Override
    public SubgenreDTO readOne(Integer id) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SubgenreDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<SubgenreEntity> subgenreEntities = (List<SubgenreEntity>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(SubgenreEntity.class);
                criteria.add(Restrictions.in("subgenre", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
        List<SubgenreDTO> subgenreDTOs = new ArrayList<SubgenreDTO>();
        for (SubgenreEntity subgenreEntity : subgenreEntities) {
            subgenreDTOs.add(unwrap(subgenreEntity));
        }
        return subgenreDTOs;
    }

    @Override
    public List<SubgenreDTO> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<SubgenreDTO> readAll() {
        return null;
    }

    @Override
    public void update(SubgenreDTO subgenre) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    protected SubgenreEntity wrap(SubgenreDTO subgenreDTO) {
        SubgenreEntity subgenreEntity = new SubgenreEntity();

        subgenreEntity.setName(subgenreDTO.getName());
        subgenreEntity.setDescription(subgenreDTO.getDescription());

        return subgenreEntity;
    }

    @Override
    protected SubgenreDTO unwrap(SubgenreEntity subgenreEntity) {
        SubgenreDTO subgenreDTO = new SubgenreDTO();

        subgenreDTO.setId(subgenreEntity.getSubgenre());
        subgenreDTO.setName(subgenreEntity.getName());
        subgenreDTO.setDescription(subgenreEntity.getDescription());

        return subgenreDTO;
    }
}
