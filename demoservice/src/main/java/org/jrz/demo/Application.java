package org.jrz.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.jrz.rpc.*","org.jrz.demo.*"})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
