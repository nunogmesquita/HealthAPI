package HealthAPI.repository;

import HealthAPI.model.Role;
import HealthAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    User findByToken(String jwt);

    List<User> findByRole(Role healthcareprovider);
}