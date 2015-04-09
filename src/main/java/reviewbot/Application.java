/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jtidwell on 3/18/2015.
 */

@SpringBootApplication
@Configuration
//The ComponentScan annotation tells spring where to look for valid component classes.
//The app will fail without at least .controllers.
@ComponentScan({
        "reviewbot.hello",
        "reviewbot.controllers",
        "reviewbot.dao",
        "reviewbot.configuration"})

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
