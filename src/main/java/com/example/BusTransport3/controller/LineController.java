package com.example.BusTransport3.controller;

import com.example.BusTransport3.domain.Line;
import com.example.BusTransport3.exception.LineAlreadyExists;
import com.example.BusTransport3.exception.LineNotFoundException;
import com.example.BusTransport3.service.LineService;
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
@Tag(name = "Lineas", description = "Listado de Lineas")
public class LineController {

    private final Logger logger = LoggerFactory.getLogger(LineController.class);

    @Autowired
    private LineService lineService;

    @Operation(summary = "Obtiene el listado de Lineas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de Lineas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Line.class)))),
    })
    @GetMapping(value = "/lines", produces = "application/json")
    public ResponseEntity<ArrayList<Line>> getLines(){
        return new ResponseEntity<>(lineService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una linea por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la linea", content = @Content(schema = @Schema(implementation = Line.class))),
            @ApiResponse(responseCode = "404", description = "La linea no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/lineByid={id}", produces = "application/json")
    public ResponseEntity<Line> getLineById(@PathVariable Integer id) {
        Line line = lineService.findById(id)
                .orElseThrow(() -> new LineNotFoundException(id));
        return new ResponseEntity<>(line, HttpStatus.OK);
    }

    @Operation(summary = "Almacena una linea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "linea almacenada", content = @Content(schema = @Schema(implementation = Line.class))),
            @ApiResponse(responseCode = "409", description = "esta linea ya existe en la base de datos", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @RequestMapping(value = "/lines", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Line> addLine(@RequestBody Line line){
        logger.info("estamos para añadir: "+ line.toString());
        if (!lineService.findByName(line.getName()).isEmpty()){
            throw new LineAlreadyExists();
        }
        logger.info(line.toString());
        return new ResponseEntity<>(lineService.save(line), HttpStatus.CREATED);
    }

    @Operation(summary = "actualiza el nombre una linea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo el nombre de la linea", content = @Content(schema = @Schema(implementation = Line.class))),
            @ApiResponse(responseCode = "404", description = "La linea no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/lines/{id}/change-name", produces = "application/json")
    public ResponseEntity<Line> modifyLineName(@PathVariable Integer id, @RequestBody String name) {
        Line line = lineService.findById(id)
                .orElseThrow(() -> new LineNotFoundException(id));
        line.setName(name);
        return new ResponseEntity<>(lineService.save(line), HttpStatus.CREATED);
    }

    @Operation(summary = "actualiza una linea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo la linea", content = @Content(schema = @Schema(implementation = Line.class))),
            @ApiResponse(responseCode = "404", description = "La linea no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/lines", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Line> modifyLine(@RequestBody Line line){
        Integer id = line.getId();
        lineService.findById(id)
                .orElseThrow(() -> new LineNotFoundException(id));
        logger.info(line.toString());
        return new ResponseEntity<>(lineService.save(line), HttpStatus.CREATED);
    }

    @Operation(summary = "Elimina una linea")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la linea", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La linea no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/lines/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteLine(@PathVariable Integer id) {
        lineService.findById(id)
                .orElseThrow(() -> new LineNotFoundException(id));
        lineService.deleteById(id);
        return new ResponseEntity<>(Response.correctResponse(), HttpStatus.OK);
    }


    @ExceptionHandler(LineNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(LineNotFoundException bsnfe) {
        Response response = Response.errorResonse(NOT_FOUND, bsnfe.getMessage());
        logger.error(bsnfe.getMessage(), bsnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(LineAlreadyExists.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Response> handleException(LineAlreadyExists bsfe) {
        Response response = Response.errorResonse(CONFLICT, bsfe.getMessage());
        logger.error(bsfe.getMessage(), bsfe);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
