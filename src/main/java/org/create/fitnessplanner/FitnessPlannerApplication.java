package org.create.fitnessplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FitnessPlannerApplication {

    public static void main(String[] args) {

        SpringApplication.run(FitnessPlannerApplication.class, args);

    }



}
