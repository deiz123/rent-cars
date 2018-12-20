package ru.soft.rentcars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.soft.rentcars.contracts.repositories.CarRepository;
import ru.soft.rentcars.contracts.services.ClientService;
import ru.soft.rentcars.exceptions.AlreadyExistsException;
import ru.soft.rentcars.exceptions.NotFoundException;
import ru.soft.rentcars.contracts.repositories.ClientRepository;
import ru.soft.rentcars.models.entities.Car;
import ru.soft.rentcars.models.entities.Client;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class DefaultClientService implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CarRepository carRepository;

    public DefaultClientService(ClientRepository clientRepository, CarRepository carRepository) {
        super();
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
    }

    @Override
    @Transactional
    public void addClient(
            String clientName,
            Date clientBirthday,
            String carModel,
            Integer carYear
    ) {
        if (!clientRepository.existsClientByNameAndBirthday(clientName, clientBirthday)) {
            Car car = carRepository.findCarByModelAndYear(carModel, carYear);

            if (car != null && car.getClient() == null) {
                Client createdClient = clientRepository.save(new Client(clientName, clientBirthday));
                car.setClient(createdClient);
                carRepository.save(car);
            } else {
                throw new NotFoundException("Car not found");
            }
        } else {
            throw new AlreadyExistsException("Client already exists");
        }
    }

    @Override
    @Transactional
    public void deleteClient(String name, String model) {
        Client client = clientRepository.findClientByName(name);

        if (client != null) {
            if (client.getCar() != null && client.getCar().getModel().equals(model)) {
                clientRepository.delete(client);
            } else {
                throw new NotFoundException("Client don't rent this car");
            }
        } else {
            throw new NotFoundException("Client not found");
        }
    }
}
