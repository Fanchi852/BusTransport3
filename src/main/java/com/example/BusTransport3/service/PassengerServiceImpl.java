package com.example.BusTransport3.service;


import com.example.BusTransport3.domain.Passenger;
import com.example.BusTransport3.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private PassengerRepository passengerrepository;

    @Override
    public ArrayList<Passenger> findAll() {
        return passengerrepository.findAll();
    }

    @Override
    public Optional<Passenger> findById(Integer id) {
        return passengerrepository.findById(id);
    }

    @Override
    public Optional<Passenger> findByName(String name) {
        return passengerrepository.findByName(name);
    }

    @Override
    public Passenger save(Passenger passenger) {
        return passengerrepository.save(passenger);
    }

    @Override
    public void deleteById(Integer id) {passengerrepository.deleteById(id);}

    @Override
    public void deleteAll() {}

    @Override
    public long count() {
        return passengerrepository.count();
    }

}
