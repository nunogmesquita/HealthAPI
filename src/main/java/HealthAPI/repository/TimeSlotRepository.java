package HealthAPI.repository;

import HealthAPI.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> getAllByHcpId(Long currentHcpId);
}