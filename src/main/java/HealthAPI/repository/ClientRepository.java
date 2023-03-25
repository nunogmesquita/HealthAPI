package HealthAPI.repository;

import HealthAPI.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    Client findByTokens(String jwt);

    List<Client> findByDeletedFalse();

    Optional<Client> findById(Long id);

}