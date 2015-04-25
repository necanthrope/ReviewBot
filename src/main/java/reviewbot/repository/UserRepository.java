/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import reviewbot.dto.UserDTO;
import reviewbot.entity.UserEntity;

import javax.transaction.Transactional;

/**
 * Created by jtidwell on 4/20/2015.
 */
@Repository
@Transactional
public class UserRepository extends AbstractRepository<Integer, Integer, UserEntity, UserDTO> {
    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO readOne(Integer id) {
        UserEntity userEntity = (UserEntity) getCurrentSession().get(UserEntity.class, id);
        return unwrap(userEntity);
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
    protected UserEntity wrap(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        //userEntity.setPassword(userDTO.getPassword());
        userEntity.setForename(userDTO.getForename());
        userEntity.setSurname(userEntity.getSurname());
        userEntity.setAdmin(userEntity.getAdmin());

        return userEntity;
    }

    @Override
    protected UserDTO unwrap(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setForename(userEntity.getForename());
        userDTO.setSurname(userEntity.getSurname());
        userDTO.setAdmin(userEntity.getAdmin());

        return userDTO;
    }
}
