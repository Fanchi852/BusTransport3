package com.example.BusTransport3.service;

import com.example.BusTransport3.domain.Line;
import com.example.BusTransport3.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class LineServiceImpl implements LineService{

    @Autowired
    private LineRepository linerepository;

    @Override
    public ArrayList<Line> findAll() {
        return linerepository.findAll();
    }

    @Override
    public Optional<Line> findById(Integer id) {
        return linerepository.findById(id);
    }

    @Override
    public Set<Line> findByName(String name) {
        return linerepository.findByName(name);
    }

    @Override
    public Line save(Line line) {
        return linerepository.save(line);
    }

    @Override
    public void deleteById(Integer id) {
        linerepository.deleteById(id);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public long count() {
        return linerepository.count();
    }

}
