package flashcards;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Log {
    private ArrayList<String> logContents;

    public Log() {
        this.logContents = new ArrayList<>();
    }

    public void logLine(String line) {
        this.logContents.add(line);
    }

    public void printAndLogLine(String line) {
        System.out.println(line);
        this.logLine(line);
    }

    public String scanAndLogLine(Scanner scanner) {
        String line = scanner.nextLine();
        this.logLine(line);
        return line;
    }

    public void clearLog() {
        this.logContents.clear();
    }

    public void saveLog(File logFile) {
        String response;
        try (PrintWriter writer = new PrintWriter(logFile)) {
            logContents.forEach(writer::println);
            response = "The log has been saved.";
        } catch(FileNotFoundException e) {
           response = "File not found";
        }
        System.out.println(response);
        this.logLine(response);
    }

}
