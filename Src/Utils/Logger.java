package Src.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_FILE = "app.log";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public void info(String message) {
        log("INFO", message);
    }

    public void warning(String message) {
        log("WARNING", message);
    }

    public void error(String message, Exception e) {
        log("ERROR", message + " - " e.getMessage());
    }

    private void log(String level, String message) {
        String timestamp = dtf.format(LocalDateTime.now());
        String logMessage = String.format("%s [%s]: %s", timestamp, level, message);
        System.out.println(logMessage);
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
