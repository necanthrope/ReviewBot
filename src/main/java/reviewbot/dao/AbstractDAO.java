package reviewbot.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by jtidwell on 4/7/2015.
 */
public abstract class AbstractDAO<T> extends HibernateDaoSupport{

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    protected EntityManager _entityManager;

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }

    public abstract void create(T args);

    public abstract void update(T args);

    public abstract void delete(T args);

}
