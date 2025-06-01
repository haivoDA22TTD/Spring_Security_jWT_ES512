package spring.boot.rsa512.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.rsa512.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
