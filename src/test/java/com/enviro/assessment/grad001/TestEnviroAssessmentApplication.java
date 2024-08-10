package com.enviro.assessment.grad001;

import org.springframework.boot.SpringApplication;

public class TestEnviroAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.from(EnviroAssessmentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
