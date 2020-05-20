package flashcards;
import java.util.*;

public class Main {
    private static Menu readArgs(Log log, String[] args) {
        Map<String, String> argMap = new HashMap<> ();

        Deck deck = new Deck();
        String exportPath = null;

        for (int i = 0; i < args.length; i += 2) {
            argMap.put(args[i], args[i+1]);
        }

        if (argMap.containsKey("-import")) {
            deck.importCards(log, argMap.get("-import"));
        }

        if (argMap.containsKey("-export")) {
            exportPath = argMap.get("-export");
        }

        return new Menu(deck, exportPath);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Log log = new Log();
        Menu menu = readArgs(log, args);

        String userChoice;
        boolean menuStatus = true;
        while (menuStatus) {
            log.printAndLogLine(menu.toString());

            userChoice = log.scanAndLogLine(scanner);

            menuStatus = menu.use(log, scanner, userChoice);
        }
    }
}

