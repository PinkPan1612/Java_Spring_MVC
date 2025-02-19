package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    // get all users
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // get user by email
    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    // save user
    public User handleSaveUser(@Valid User user) {
        User eric = this.userRepository.save(user);
        System.out.println(eric.toString());
        return eric;
    }

    // get detail user
    public User getUserById(long id) {
        return this.userRepository.findOneById(id);
    }

    // update user
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    // delete user
    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }
}
