package flashcards;

import java.util.Scanner;

class DeckBuilder {

    public static Deck buildDeck(Deck deck, int numberOfCards, Scanner scanner) {

        String question;
        String answer;

        for (int i = 1; i < numberOfCards + 1; i++) {
            System.out.println("The card #" + i + ":");
            question = scanner.nextLine();
            while (deck.hasQuestion(question)) {
                System.out.println("The card \"" + question + "\" already exists. Try again:");
                question = scanner.nextLine();
            }

            System.out.println("The definition of card #" + i + ":");
            answer = scanner.nextLine();
            while(deck.hasAnswer(answer)) {
                System.out.println("The definition \"" + answer + "\" already exists. Try again:");
                answer = scanner.nextLine();
            }

            deck.addCard(new Card(answer, question));
        }
        return deck;
    }
}
