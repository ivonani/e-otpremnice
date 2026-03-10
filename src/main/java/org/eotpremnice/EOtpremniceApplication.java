package org.eotpremnice;

import org.eotpremnice.xml.writer.ErrorFileWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class EOtpremniceApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(EOtpremniceApplication.class, args);
        } catch (Throwable t) {
            ErrorFileWriter.write("Spring Boot failed to start", t);
        }
    }
}