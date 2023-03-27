package HealthAPI.repository;

import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    Page<TimeSlot> findByIsBookedFalse(Pageable pageable);

    Page<TimeSlot> findByUserAndIsBookedFalse(Long userId, Pageable pageable);

    Page<TimeSlot> findByUser_SpecialityAndIsBookedFalse(Speciality speciality, Pageable pageable);

    void deleteByUser_Id(Long userId);

    Optional<TimeSlot> findById(Long id);

}