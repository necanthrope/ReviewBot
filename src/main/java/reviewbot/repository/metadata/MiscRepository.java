package reviewbot.repository.metadata;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.metadata.MiscDTO;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
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
public class MiscRepository  extends AbstractRepository<Misc> {

    @Override
    public Misc create(Misc misc) {
        getCurrentSession().save(misc);
        return misc;
    }

    @Override
    public List<Misc> readAll() {
        return _entityManager.createQuery("from Misc").getResultList();
    }

    @Override
    public List<Misc> readRange(Integer offset, Integer length) {

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

        return miscEntities;

    }

    @Override
    public Misc readOne(Integer id) {
        return (Misc) getCurrentSession().get(Misc.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Misc> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Misc> miscEntities = (List<Misc>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Misc.class);
                criteria.add(Restrictions.in("id", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        return miscEntities;
    }

    @Override
    public Misc update(Misc inMisc) {
        Misc misc = (Misc) getCurrentSession().get(Misc.class, inMisc.getMisc());

        misc.setName(inMisc.getName());
        misc.setDescription(inMisc.getDescription());

        getCurrentSession().merge(misc);
        return misc;
    }

    @Override
    public void delete(Integer id) {
        Misc misc = (Misc) getCurrentSession().get(Misc.class, id);
        if (misc != null) {
            getCurrentSession().delete(misc);
            getCurrentSession().flush();
        }
    }

}