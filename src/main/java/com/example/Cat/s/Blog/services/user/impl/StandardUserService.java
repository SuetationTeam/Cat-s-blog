package com.example.Cat.s.Blog.services.user.impl;

import com.example.Cat.s.Blog.db.entity.roles.Role;
import com.example.Cat.s.Blog.db.entity.roles.RoleType;
import com.example.Cat.s.Blog.db.entity.users.User;
import com.example.Cat.s.Blog.db.repositories.RoleRepository;
import com.example.Cat.s.Blog.db.repositories.UserRepository;
import com.example.Cat.s.Blog.services.exceptions.ExistingUsernameException;
import com.example.Cat.s.Blog.services.exceptions.NonExistingUserException;
import com.example.Cat.s.Blog.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StandardUserService implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    /**
     * возвращает пользователя по заданному id
     *
     * @param id
     * @return User
     */
    @Override
    public User showById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new NonExistingUserException();
        }
        return foundUser.get();
    }

    /**
     * возвращает список всех пользователей
     *
     * @return список User
     */
    @Override
    public List<User> showAll() {
        return userRepository.findAll();
    }

    /**
     * Добавляет нового пользователя в базу данных
     */
    @Override
    public void add(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new ExistingUsernameException();
        }

        User addedUser = new User(
                username,
                password, roleRepository.findByName(RoleType.ROLE_USER));
        userRepository.saveAndFlush(addedUser);
    }

    /**
     * обновляет информацию  о пользователе по id
     *
     * @param id
     * @param username
     * @param userRole
     */
    @Override
    public void update(Long id, String username, Role userRole) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new NonExistingUserException();
        }
        User updatedUser = foundUser.get();
        //забабахать бы проверку на имя уникальное
        updatedUser.setUsername(username);
        updatedUser.setUserRole(userRole);
        userRepository.saveAndFlush(updatedUser);
    }

    /**
     * удаляет пользователя по id
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new NonExistingUserException();
        }
        userRepository.deleteById(id);
    }

}
