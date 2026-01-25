package org.eotpremnice.xml.writer;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor
public final class ErrorFileWriter {

    public static void write(String message) {
        try {
            Path path = Paths.get(
                    System.getProperty("user.dir"),
                    "error_0.txt"
            );
            Files.write(path, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
