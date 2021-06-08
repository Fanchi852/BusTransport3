package com.example.BusTransport3.repository;

import com.example.BusTransport3.domain.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger,Integer> {

    ArrayList<Passenger> findAll();
    Optional<Passenger> findById(Integer id);
    Optional<Passenger> findByName(String name);
    Passenger save(Passenger passenger);
    void deleteById(Integer id);
    void deleteAll();
    long count();
}
