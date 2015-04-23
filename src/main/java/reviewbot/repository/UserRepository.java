/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import reviewbot.dto.UserDTO;
import reviewbot.entity.User;

import javax.transaction.Transactional;

/**
 * Created by jtidwell on 4/20/2015.
 */
@Repository
@Transactional
public class UserRepository extends AbstractRepository<Integer, Integer, User, UserDTO> {
    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO readOne(Integer id) {
        User user = (User) getCurrentSession().get(User.class, id);
        return unwrap(user);
    }

    @Override
    public List<UserDTO> readList(Integer[] ids) {
        return null;
    }

    @Override
    public List<UserDTO> readRange(Integer offset, Integer length) {
        return null;
    }

    @Override
    public List<UserDTO> readAll() {
        return null;
    }

    @Override
    public void update(UserDTO userDTO) {

    }

    @Override
    public void delete(Integer args) {

    }

    @Override
    protected User wrap(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        //user.setPassword(userDTO.getPassword());
        user.setForename(userDTO.getForename());
        user.setSurname(user.getSurname());
        user.setAdmin(user.getAdmin());

        return user;
    }

    @Override
    protected UserDTO unwrap(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setForename(user.getForename());
        userDTO.setSurname(user.getSurname());
        userDTO.setAdmin(user.getAdmin());

        return userDTO;
    }
}
