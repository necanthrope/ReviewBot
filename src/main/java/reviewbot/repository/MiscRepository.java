package reviewbot.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.metadata.MiscDTO;
import reviewbot.entity.metadata.Misc;
import reviewbot.repository.AbstractRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/25/2015.
 */
@Repository
@Transactional
public class MiscRepository  extends AbstractRepository<Integer, Integer, Misc, MiscDTO> {

    @Override
    public MiscDTO create(MiscDTO miscDTO) {
        Misc misc = wrap(miscDTO);
        getCurrentSession().save(misc);
        return unwrap(misc);
    }

    @Override
    public List<MiscDTO> readAll() {
        List<Misc> miscEntities = _entityManager.createQuery("from Misc").getResultList();
        List<MiscDTO> miscDTOs = new ArrayList<MiscDTO>();
        for (Misc misc : miscEntities) {
            miscDTOs.add(unwrap(misc));
        }
        return miscDTOs;
    }

    @Override
    public List<MiscDTO> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<Misc> miscEntities = (List<Misc>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Misc");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        List<MiscDTO> miscDTOs = new ArrayList<MiscDTO>();
        for (Misc misc : miscEntities) {
            miscDTOs.add(unwrap(misc));
        }
        return miscDTOs;

    }

    @Override
    public MiscDTO readOne(Integer id) {
        return unwrap(readOneEntity(id));
    }

    public Misc readOneEntity(Integer id) {

        if (id == null)
            return new Misc();
        return (Misc) getCurrentSession().get(Misc.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MiscDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Misc> miscEntities = (List<Misc>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Misc.class);
                criteria.add(Restrictions.in("id", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<MiscDTO> miscDTOs = new ArrayList<MiscDTO>();
        for (Misc misc : miscEntities) {
            miscDTOs.add(unwrap(misc));
        }
        return miscDTOs;
    }

    @Override
    public void update(MiscDTO miscDTO) {
        Misc misc = (Misc) getCurrentSession().get(Misc.class, miscDTO.getId());

        misc.setName(miscDTO.getName());
        misc.setDescription(miscDTO.getDescription());

        getCurrentSession().merge(misc);
    }

    @Override
    public void delete(Integer id) {
        Misc misc = (Misc) getCurrentSession().get(Misc.class, id);
        if (misc != null) {
            getCurrentSession().delete(misc);
            getCurrentSession().flush();
        }
    }

    @Override
    protected Misc wrap(MiscDTO miscDTO) {
        Misc misc = new Misc();

        misc.setName(miscDTO.getName());
        misc.setDescription(miscDTO.getDescription());

        return misc;
    }

    @Override
    protected MiscDTO unwrap(Misc misc) {
        MiscDTO miscDTO = new MiscDTO();

        miscDTO.setId(misc.getMisc());
        miscDTO.setName(misc.getName());
        miscDTO.setDescription(misc.getDescription());

        return miscDTO;
    }

}