package com.example.BusTransport3.exception;

public class BusStopAlreadyExists extends RuntimeException{

    public BusStopAlreadyExists(Integer id){super("BusStop already exists - " + id);}
    public BusStopAlreadyExists(){super("BusStop already exists");}

}
