package com.example.BusTransport3.exception;

public class LineNotFoundException extends RuntimeException{

    public LineNotFoundException(Integer id){super("Line not found: " + id);}

}
