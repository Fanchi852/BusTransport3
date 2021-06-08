package com.example.BusTransport3.exception;

public class BusAlreadyExists extends RuntimeException{

    public BusAlreadyExists(Integer id){super("Bus already exists - " + id);}
    public BusAlreadyExists(){super("Bus already exists");}

}
