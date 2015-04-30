/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 *  Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reviewbot.dto.UserDTO;
import reviewbot.entity.User;
import reviewbot.repository.UserRepository;

import java.util.List;

/**
 * Created by jtidwell on 4/30/2015.
 */
@Service
public class UserService extends AbstractService<User, UserDTO>{

    @Autowired
    private UserRepository _userRepository;

    @Override
    public UserDTO create(UserDTO dto) {
        return null;
    }

    @Override
    public UserDTO readOne(Long id) {
        return unwrap(_userRepository.readOne(id.intValue()));
    }

    @Override
    public List<UserDTO> readList(Long[] ids) {
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
    public UserDTO update(UserDTO dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public User wrap(UserDTO userDTO) {
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
    public UserDTO unwrap(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setForename(user.getForename());
        userDTO.setSurname(user.getSurname());
        userDTO.setAdmin(user.getAdmin());

        return userDTO;
    }

}
