package com.example.BusTransport3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusTransport3Application {

	public static void main(String[] args) {

		try {
			SpringApplication.run(BusTransport3Application.class, args);
		}catch (Exception error){
			System.out.println(error);
		}

	}

}
