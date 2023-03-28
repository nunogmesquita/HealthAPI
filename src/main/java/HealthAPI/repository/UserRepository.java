package HealthAPI.repository;

import HealthAPI.model.Role;
import HealthAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String username);

    User findByTokens(String jwt);

    List<User> findByRole(Role healthcareprovider);

    List<User> findByDeletedFalse();

    List<User> findByDeletedTrue();

    Optional<User> findByIdAndDeletedFalse(Long id);

    List<User> findByRoleAndDeleted(Role healthcareprovider, boolean deleted);
}