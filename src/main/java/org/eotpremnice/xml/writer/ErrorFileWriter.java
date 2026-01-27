package org.eotpremnice.xml.writer;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

@NoArgsConstructor
public final class ErrorFileWriter {

    private static final Path ERROR_FILE = Paths.get(
            System.getProperty("user.dir"),
            "error_0.txt"
    );

    public static void write(String message) {
        try {
            Files.write(ERROR_FILE, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void writeToErrorTxt(String title, Throwable t) {

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(LocalDateTime.now()).append("] ").append(title).append("\n");
            sb.append(t.toString()).append("\n\n");

            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            sb.append(sw).append("\n");
            sb.append("------------------------------------------------------------\n\n");

            Files.write(ERROR_FILE, sb.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ignored) {
            // last resort: nothing else we can do
        }
    }
}
