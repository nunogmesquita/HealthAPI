package HealthAPI.repository;

import HealthAPI.model.address.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByStateId(@Param("id") Integer id);

}