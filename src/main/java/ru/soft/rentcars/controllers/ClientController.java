package ru.soft.rentcars.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.soft.rentcars.contracts.services.ClientService;
import ru.soft.rentcars.exceptions.AlreadyExistsException;
import ru.soft.rentcars.exceptions.NotFoundException;
import ru.soft.rentcars.models.requests.NewClientRequest;
import ru.soft.rentcars.models.requests.RemoveClientRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
@Api(description="Customer management")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "Added new client")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Client added"),
            @ApiResponse(code = 400, message = "Not valid argument"),
            @ApiResponse(code = 404, message = "Car not found"),
            @ApiResponse(code = 409, message = "Client already exists"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping(value = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addClient(@Valid @RequestBody NewClientRequest request) {
        try {
            clientService.addClient(request.getName(), request.getBirthday(), request.getModel(), request.getYear());
        } catch (AlreadyExistsException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @ApiOperation(value = "Delete existing client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client deleted"),
            @ApiResponse(code = 400, message = "Not valid argument"),
            @ApiResponse(code = 404, message = "Car not found or Client don't rent this car"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @DeleteMapping(value = "/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteClient(@Valid @RequestBody RemoveClientRequest request) {
        try {
            clientService.deleteClient(request.getName(), request.getModel());
        } catch (NotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
