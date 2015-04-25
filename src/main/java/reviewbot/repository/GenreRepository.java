/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.GenreDTO;
import reviewbot.entity.GenreEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Repository
@Transactional
public class GenreRepository extends AbstractRepository<Integer, Integer, GenreEntity, GenreDTO> {

    @Override
    public GenreDTO create(GenreDTO genreDTO) {
        GenreEntity genreEntity = wrap(genreDTO);
        getCurrentSession().save(genreEntity);
        return unwrap(genreEntity);
    }

    @Override
    public List<GenreDTO> readAll() {
        List<GenreEntity> genreEntities = _entityManager.createQuery("from Genre").getResultList();
        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        for (GenreEntity genreEntity : genreEntities) {
            genreDTOs.add(unwrap(genreEntity));
        }
        return genreDTOs;
    }

    @Override
    public List<GenreDTO> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<GenreEntity> genreEntities = (List<GenreEntity>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Genre");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        for (GenreEntity genreEntity : genreEntities) {
            genreDTOs.add(unwrap(genreEntity));
        }
        return genreDTOs;

    }

    @Override
    public GenreDTO readOne(Integer id) {
        return unwrap(readOneEntity(id));
    }

    public GenreEntity readOneEntity(Integer id) {

        if (id == null)
            return new GenreEntity();
        return (GenreEntity) getCurrentSession().get(GenreEntity.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GenreDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<GenreEntity> genreEntities = (List<GenreEntity>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(GenreEntity.class);
                criteria.add(Restrictions.in("id",ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<GenreDTO> genreDTOs = new ArrayList<GenreDTO>();
        for (GenreEntity genreEntity : genreEntities) {
            genreDTOs.add(unwrap(genreEntity));
        }
        return genreDTOs;
    }

    @Override
    public void update(GenreDTO genreDTO) {
        GenreEntity genreEntity = (GenreEntity) getCurrentSession().get(GenreEntity.class, genreDTO.getId());

        genreEntity.setName(genreDTO.getName());
        genreEntity.setDescription(genreDTO.getDescription());

        getCurrentSession().merge(genreEntity);
    }

    @Override
    public void delete(Integer id) {
        GenreEntity genreEntity = (GenreEntity) getCurrentSession().get(GenreEntity.class, id);
        if (genreEntity != null) {
            getCurrentSession().delete(genreEntity);
            getCurrentSession().flush();
        }
    }

    @Override
    protected GenreEntity wrap(GenreDTO genreDTO) {
        GenreEntity genreEntity = new GenreEntity();

        genreEntity.setName(genreDTO.getName());
        genreEntity.setDescription(genreDTO.getDescription());

        return genreEntity;
    }

    @Override
    protected GenreDTO unwrap(GenreEntity genreEntity) {
        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId(genreEntity.getId());
        genreDTO.setName(genreEntity.getName());
        genreDTO.setDescription(genreEntity.getDescription());

        return genreDTO;
    }

}
