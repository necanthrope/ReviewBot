package reviewbot.repository.metadata;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.metadata.AwardDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
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
public class AwardRepository  extends AbstractRepository<Award>{

    @Override
    public Award create(Award award) {
        getCurrentSession().save(award);
        return award;
    }

    @Override
    public List<Award> readAll() {
        return _entityManager.createQuery("from Award").getResultList();
    }

    @Override
    public List<Award> readRange(Integer offset, Integer length) {

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

        return awardEntities;

    }

    @Override
    public Award readOne(Integer id) {
        return (Award) getCurrentSession().get(Award.class, id);
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<Award> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Award> awardEntities = (List<Award>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Award.class);
                criteria.add(Restrictions.in("id", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
        return awardEntities;
    }

    @Override
    public Award update(Award inAward) {
        Award award = (Award) getCurrentSession().get(Award.class, inAward.getAward());

        award.setName(inAward.getName());
        award.setDescription(inAward.getDescription());

        getCurrentSession().merge(award);

        return award;
    }

    @Override
    public void delete(Integer id) {
        Award award = (Award) getCurrentSession().get(Award.class, id);
        if (award != null) {
            getCurrentSession().delete(award);
            getCurrentSession().flush();
        }
    }

}
