package flashcards;
import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the number of cards:");
        int numberOfCards = Integer.parseInt(scanner.nextLine());

        Deck deck = DeckBuilder.buildDeck(new Deck(), numberOfCards, scanner);
        Quiz.takeQuiz(deck, scanner);

        scanner.close();
    }
}

