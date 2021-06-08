package com.example.BusTransport3.service;



import com.example.BusTransport3.domain.Passenger;

import java.util.ArrayList;
import java.util.Optional;

public interface PassengerService {

    ArrayList<Passenger> findAll();
    Optional<Passenger> findById(Integer id);
    Optional<Passenger> findByName(String name);
    Passenger save(Passenger passenger);
    void deleteById(Integer id);
    void deleteAll();
    long count();

}
