package org.eotpremnice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SystblParamJdbcRepository {

    private final JdbcTemplate jdbc;

    public String readNcharParam(int idParam) {
        String sql = "SELECT RTRIM(ncharParam) FROM systblParam WHERE IDParam = ?";
        return jdbc.queryForObject(sql, String.class, idParam);
    }

    public String readNcharParam(int idParam, String idRacunar) {
        String sql = "SELECT RTRIM(ncharParam) FROM systblParam WHERE IDParam = ? AND IDRacunar = ?";
        return jdbc.queryForObject(sql, String.class, idParam, idRacunar);
    }
}
