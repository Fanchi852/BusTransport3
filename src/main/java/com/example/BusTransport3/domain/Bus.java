package com.example.BusTransport3.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Buss")
public class Bus {

    @Schema(description = "numero de identificacion unico en la base de datos", example = "542", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "ccodigo de identificacion del autobus", example = "E-11", required = true)
    @Column
    @NotEmpty(message = "el codigo del autobus es obligatorio")
    private String code;

    @Schema(description = "identifica el autobus como uno de cuerpo simple o cuerpo doble, sera tru si es doble false si es simple", example = "true", required = true)
    @Column(name = "double_bus")
    private Boolean doublebus;

    @Schema(description = "fecha de adquisicion de autobus", example = "25/01/1999 17:25:00", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "buy_date")
    private LocalDate buydate;

    @Schema(description = "litros de consumo de combustible cada 100 km", example = "28,60", required = true)
    @Column
    private Float consumption;

    @Schema(description = "cantidad de pasajeros maxima que puede transporta el autobus", example = "80", required = true)
    @Column
    private Integer capacity;
/*
    @ManyToOne()
    @JoinColumn(name = "line_id")
    @JsonBackReference
    private Line line;

    @OneToMany(mappedBy = "bus")
    @JsonBackReference
    private List<PassengersBusesRelation> passengersbusesrelation;
*/
}
