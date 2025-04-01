package vn.rawuy.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.rawuy.laptopshop.domain.Role;
import vn.rawuy.laptopshop.domain.User;
import vn.rawuy.laptopshop.domain.dto.RegisterDTO;
import vn.rawuy.laptopshop.repository.CartRepository;
import vn.rawuy.laptopshop.repository.RoleRepository;
import vn.rawuy.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, CartRepository cartRepo) {
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

    // mapper
    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User newUser = new User();
        newUser.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(registerDTO.getPassword());
        return newUser;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
