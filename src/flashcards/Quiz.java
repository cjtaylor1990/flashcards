package flashcards;

import java.util.Scanner;

public class Quiz {
    public static void takeQuiz(Log log, Scanner scanner, Deck deck, int numberCards) {

        String question;
        String userAnswer;
        String response;
        for (String answer : deck.getAnswers(numberCards)) {
            question = deck.getQuestion(answer);
            log.printAndLogLine("Print the definition of \"" + question + "\":");

            userAnswer = log.scanAndLogLine(scanner);

            if (answer.equals(userAnswer)) {
                response = "Correct answer.";
            } else if (deck.hasAnswer(userAnswer)){
                response = "Wrong answer. (The correct one is \"" + answer + "\", you've " +
                                "just written the definition of \"" +
                                deck.getQuestion(userAnswer) + "\" card.)";
                deck.incrementStat(answer);
            } else {
                response = "Wrong answer. The correct one is \"" + answer + "\".";
                deck.incrementStat(answer);
            }

            log.printAndLogLine(response);
        }
    }
}
