package com.example.BusTransport3.exception;

public class BusStopNotFoundException extends RuntimeException{

    public BusStopNotFoundException(Integer id){super("BusStop not found: " + id);}

}
