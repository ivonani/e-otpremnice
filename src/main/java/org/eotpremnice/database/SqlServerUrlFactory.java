package org.eotpremnice.database;

public final class SqlServerUrlFactory {

    public static final String DB_NAME = "EOtpremnice";

    private SqlServerUrlFactory() {}

    public static String buildJdbcUrl(String instanceName) {
        // instanceName primer: "localhost\\SQLEXPRESS" ili "SQL01\\INSOFT"
        return "jdbc:sqlserver://"
                + instanceName
                + ";database=" + DB_NAME
                + ";";
    }
}
