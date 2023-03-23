package HealthAPI.repository;

import HealthAPI.model.Appointment;
import HealthAPI.model.Status;
import HealthAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByTimeSlotIdAndHcpIdAndClientId(Long id, Long hcpId, Long clientId);

    List<Appointment> findAllByTimeSlotIdAndHcpId(Long timeSlotId, Long hcpId);

    List<Appointment> findAllByClientIdAndSTATUS(Long clientId, Status active);

    List<Appointment> findAllByHcpIdAndSTATUS(Long hcpId, Status active);
}