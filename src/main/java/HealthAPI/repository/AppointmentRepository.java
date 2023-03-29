package HealthAPI.repository;

import HealthAPI.model.Appointment;
import HealthAPI.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByIdAndStatus(Long id, Status status);

    List<Appointment> findAllByUser_IdAndStatus(Long userId, Status status);

    List<Appointment> findAllByClient_IdAndStatus(Long clientId, Status status);

}