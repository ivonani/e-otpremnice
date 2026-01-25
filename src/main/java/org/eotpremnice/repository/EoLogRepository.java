package org.eotpremnice.repository;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.model.EoLogEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EoLogRepository {

    private final JdbcTemplate jdbc;

    public List<EoLogEntry> findIDDOKs(String idFirme, String tipDokumenta, String idRacunar) {
        String sql =
                "SELECT IDDOK, RTRIM(RequestID) " +
                        "FROM dbo.eoLog " +
                        "WHERE IDFirme = ? " +
                        "  AND TipDokumenta = ? " +
                        "  AND IDRacunar = ? " +
                        "  AND ZaSlanje = 1 " +
                        "  AND Komanda = 'SEND_EO'";

        return jdbc.query(
                sql,
                (rs, rowNum) -> new EoLogEntry(
                        rs.getInt("IDDOK"),
                        rs.getString("RequestID")),
                idFirme,
                tipDokumenta,
                idRacunar
        );
    }
}