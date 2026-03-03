package com.yodishtr.ATM.Simulator;

import org.springframework.boot.SpringApplication;

public class TestAtmSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.from(AtmSimulatorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
