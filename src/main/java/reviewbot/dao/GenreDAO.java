package reviewbot.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import reviewbot.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Repository
@Transactional
public class GenreDAO extends AbstractDAO<Genre> {


    @Override
    public void create(Genre genre) {

    }

    @Override
    public void update(Genre genre) {

    }

    @Override
    public void delete(Genre genre) {

    }

}
