package org.eotpremnice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EOtpremniceJob implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    @Override
    public void run(String... args) {

        System.out.println("HI");

        Integer one = jdbc.queryForObject("SELECT 1", Integer.class);
        System.out.println("DB OK, SELECT 1 = " + one);

    }
}
