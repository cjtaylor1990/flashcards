package flashcards;

import java.util.LinkedHashMap;
import java.util.ArrayList;

class Deck {
    private LinkedHashMap<String, String> map;

    public Deck() {
        this.map = new LinkedHashMap<>();
    }

    public void addCard(Card card) {
        this.map.put(card.getAnswer(), card.getQuestion());
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

    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        this.map.forEach((answer, question) -> answers.add(answer));
        return answers;
    }
}
