package org.eotpremnice.repository;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.model.EoLogEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EoLogEntryRepository {

    private final JdbcTemplate jdbc;

    public List<EoLogEntry> findIDDOKs(String idFirme, String tipDokumenta, String idRacunar) {
        String sql =
                "SELECT IDDOK, RTRIM(RequestID) as RequestID, Komanda, RTRIM(SEF_ID) as SEF_ID " +
                        "FROM dbo.eoLog " +
                        "WHERE IDFirme = ? " +
                        "  AND TipDokumenta = ? " +
                        "  AND IDRacunar = ? " +
                        "  AND ZaSlanje = 1 ";

        return jdbc.query(
                sql,
                (rs, rowNum) -> new EoLogEntry(
                        rs.getInt("IDDOK"),
                        rs.getString("RequestID"),
                        rs.getString("Komanda"),
                        rs.getString("SEF_ID")),
                idFirme,
                tipDokumenta,
                idRacunar
        );
    }
}