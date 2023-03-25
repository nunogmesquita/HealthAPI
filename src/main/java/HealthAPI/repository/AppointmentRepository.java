package HealthAPI.repository;

import HealthAPI.model.Appointment;
import HealthAPI.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByClientIdAndSTATUS(Long clientId, Status active);

    List<Appointment> findAllByUserIdAndSTATUS(Long hcpId, Status active);

}