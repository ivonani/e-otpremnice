package org.eotpremnice.repository;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.eotpremnice.model.PristupniParametri;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PristupniParametriRepository {

    private final JdbcTemplate jdbc;
    private SimpleJdbcCall pristupniParametriCall;

    @PostConstruct
    void init() {
        this.pristupniParametriCall = new SimpleJdbcCall(jdbc)
                .withProcedureName("PristupniParametri");
    }

    public PristupniParametri load() {
        Map<String, Object> result = pristupniParametriCall.execute();

        // SQL Server vraća result set pod ovim ključem
        var rows = (java.util.List<Map<String, Object>>) result.get("#result-set-1");

        if (rows == null || rows.isEmpty()) {
            throw new IllegalStateException("PristupniParametri returned no rows");
        }

        Map<String, Object> row = rows.get(0);

        String file = (String) row.get("File");
        String url = (String) row.get("URL");

        if (file == null || url == null) {
            throw new IllegalStateException("File or URL is null in PristupniParametri result");
        }

        return new PristupniParametri(file.trim(), url.trim());
    }

}
