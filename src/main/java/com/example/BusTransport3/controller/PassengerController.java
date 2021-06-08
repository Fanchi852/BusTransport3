package com.example.BusTransport3.controller;

import com.example.BusTransport3.domain.Passenger;
import com.example.BusTransport3.exception.PassengerAlreadyExists;
import com.example.BusTransport3.exception.PassengerNotFoundException;
import com.example.BusTransport3.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.example.BusTransport3.controller.Response.CONFLICT;
import static com.example.BusTransport3.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Pasajeros", description = "Listado de los Pasajeros")
public class PassengerController {

    private final Logger logger = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerService passengerservice;

    @Operation(summary = "Obtiene el listado de pasajeros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pasajeros",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Passenger.class)))),
    })
    @GetMapping(value = "/passengers", produces = "application/json")
    public ResponseEntity<ArrayList<Passenger>> getPassengers(){
        return new ResponseEntity<>(passengerservice.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un pasajero por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el pasajero", content = @Content(schema = @Schema(implementation = Passenger.class))),
            @ApiResponse(responseCode = "404", description = "El pasajero no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/passengerByid={id}", produces = "application/json")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Integer id) {
        Passenger passenger = passengerservice.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @Operation(summary = "Almacena un pasajero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "pasajero almacenado", content = @Content(schema = @Schema(implementation = Passenger.class))),
            @ApiResponse(responseCode = "409", description = "este pasajero ya existe en la base de datos", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @RequestMapping(value = "/passengers", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Passenger> addPassenger(@RequestBody Passenger passenger){
        logger.info("estamos para añadir: "+ passenger.toString());
        if (!passengerservice.findByName(passenger.getName()).isEmpty()){
            throw new PassengerAlreadyExists();
        }
        logger.info(passenger.toString());
        return new ResponseEntity<>(passengerservice.save(passenger), HttpStatus.CREATED);
    }

    @Operation(summary = "actualiza el nombre de un pasajero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo el nombre del pasajero", content = @Content(schema = @Schema(implementation = Passenger.class))),
            @ApiResponse(responseCode = "404", description = "El pasajero no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/passengers/{id}/change-name", produces = "application/json")
    public ResponseEntity<Passenger> modifyPassengerName(@PathVariable Integer id, @RequestBody String name) {
        Passenger passenger = passengerservice.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
        passenger.setName(name);
        return new ResponseEntity<>(passengerservice.save(passenger), HttpStatus.CREATED);
    }

    @Operation(summary = "actualiza un pasajero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo el pasajero", content = @Content(schema = @Schema(implementation = Passenger.class))),
            @ApiResponse(responseCode = "404", description = "el pasajero no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/passengers", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Passenger> modifyPassanger(@RequestBody Passenger passenger){
        Integer id = passenger.getId();
        passengerservice.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
        logger.info(passenger.toString());
        return new ResponseEntity<>(passengerservice.save(passenger), HttpStatus.CREATED);
    }

    @Operation(summary = "Elimina un pasajero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el pasajero", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "el pasajero no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/passengers/{id}", produces = "application/json")
    public ResponseEntity<Response> deletePassenger(@PathVariable Integer id) {
        passengerservice.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
        passengerservice.deleteById(id);
        return new ResponseEntity<>(Response.correctResponse(), HttpStatus.OK);
    }


    @ExceptionHandler(PassengerNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(PassengerNotFoundException bsnfe) {
        Response response = Response.errorResonse(NOT_FOUND, bsnfe.getMessage());
        logger.error(bsnfe.getMessage(), bsnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PassengerAlreadyExists.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Response> handleException(PassengerAlreadyExists bsfe) {
        Response response = Response.errorResonse(CONFLICT, bsfe.getMessage());
        logger.error(bsfe.getMessage(), bsfe);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
