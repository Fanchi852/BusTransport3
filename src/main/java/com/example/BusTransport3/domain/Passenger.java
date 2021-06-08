package com.example.BusTransport3.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Passengers")
public class Passenger {

    @Schema(description = "numero de identificacion unico en la base de datos", example = "15", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "identifica a los clientes que han obtenido un bono premium de los que usan el transporte de forma anonima", example = "true", required = true)
    @Column
    private Boolean premium;

    @Schema(description = "fecha en la que el cliente obtubo el bono premium", example = "2015-01-01", required = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "inscription_date")
    private LocalDate inscriptiondate;

    @Schema(description = "nombre del cliente", example = "Francisco Jesus Moya Olivares", required = false)
    @Column
    private String name;

    @Schema(description = "edad del cliente a fecha de obtencion del bono premium", example = "26", required = false)
    @Column
    private Integer edad;

    @Schema(description = "distancia entre la casa del cliente y la parada mas cercana", example = "25.4", required = false)
    @Column(name = "distance_between_home_Stopbus")
    private Float distancebetweenhomestopbus;
/*
    @OneToMany(mappedBy = "passenger")
    private List<PassengersBusesRelation> passengersbusesrelation;

 */
}
