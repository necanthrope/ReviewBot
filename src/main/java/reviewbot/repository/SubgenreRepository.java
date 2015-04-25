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
import reviewbot.dto.metadata.SubgenreDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Subgenre;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/8/2015.
 */
@Repository
@Transactional
public class SubgenreRepository extends AbstractRepository<Integer, Integer, Subgenre, SubgenreDTO>{

    @Override
    public SubgenreDTO create(SubgenreDTO subgenreDTO) {
        Subgenre subgenre = wrap(subgenreDTO);
        getCurrentSession().save(subgenre);
        return unwrap(subgenre);
    }

    @Override
    public List<SubgenreDTO> readAll() {
        List<Subgenre> subgenreEntities = _entityManager.createQuery("from Subgenre").getResultList();
        List<SubgenreDTO> subgenreDTOs = new ArrayList<SubgenreDTO>();
        for (Subgenre subgenre : subgenreEntities) {
            subgenreDTOs.add(unwrap(subgenre));
        }
        return subgenreDTOs;
    }

    @Override
    public List<SubgenreDTO> readRange(Integer offset, Integer length) {

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

        List<SubgenreDTO> subgenreDTOs = new ArrayList<SubgenreDTO>();
        for (Subgenre subgenre : subgenreEntities) {
            subgenreDTOs.add(unwrap(subgenre));
        }
        return subgenreDTOs;

    }


    @Override
    public SubgenreDTO readOne(Integer id) {
        return unwrap(readOneEntity(id));
    }

    public Subgenre readOneEntity(Integer id) {

        if (id == null)
            return new Subgenre();
        return (Subgenre) getCurrentSession().get(Subgenre.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SubgenreDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Subgenre> subgenreEntities = (List<Subgenre>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Subgenre.class);
                criteria.add(Restrictions.in("subgenre", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
        List<SubgenreDTO> subgenreDTOs = new ArrayList<SubgenreDTO>();
        for (Subgenre subgenre : subgenreEntities) {
            subgenreDTOs.add(unwrap(subgenre));
        }
        return subgenreDTOs;
    }



    @Override
    public void update(SubgenreDTO subgenreDTO) {
        Subgenre subgenre = (Subgenre) getCurrentSession().get(Subgenre.class, subgenreDTO.getId());

        subgenre.setName(subgenreDTO.getName());
        subgenre.setDescription(subgenreDTO.getDescription());

        getCurrentSession().merge(subgenre);
    }

    @Override
    public void delete(Integer id) {
        Subgenre subgenre = (Subgenre) getCurrentSession().get(Subgenre.class, id);
        if (subgenre != null) {
            getCurrentSession().delete(subgenre);
            getCurrentSession().flush();
        }
    }

    @Override
    protected Subgenre wrap(SubgenreDTO subgenreDTO) {
        Subgenre subgenre = new Subgenre();

        subgenre.setName(subgenreDTO.getName());
        subgenre.setDescription(subgenreDTO.getDescription());

        return subgenre;
    }

    @Override
    protected SubgenreDTO unwrap(Subgenre subgenre) {
        SubgenreDTO subgenreDTO = new SubgenreDTO();

        subgenreDTO.setId(subgenre.getSubgenre());
        subgenreDTO.setName(subgenre.getName());
        subgenreDTO.setDescription(subgenre.getDescription());

        return subgenreDTO;
    }

    public GenreMap wrapMapping (Book book, SubgenreDTO subgenreDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setSubgenre(readOneEntity(subgenreDTO.getId()));
        return genreMap;
    }
}
