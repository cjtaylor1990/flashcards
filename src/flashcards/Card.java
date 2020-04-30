package flashcards;

class Card {
    private String answer;
    private String question;

    public Card(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getQuestion() {
        return this.question;
    }
}
