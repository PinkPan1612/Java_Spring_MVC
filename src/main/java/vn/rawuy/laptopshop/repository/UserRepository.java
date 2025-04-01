package vn.rawuy.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.rawuy.laptopshop.domain.User;

// crud: create, read, update, delete
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User rawuy);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    User findOneById(long id);

    void deleteById(long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
