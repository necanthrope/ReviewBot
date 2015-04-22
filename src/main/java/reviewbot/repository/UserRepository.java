/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import reviewbot.entity.User;

import javax.transaction.Transactional;

/**
 * Created by jtidwell on 4/20/2015.
 */
@Repository
@Transactional
public class UserRepository extends AbstractRepository<Integer, Integer, User> {
    @Override
    public User create(User args) {
        return null;
    }

    @Override
    public User readOne(Integer id) {
        User user;
        user = (User) getCurrentSession().get(User.class, id);
        return user;
    }

    @Override
    public List<User> readList(Integer[] ids) {
        return null;
    }

    @Override
    public List<User> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public void update(User args) {

    }

    @Override
    public void delete(Integer args) {

    }
}