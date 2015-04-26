package reviewbot.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import reviewbot.dto.metadata.FormatDTO;
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
public class FormatRepository  extends AbstractRepository<Integer, Integer, Format, FormatDTO> {

    @Override
    public FormatDTO create(FormatDTO formatDTO) {
        Format format = wrap(formatDTO);
        getCurrentSession().save(format);
        return unwrap(format);
    }

    @Override
    public List<FormatDTO> readAll() {
        List<Format> formatEntities = _entityManager.createQuery("from Format").getResultList();
        List<FormatDTO> formatDTOs = new ArrayList<FormatDTO>();
        for (Format format : formatEntities) {
            formatDTOs.add(unwrap(format));
        }
        return formatDTOs;
    }

    @Override
    public List<FormatDTO> readRange(Integer offset, Integer length) {

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

        List<FormatDTO> formatDTOs = new ArrayList<FormatDTO>();
        for (Format format : formatEntities) {
            formatDTOs.add(unwrap(format));
        }
        return formatDTOs;

    }

    @Override
    public FormatDTO readOne(Integer id) {
        return unwrap(readOneEntity(id));
    }

    public Format readOneEntity(Integer id) {

        if (id == null)
            return new Format();
        return (Format) getCurrentSession().get(Format.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FormatDTO> readList(Integer[] idsIn) {
        final Integer[] ids = idsIn;
        List<Format> formatEntities = (List<Format>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Format.class);
                criteria.add(Restrictions.in("id", ids));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });

        List<FormatDTO> formatDTOs = new ArrayList<FormatDTO>();
        for (Format format : formatEntities) {
            formatDTOs.add(unwrap(format));
        }
        return formatDTOs;
    }

    @Override
    public void update(FormatDTO formatDTO) {
        Format format = (Format) getCurrentSession().get(Format.class, formatDTO.getId());

        format.setName(formatDTO.getName());
        format.setDescription(formatDTO.getDescription());

        getCurrentSession().merge(format);
    }

    @Override
    public void delete(Integer id) {
        Format format = (Format) getCurrentSession().get(Format.class, id);
        if (format != null) {
            getCurrentSession().delete(format);
            getCurrentSession().flush();
        }
    }

    @Override
    protected Format wrap(FormatDTO formatDTO) {
        Format format = new Format();

        format.setName(formatDTO.getName());
        format.setDescription(formatDTO.getDescription());

        return format;
    }

    @Override
    protected FormatDTO unwrap(Format format) {
        FormatDTO formatDTO = new FormatDTO();

        formatDTO.setId(format.getFormat());
        formatDTO.setName(format.getName());
        formatDTO.setDescription(format.getDescription());

        return formatDTO;
    }

    public GenreMap wrapMapping (Book book, FormatDTO formatDTO) {
        GenreMap genreMap = new GenreMap();
        genreMap.setBook(book);
        genreMap.setFormat(readOneEntity(formatDTO.getId()));
        return genreMap;
    }

}