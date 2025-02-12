package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User handleSaveUser(User user) {
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
}
