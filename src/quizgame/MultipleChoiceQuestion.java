package quizgame;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
	private List<String> choices;
    private int correctChoice;

    public MultipleChoiceQuestion(String question, List<String> choices, int correctChoice) {
        super(question);
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }
}
