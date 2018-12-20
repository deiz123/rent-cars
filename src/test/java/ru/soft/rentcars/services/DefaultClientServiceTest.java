package ru.soft.rentcars.services;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.soft.rentcars.contracts.repositories.CarRepository;
import ru.soft.rentcars.contracts.repositories.ClientRepository;
import ru.soft.rentcars.contracts.services.ClientService;
import ru.soft.rentcars.exceptions.AlreadyExistsException;
import ru.soft.rentcars.exceptions.NotFoundException;
import ru.soft.rentcars.models.entities.Car;
import ru.soft.rentcars.models.entities.Client;
import ru.soft.rentcars.service.DefaultClientService;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DefaultClientServiceTest {

    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    CarRepository carRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void init() {
        clientService = new DefaultClientService(clientRepository, carRepository);
    }

    @Test
    public void testAddClient() {
        String clientName = "Alex";
        Date clientBirthday = new Date();
        String carModel = "kia rio";
        Integer carYear = 2014;

        Client createdClient = new Client();
        createdClient.setId(1);
        createdClient.setName(clientName);
        createdClient.setBirthday(clientBirthday);

        Car car = new Car();
        car.setId(2);
        car.setModel(carModel);
        car.setYear(carYear);

        Mockito.doReturn(false).when(clientRepository).existsClientByNameAndBirthday(clientName, clientBirthday);
        Mockito.doReturn(car).when(carRepository).findCarByModelAndYear(carModel, carYear);
        Mockito.doReturn(createdClient).when(clientRepository).save(new Client(clientName, clientBirthday));

        clientService.addClient(clientName, clientBirthday, carModel, carYear);

        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository, times(1)).save(captor.capture());
        car.setClient(createdClient);
        assertEquals(car, captor.getValue());

        verify(clientRepository, times(1)).existsClientByNameAndBirthday(anyString(), any());
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(carRepository, times(1)).findCarByModelAndYear(anyString(), anyInt());
    }

    @Test(expected = NotFoundException.class)
    public void testAddClient_whenCarIsBusy() {
        String clientName = "Alex";
        Date clientBirthday = new Date();
        String carModel = "kia rio";
        Integer carYear = 2014;

        Client client = new Client();
        client.setId(1);
        client.setName(clientName);
        client.setBirthday(clientBirthday);

        Car car = new Car();
        car.setId(2);
        car.setModel(carModel);
        car.setYear(carYear);
        car.setClient(client);

        Mockito.doReturn(false).when(clientRepository).existsClientByNameAndBirthday(clientName, clientBirthday);
        Mockito.doReturn(car).when(carRepository).findCarByModelAndYear(carModel, carYear);

        clientService.addClient(clientName, clientBirthday, carModel, carYear);
    }

    @Test(expected = AlreadyExistsException.class)
    public void testAddClient_whenClientExists() {
        String clientName = "Alex";
        Date clientBirthday = new Date();
        String carModel = "kia rio";
        Integer carYear = 2014;

        Mockito.doReturn(true).when(clientRepository).existsClientByNameAndBirthday(clientName, clientBirthday);

        clientService.addClient(clientName, clientBirthday, carModel, carYear);
    }

    @Test(expected = NotFoundException.class)
    public void testAddClient_whenCarNotFound() {
        String clientName = "Alex";
        Date clientBirthday = new Date();
        String carModel = "kia rio";
        Integer carYear = 2014;

        Mockito.doReturn(false).when(clientRepository).existsClientByNameAndBirthday(clientName, clientBirthday);
        Mockito.doReturn(null).when(carRepository).findCarByModelAndYear(carModel, carYear);

        clientService.addClient(clientName, clientBirthday, carModel, carYear);
    }

    @Test
    public void testDeleteClient() {
        String name = "Alex";
        String model = "kia rio";

        Car car = new Car();
        car.setId(2);
        car.setModel(model);
        car.setYear(2014);

        Client client = new Client();
        client.setId(1);
        client.setName(name);
        client.setBirthday(new Date());
        client.setCar(car);

        Mockito.doReturn(client).when(clientRepository).findClientByName(name);

        clientService.deleteClient(name, model);

        ArgumentCaptor<Client> argument = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository, times(1)).delete(argument.capture());
        assertEquals(name, argument.getValue().getName());
    }

    @Test
    public void testDeleteClient_whenClientNotFound() {
        String name = "Alex";
        String model = "kia rio";

        Mockito.doReturn(null).when(clientRepository).findClientByName(name);

        exceptionRule.expect(NotFoundException.class);
        exceptionRule.expectMessage("Client not found");

        clientService.deleteClient(name, model);
    }

    @Test
    public void testDeleteClient_whenClientNotHaveCar() {
        String name = "Alex";
        String model = "kia rio";

        Client client = new Client();
        client.setId(1);
        client.setName(name);
        client.setBirthday(new Date());
        client.setCar(null);

        Mockito.doReturn(client).when(clientRepository).findClientByName(name);

        exceptionRule.expect(NotFoundException.class);
        exceptionRule.expectMessage("Client don't rent this car");

        clientService.deleteClient(name, model);
    }

    @Test
    public void testDeleteClient_whenClientNotRentThisCar() {
        String name = "Alex";
        String model = "kia rio";

        Car car = new Car();
        car.setId(2);
        car.setModel("hyundai solaris");
        car.setYear(2014);

        Client client = new Client();
        client.setId(1);
        client.setName(name);
        client.setBirthday(new Date());
        client.setCar(car);

        Mockito.doReturn(client).when(clientRepository).findClientByName(name);

        exceptionRule.expect(NotFoundException.class);
        exceptionRule.expectMessage("Client don't rent this car");

        clientService.deleteClient(name, model);
    }
}
