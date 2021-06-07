package com.example.BusTransport3.exception;

public class PassengerNotFoundException extends RuntimeException{

    public PassengerNotFoundException(Integer id){super("Passenger not found: " + id);}

}
