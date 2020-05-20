package flashcards;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.ArrayList;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

class Deck {
    private LinkedHashMap<String, String> map;
    private LinkedHashMap<String, Integer> stats;
    private int deckSize;

    public Deck() {
        this.map = new LinkedHashMap<>();
        this.stats = new LinkedHashMap<>();
        this.deckSize = 0;
    }

    public int getDeckSize() {
        return this.deckSize;
    }

    public void addCard(Card card) {
        this.addCard(card, 0);
    }

    public void addCard(Card card, int stat) {
        this.map.put(card.getAnswer(), card.getQuestion());
        this.stats.put(card.getAnswer(), stat);
        this.deckSize++;
    }

    public void removeCard(String question) {
        for (String answer : this.getAnswers()) {
            if (this.getQuestion(answer).equals(question)) {
                this.map.remove(answer);
                this.stats.remove(answer);
                this.deckSize--;
            }
        }
    }

    private void updateCard(Card card) {
        this.updateCard(card, 0);
    }

    private void updateCard(Card card, int stat) {
        for (String answer : this.getAnswers()) {
            if (this.map.get(answer).equals(card.getQuestion())) {
                this.map.remove(answer);
                this.map.put(card.getAnswer(), card.getQuestion());
                this.stats.remove(answer);
                this.stats.put(card.getAnswer(), stat);
            }
        }
    }

    public boolean hasAnswer(String answer) {
        return this.map.containsKey(answer);
    }

    public boolean hasQuestion(String question) {
        return this.map.containsValue(question);
    }

    public String getQuestion(String answer) {
        return this.map.get(answer);
    }

    public int getStat(String answer) {
        return this.stats.get(answer);
    }

    public void updateStat(String answer, int stat) {
        this.stats.replace(answer, stat);
    }

    public void incrementStat(String answer) {
        this.updateStat(answer, this.stats.get(answer) + 1);
    }

    public void resetStats() {
        this.stats.forEach((answer, stat) -> this.updateStat(answer, 0));
    }

    public String findHardestCard() {
        ArrayList<String> hardestCards = new ArrayList<>();
        int highestStat = 0;
        int currentStat;
        for (String answer : this.getAnswers()) {
            currentStat = this.getStat(answer);
            if (currentStat > 0 && currentStat >= highestStat) {
                if (currentStat > highestStat) {
                    highestStat = currentStat;
                    hardestCards.clear();
                }
                hardestCards.add(answer);
            }
        }
        if (hardestCards.size() == 0) {
            return "There are no cards with errors.";
        } else {
            StringBuilder hardestCardsPrint = new StringBuilder();
            String currentHardestCard = "\"" + this.getQuestion(hardestCards.get(0)) + "\"";
            hardestCardsPrint.append(currentHardestCard);
            if (hardestCards.size() > 1) {
                for (int i = 1; i < hardestCards.size(); i++) {
                    currentHardestCard = ", \"" + this.getQuestion(hardestCards.get(1)) + "\"";
                    hardestCardsPrint.append(currentHardestCard);
                }
                return "The hardest cards are " + hardestCardsPrint + ". " +
                        "You have " + highestStat + "errors answering them.";
            } else {
                return "The hardest card is " + hardestCardsPrint + ". " +
                        "You have " + highestStat + " errors answering it.";
            }
        }

    }

    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        this.map.forEach((answer, question) -> answers.add(answer));
        return answers;
    }

    public ArrayList<String> getAnswers(int numberOfCards) {
        ArrayList<String> answers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfCards; i++) {
            answers.add(this.getAnswers().get(random.nextInt(this.deckSize)));
        }
        return answers;
    }

    public void exportDeck(Log log, String fileName) {
        String response;
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            map.forEach(
                    (answer, question) -> {
                        writer.println(question + ":" + answer + ":" + this.getStat(answer));
                    }
            );
            response = this.getDeckSize() + " cards have been saved.";
        } catch (FileNotFoundException e) {
            response = "File not found.";
        }
        log.printAndLogLine(response);
    }

    public void importCards(Log log, String fileName) {
        String response;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            int cardsAdded = 0;
            while (scanner.hasNextLine()) {
                String[] pair = scanner.nextLine().split(":");
                if (this.hasQuestion(pair[0])) {
                    this.updateCard(new Card(pair[1], pair[0]), Integer.parseInt(pair[2]));
                } else {
                    this.addCard(new Card(pair[1], pair[0]), Integer.parseInt(pair[2]));
                }
                cardsAdded++;
            }

            response = cardsAdded + " cards have been loaded";

        } catch(FileNotFoundException e) {
            response = "File not found.";
        }
        log.printAndLogLine(response);
    }
}
