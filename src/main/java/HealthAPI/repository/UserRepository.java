package HealthAPI.repository;

import HealthAPI.model.Role;
import HealthAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoleAndDeletedFalse(Role healthcareprovider);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String username);

    List<User> findByDeleted(Boolean deleted);

    Optional<User> findByIdAndDeletedFalse(Long id);

    Optional<User> findByIdAndDeletedTrue(Long userId);

}