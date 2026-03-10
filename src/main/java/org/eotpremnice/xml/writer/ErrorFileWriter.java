package org.eotpremnice.xml.writer;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;

@NoArgsConstructor
public final class ErrorFileWriter {

    private static final Path ERROR_FILE = Paths.get("C:\\InSoft\\",
            "error_0.txt"
    );

//    public static void write(String message) {
//        try {
//            Files.write(ERROR_FILE, message.getBytes(StandardCharsets.UTF_8));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void write(String message) {
        writeInternal(message, null, false);
    }

    public static void write(Throwable t) {
        writeInternal(null, t, false);
    }

    public static void write(String message, Throwable t) {
        writeInternal(message, t, false);
    }

    // Ako želiš da se greške nadovezuju umesto da pregaze fajl
    public static void append(String message, Throwable t) {
        writeInternal(message, t, true);
    }

    private static void writeInternal(String message, Throwable t, boolean append) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ERROR ").append(LocalDateTime.now()).append(" ===\n");

            if (message != null && !message.isEmpty()) {
                sb.append("Message: ").append(message).append("\n");
            }

            if (t != null) {
                sb.append("Exception: ").append(t.getClass().getName()).append("\n");
                sb.append("Exception message: ").append(t.getMessage()).append("\n");

                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                sb.append("Stacktrace:\n").append(sw).append("\n");
            }

            sb.append("====================================\n");

            byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);

            OpenOption[] opts = append
                    ? new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND}
                    : new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING};

            Files.write(ERROR_FILE, bytes, opts);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
