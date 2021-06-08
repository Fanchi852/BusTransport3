package com.example.BusTransport3.controller;

import com.example.BusTransport3.domain.BusStop;
import com.example.BusTransport3.exception.BusStopAlreadyExists;
import com.example.BusTransport3.exception.BusStopNotFoundException;
import com.example.BusTransport3.service.BusStopService;
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
import java.util.HashSet;
import java.util.Set;

import static com.example.BusTransport3.controller.Response.CONFLICT;
import static com.example.BusTransport3.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Autobuses", description = "Listado de autobuses")
public class BusStopController {

    private final Logger logger = LoggerFactory.getLogger(BusController.class);

    @Autowired
    private BusStopService busstopservice;

    @Operation(summary = "Obtiene el listado de paradas de autobus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de paradas de autobus",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BusStop.class)))),
    })
    @GetMapping(value = "/busStops", produces = "application/json")
    public ResponseEntity<ArrayList<BusStop>> getBusStops(){
        return new ResponseEntity<>(busstopservice.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una parada de autobus por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la paradas de autobus", content = @Content(schema = @Schema(implementation = BusStop.class))),
            @ApiResponse(responseCode = "404", description = "La paradas de autobus no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/busStopByid={id}", produces = "application/json")
    public ResponseEntity<BusStop> getBusStopById(@PathVariable Integer id) {
        BusStop busStop = busstopservice.findById(id)
                .orElseThrow(() -> new BusStopNotFoundException(id));
        return new ResponseEntity<>(busStop, HttpStatus.OK);
    }

    @Operation(summary = "Almacena una parada de autobus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "parada de autobus almacenado", content = @Content(schema = @Schema(implementation = BusStop.class))),
            @ApiResponse(responseCode = "409", description = "parada de autobus ya existe en la base de datos", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @RequestMapping(value = "/busStops", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<BusStop> addBusStop(@RequestBody BusStop busStop){
        logger.info("estamos para añadir: "+ busStop.toString());
        if (!busstopservice.findByName(busStop.getName()).isEmpty()){
            throw new BusStopAlreadyExists();
        }
        logger.info(busStop.toString());
        return new ResponseEntity<>(busstopservice.save(busStop), HttpStatus.CREATED);
    }

    @Operation(summary = "actualiza el nombre una parada de autobus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo el nombre de la parada de autobus", content = @Content(schema = @Schema(implementation = BusStop.class))),
            @ApiResponse(responseCode = "404", description = "La parada de autobus no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/busStops/{id}/change-name", produces = "application/json")
    public ResponseEntity<BusStop> modifybusStopName(@PathVariable Integer id, @RequestBody String name) {
        BusStop busStop = busstopservice.findById(id)
                .orElseThrow(() -> new BusStopNotFoundException(id));
        busStop.setName(name);
        return new ResponseEntity<>(busstopservice.save(busStop), HttpStatus.CREATED);
    }

    @Operation(summary = "actualiza una parada de autobus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo la parada de autobus", content = @Content(schema = @Schema(implementation = BusStop.class))),
            @ApiResponse(responseCode = "404", description = "La parada de autobus no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/busStops", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BusStop> modifyBusStop(@RequestBody BusStop busStop){
        Integer id = busStop.getId();
        busstopservice.findById(id)
                .orElseThrow(() -> new BusStopNotFoundException(id));
        logger.info(busStop.toString());
        return new ResponseEntity<>(busstopservice.save(busStop), HttpStatus.CREATED);
    }

    @Operation(summary = "Elimina una parada de autobus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la parada de autobus", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La parada de autobus no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/busesStop/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteBusStop(@PathVariable Integer id) {
        busstopservice.findById(id)
                .orElseThrow(() -> new BusStopNotFoundException(id));
        busstopservice.deleteById(id);
        return new ResponseEntity<>(Response.correctResponse(), HttpStatus.OK);
    }


    @ExceptionHandler(BusStopNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(BusStopNotFoundException bsnfe) {
        Response response = Response.errorResonse(NOT_FOUND, bsnfe.getMessage());
        logger.error(bsnfe.getMessage(), bsnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BusStopAlreadyExists.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Response> handleException(BusStopAlreadyExists bsfe) {
        Response response = Response.errorResonse(CONFLICT, bsfe.getMessage());
        logger.error(bsfe.getMessage(), bsfe);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
