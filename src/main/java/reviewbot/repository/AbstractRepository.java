/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by jtidwell on 4/7/2015.
 */
public abstract class AbstractRepository<E> extends HibernateDaoSupport{

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    protected EntityManager _entityManager;

    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }

    protected Session getCurrentSession(){
        return _entityManager.unwrap(Session.class);
    }

    public abstract E create(E args);

    public abstract E readOne(Integer id);

    public abstract List<E> readList(Integer[] ids);

    public abstract List<E> readRange(Integer offset, Integer length);

    public abstract List<E> readAll();

    public abstract E update(E args);

    public abstract void delete(Integer args);
}
