package com.example.BusTransport3.service;



import com.example.BusTransport3.domain.Line;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public interface LineService {

    ArrayList<Line> findAll();
    Optional<Line> findById(Integer id);
    Set<Line> findByName(String name);
    Line save(Line line);
    Line modifyLine(Line line);
    void deleteById(Integer id);
    void deleteAll();
    long count();

}
