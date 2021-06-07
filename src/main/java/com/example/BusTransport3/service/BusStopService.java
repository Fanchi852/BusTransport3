package com.example.BusTransport3.service;



import com.example.BusTransport3.domain.BusStop;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public interface BusStopService {

    ArrayList<BusStop> findAll();
    Optional<BusStop> findById(Integer id);
    Set<BusStop> findByName(String name);
    BusStop save(BusStop busstop);
    void deleteById(Integer id);
    void deleteAll();
    long count();

}
