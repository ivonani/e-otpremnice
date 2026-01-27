package org.eotpremnice.config;

import org.eotpremnice.database.SqlServerUrlFactory;
import org.eotpremnice.reader.SqlInstanceFileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
public class BootstrapDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        try {
            String instance = SqlInstanceFileReader.readInstanceName();
//          String instance = "SRV2016\\INSOFT";
            String url = SqlServerUrlFactory.buildJdbcUrl(instance);

            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ds.setUrl(url);

            ds.setUsername("eoUser");
            ds.setPassword("In$oft60181680");
            return ds;
        } catch (RuntimeException e) {
            throw new RuntimeException("Nemoguc pristup bazi podataka");
        }
    }
}
