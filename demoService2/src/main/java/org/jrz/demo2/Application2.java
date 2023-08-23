package org.jrz.demo2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.jrz.rpc.*","org.jrz.demo2.*"})
public class Application2 {


    public static void main(String[] args) {
        SpringApplication.run(Application2.class, args);
    }
}
