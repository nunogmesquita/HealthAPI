package HealthAPI.repository;

import HealthAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<User, Long> {
}