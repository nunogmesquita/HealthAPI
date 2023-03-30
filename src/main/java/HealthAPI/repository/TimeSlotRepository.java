package HealthAPI.repository;

import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    Page<TimeSlot> findByIsBookedFalse(Pageable pageable);

    Page<TimeSlot> findByUser_IdAndIsBookedFalse(Long userId, Pageable pageable);

    Page<TimeSlot> findByUser_SpecialityAndIsBookedFalse(Speciality speciality, Pageable pageable);

    Optional<TimeSlot> findById(Long id);

    void deleteAllByUser_Id(Long userId);

    Optional<TimeSlot> findByStartTimeAndUser_Id(LocalDateTime startTime, Long id);

    Optional<TimeSlot> findByUser_Id(Long userId);

}