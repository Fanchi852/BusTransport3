package com.example.BusTransport3.controller;

import com.example.BusTransport3.domain.Bus;
import com.example.BusTransport3.exception.BusNotFoundException;
import com.example.BusTransport3.service.BusService;
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

import static com.example.BusTransport3.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Autobuses", description = "Listado de autobuses")
public class BusController {

    private final Logger logger = LoggerFactory.getLogger(BusController.class);

    @Autowired
    private BusService busservice;

    @Operation(summary = "Obtiene el listado de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de autobuses",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bus.class)))),
    })
    @GetMapping(value = "/buses", produces = "application/json")
    public ResponseEntity<ArrayList<Bus>> getBuses(){
        logger.info("inicio getBuses");
        logger.info("fin getBuses");
        return new ResponseEntity<>(busservice.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un autobus por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el autobus", content = @Content(schema = @Schema(implementation = Bus.class))),
            @ApiResponse(responseCode = "404", description = "El autobus no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/buses/{id}", produces = "application/json")
    public ResponseEntity<Bus> getBusById(@PathVariable Integer id) {
        Bus bus = busservice.findById(id)
                .orElseThrow(() -> new BusNotFoundException(id));

        return new ResponseEntity<>(bus, HttpStatus.OK);
    }



    @ExceptionHandler(BusNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(BusNotFoundException bnfe) {
        Response response = Response.errorResonse(NOT_FOUND, bnfe.getMessage());
        logger.error(bnfe.getMessage(), bnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
