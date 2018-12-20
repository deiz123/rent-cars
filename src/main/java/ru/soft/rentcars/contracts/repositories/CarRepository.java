package ru.soft.rentcars.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.soft.rentcars.models.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findCarByModelAndYear(String model, Integer year);
}
