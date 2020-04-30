package flashcards;

import java.util.Scanner;

public class Quiz {
    public static void takeQuiz(Deck deck, Scanner scanner) {
        String question;
        String userAnswer;
        for (String answer : deck.getAnswers()) {
            question = deck.getQuestion(answer);

            System.out.println("Print the definition of \"" + question + "\":");
            userAnswer = scanner.nextLine();
            if (answer.equals(userAnswer)) {
                System.out.println("Correct answer.");
            } else if (deck.hasAnswer(userAnswer)){
                System.out.println(
                        "Wrong answer. The correct one is \"" + answer + "\", you've " +
                                "just written the definition of \"" +
                                deck.getQuestion(userAnswer) + "\"."
                );
            } else {
                System.out.println("Wrong answer. The correct one is \"" + answer + "\".");
            }
        }
    }
}
