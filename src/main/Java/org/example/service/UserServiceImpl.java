package org.example.service;

import org.example.dao.UserDao;
import org.example.model.Role;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User justSaveUser(User user) {
        return userDao.justSaveUser(user);
    }

    @Transactional
    @Override
    public User registerUser(User user, Set<Role> rolesFromCheckbox) { //метод для сохранения новых юзеров
        User userFromDb = userDao.getUserByName(user.getUsername());

        if (userFromDb == null) {
            user.setRoles(rolesFromCheckbox);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.justSaveUser(user);

        } else {
            try {
                throw new Exception("User already exists");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }


    @Transactional
    @Override
    public User updateUser(User user, Set<Role> roles) { //метод для редактирования user-данных

        user.setRoles(roles); //роли должны быть после merge(updateRole)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User afterUpdateUser = new User();
        try {
            afterUpdateUser = userDao.updateUser(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
        return afterUpdateUser;
    }

    @Transactional
    @Override
    public List<User> usersList() {
        return userDao.usersList();
    }

    @Transactional
    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return user;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
