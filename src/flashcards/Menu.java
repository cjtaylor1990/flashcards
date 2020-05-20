package flashcards;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.io.File;

public class Menu {

    private Deck deck;
    private String exportPath;
    private MenuOptions options;

    public Menu(Deck deck, String exportPath) {
        this.deck = deck;
        this.exportPath = exportPath;
        this.options = new MenuOptions();
    }

    @Override
    public String toString() {
        return "Input the action (" + this.options.toString() + "):";
    }

    public boolean use(Log log, Scanner scanner, String userInput) {
        return this.options.choose(this.deck, log, scanner, userInput, this.exportPath);
    }
}

class MenuOptions {

    private LinkedHashSet<String> options;

    MenuOptions() {
        this.options = new LinkedHashSet<>();
        this.options.add("add");
        this.options.add("remove");
        this.options.add("import");
        this.options.add("export");
        this.options.add("ask");
        this.options.add("exit");
        this.options.add("log");
        this.options.add("hardest card");
        this.options.add("reset stats");
    }

    @Override
    public String toString() {
        String optionsString = "";
        for (String option : this.options) {
            optionsString = "".equals(optionsString) ? option : optionsString + ", " + option;
        }
        return optionsString;
    }

    public boolean add(Deck deck, Log log, Scanner scanner) {
        log.printAndLogLine("The card");

        String userQuestion = log.scanAndLogLine(scanner);

        if (deck.hasQuestion(userQuestion)) {

            log.printAndLogLine("The card \"" + userQuestion + "\" already exists.");

        } else {
            log.printAndLogLine("The definition of the card:");

            String userAnswer = log.scanAndLogLine(scanner);

            String response;
            if (deck.hasAnswer(userAnswer)) {
                response = "The definition \"" + userAnswer + "\" already exists";
            } else {
                deck.addCard(new Card(userAnswer, userQuestion));
                response = "The pair (\"" + userQuestion + "\":\"" + userAnswer + "\") has been added.";
            }
            log.printAndLogLine(response);
        }
        return true;
    }

    public boolean remove(Deck deck, Log log, Scanner scanner) {
        log.printAndLogLine("The card:");

        String userQuestion = log.scanAndLogLine(scanner);

        String response;
        if (deck.hasQuestion(userQuestion)) {
            deck.removeCard(userQuestion);
            response = "The card has been removed";
        } else {
            response = "Can't remove \"" + userQuestion + "\": there is no such card";
        }
        log.printAndLogLine(response);
        return true;
    }

    public boolean importFromFile(Deck deck, Log log, Scanner scanner) {
        log.printAndLogLine("The file:");

        String fileName = log.scanAndLogLine(scanner);

        deck.importCards(log, fileName);
        return true;
    }

    public boolean exportToFile(Deck deck, Log log, Scanner scanner) {
        log.printAndLogLine("The file:");

        String fileName = log.scanAndLogLine(scanner);

        deck.exportDeck(log, fileName);
        return true;
    }

    public boolean ask(Deck deck, Log log, Scanner scanner) {
        log.printAndLogLine("How many times to ask?");

        String userInput = log.scanAndLogLine(scanner);

        Quiz.takeQuiz(log, scanner, deck, Integer.parseInt(userInput));
        return true;
    }

    public boolean exit(Deck deck, Log log, String exportPath) {
        log.printAndLogLine("Bye bye!");
        if (exportPath != null) {
            deck.exportDeck(log, exportPath);
        }
        return false;
    }

    public boolean log(Log log, Scanner scanner) {
        String filePrompt = "File name:";

        System.out.println(filePrompt);
        log.logLine(filePrompt);

        String fileName = scanner.nextLine();
        log.logLine(fileName);

        log.saveLog(new File(fileName));
        return true;
    }

    public boolean hardestCard(Deck deck, Log log) {
        log.printAndLogLine(deck.findHardestCard());
        return true;
    }

    public boolean resetStats(Deck deck, Log log) {
        deck.resetStats();
        log.printAndLogLine("Card statistics has been reset.");
        return true;
    }

    public boolean choose(Deck deck, Log log, Scanner scanner, String userInput, String exportPath) {
        switch (userInput.toLowerCase()) {
            case "add":
                return this.add(deck, log, scanner);
            case "remove":
                return this.remove(deck, log, scanner);
            case "import":
                return this.importFromFile(deck, log, scanner);
            case "export":
                return this.exportToFile(deck, log, scanner);
            case "ask":
                return this.ask(deck, log, scanner);
            case "exit":
                return this.exit(deck, log, exportPath);
            case "log":
                return this.log(log, scanner);
            case "hardest card":
                return this.hardestCard(deck, log);
            case "reset stats":
                return this.resetStats(deck, log);
            default:
                log.printAndLogLine("Invalid user command.");
                return true;
        }
    }

}
