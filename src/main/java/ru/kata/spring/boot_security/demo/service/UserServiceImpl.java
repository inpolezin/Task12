package ru.kata.spring.boot_security.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user, List<Long> roleIds) {
        List<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            roles.add(roleRepository.findById(roleId).get());
        }
        user.setRoles(roles);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void updateUser(User user, List<Long> roleIds) {
        List<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            roles.add(roleRepository.findById(roleId).get());
        }
        User userDb = findUserById(user.getId());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setEmail(user.getEmail());
        userDb.setAge(user.getAge());
        userDb.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDb.setRoles(roles);
        userRepository.save(userDb);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByFirstname(String username) {
        return userRepository.findByFirstName(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        Optional<User> userOptional = userRepository.findByFirstName(s);

        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            return userOptional.get();
        }
    }

    @PostConstruct
    @Override
    public void init() {

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        if(roleRepository.count() == 0) {
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
            List<Role> rolesForAdmin = new ArrayList<>();
            List<Role> rolesForUser = new ArrayList<>();
            rolesForAdmin.add(roleAdmin);
            rolesForAdmin.add(roleUser);
            rolesForUser.add(roleUser);
            User admin = new User();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setAge(21);
            admin.setEmail("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("123"));
            admin.setRoles(rolesForAdmin);
            User user = new User ();
            user.setFirstName("user");
            user.setLastName("user");
            user.setAge(21);
            user.setEmail("user");
            user.setPassword(new BCryptPasswordEncoder().encode("123"));
            user.setRoles(rolesForUser);
            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}
