/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.dao;

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
public abstract class AbstractDAO<I, L, T> extends HibernateDaoSupport{

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

    public abstract T create(T args);

    public abstract T readOne(L id);

    public abstract List<T> readList(L[] ids);

    public abstract List<T> readRange(I offset, I length);

    public abstract List<T> readAll();

    public abstract void update(T args);

    public abstract void delete(I args);

}
