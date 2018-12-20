package ru.soft.rentcars.contracts.services;

import java.util.Date;

public interface ClientService {
    void addClient(String clientName, Date clientBirthday, String carModel, Integer carYear);

    void deleteClient(String name, String model);
}
