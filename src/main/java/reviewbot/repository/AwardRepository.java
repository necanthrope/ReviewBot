package reviewbot.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.metadata.AwardDTO;
import reviewbot.entity.metadata.Award;
import reviewbot.repository.AbstractRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/25/2015.
 */
@Repository
@Transactional
public class AwardRepository  extends AbstractRepository<Integer, Integer, Award, AwardDTO>{

    @Override
    public AwardDTO create(AwardDTO awardDTO) {
        Award award = wrap(awardDTO);
        getCurrentSession().save(award);
        return unwrap(award);
    }

    @Override
    public List<AwardDTO> readAll() {
        List<Award> awardEntities = _entityManager.createQuery("from Award").getResultList();
        List<AwardDTO> awardDTOs = new ArrayList<AwardDTO>();
        for (Award award : awardEntities) {
            awardDTOs.add(unwrap(award));
        }
        return awardDTOs;
    }

    @Override
    public List<AwardDTO> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<Award> awardEntities = (List<Award>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Award");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        List<AwardDTO> awardDTOs = new ArrayList<AwardDTO>();
        for (Award award : awardEntities) {
            awardDTOs.add(unwrap(award));
        }
        return awardDTOs;

    }

    @Override
    public AwardDTO readOne(Integer id) {
        return unwrap(readOneEntity(id));
    }

    public Award readOneEntity(Integer id) {

        if (id == null)
            return new Award();
        return (Award) getCurrentSession().get(Award.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AwardDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Award> awardEntities = (List<Award>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Award.class);
                criteria.add(Restrictions.in("id", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<AwardDTO> awardDTOs = new ArrayList<AwardDTO>();
        for (Award award : awardEntities) {
            awardDTOs.add(unwrap(award));
        }
        return awardDTOs;
    }

    @Override
    public void update(AwardDTO awardDTO) {
        Award award = (Award) getCurrentSession().get(Award.class, awardDTO.getId());

        award.setName(awardDTO.getName());
        award.setDescription(awardDTO.getDescription());

        getCurrentSession().merge(award);
    }

    @Override
    public void delete(Integer id) {
        Award award = (Award) getCurrentSession().get(Award.class, id);
        if (award != null) {
            getCurrentSession().delete(award);
            getCurrentSession().flush();
        }
    }

    @Override
    protected Award wrap(AwardDTO awardDTO) {
        Award award = new Award();

        award.setName(awardDTO.getName());
        award.setDescription(awardDTO.getDescription());

        return award;
    }

    @Override
    protected AwardDTO unwrap(Award award) {
        AwardDTO awardDTO = new AwardDTO();

        awardDTO.setId(award.getAward());
        awardDTO.setName(award.getName());
        awardDTO.setDescription(award.getDescription());

        return awardDTO;
    }




}
