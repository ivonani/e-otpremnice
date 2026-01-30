package org.eotpremnice.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class SqlInstanceFileReader {

    public static final Path SYSFIRMA_PATH = Paths.get("C:\\InSoft\\SysFirma.txt");

    private SqlInstanceFileReader() {}

    public static String readInstanceName() {
        try {
            if (!Files.exists(SYSFIRMA_PATH)) {
                throw new IllegalStateException("SysFirma.txt ne postoji na lokaciji: " + SYSFIRMA_PATH);
            }

            try (BufferedReader br = Files.newBufferedReader(SYSFIRMA_PATH, StandardCharsets.UTF_8)) {
                String firstLine = br.readLine();
                String instance = (firstLine != null) ? firstLine.trim() : "";

                if (instance.isEmpty()) {
                    throw new IllegalStateException("SysFirma.txt je prazan (prva linija je prazna).");
                }

                return instance;
            }

        } catch (IOException e) {
            throw new IllegalStateException("Ne mogu da procitam SysFirma.txt: " + SYSFIRMA_PATH, e);
        }
    }
}
