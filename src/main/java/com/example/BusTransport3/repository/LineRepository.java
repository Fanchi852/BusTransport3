package com.example.BusTransport3.repository;

import com.example.BusTransport3.domain.Line;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LineRepository extends CrudRepository<Line,Integer> {

    ArrayList<Line> findAll();
    Optional<Line> findById(Integer id);
    Set<Line> findByName(String name);
    Line save(Line line);
    void deleteById(Integer id);
    void deleteAll();
    long count();
}
