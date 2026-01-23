package org.eotpremnice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class EOtpremniceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EOtpremniceApplication.class);
    }
}