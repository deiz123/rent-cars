package ru.soft.rentcars.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.soft.rentcars.models.entities.Client;

import java.util.Date;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findClientByName(String name);

    boolean existsClientByNameAndBirthday(String name, Date birthday);
}
