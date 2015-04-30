package reviewbot.repository.metadata;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.entity.Book;
import reviewbot.entity.GenreMap;
import reviewbot.entity.metadata.Format;
import reviewbot.repository.AbstractRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtidwell on 4/25/2015.
 */
@Repository
@Transactional
public class FormatRepository  extends AbstractRepository<Format> {

    @Override
    public Format create(Format format) {
        getCurrentSession().save(format);
        return format;
    }

    @Override
    public List<Format> readAll() {
        return _entityManager.createQuery("from Format").getResultList();
    }

    @Override
    public List<Format> readRange(Integer offset, Integer length) {

        final Integer len = length;
        final Integer offs = offset;

        List<Format> formatEntities = (List<Format>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query q = getSessionFactory().getCurrentSession().createQuery("from Format");
                q.setFirstResult(offs);
                q.setMaxResults(len);
                return q.list();
            }
        });

        return formatEntities;

    }

    @Override
    public Format readOne(Integer id) {
        return (Format) getCurrentSession().get(Format.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Format> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Format> formatEntities = (List<Format>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Format.class);
                criteria.add(Restrictions.in("id", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        return formatEntities;
    }

    @Override
    public Format update(Format inFormat) {
        Format format = (Format) getCurrentSession().get(Format.class, inFormat.getFormat());

        format.setName(inFormat.getName());
        format.setDescription(inFormat.getDescription());

        getCurrentSession().merge(format);
        return format;
    }

    @Override
    public void delete(Integer id) {
        Format format = (Format) getCurrentSession().get(Format.class, id);
        if (format != null) {
            getCurrentSession().delete(format);
            getCurrentSession().flush();
        }
    }


}