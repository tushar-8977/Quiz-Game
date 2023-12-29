package quizgame;

import java.util.List;

public class QuizResult {
	private String quizName;
	private List<String> userResponses;
	private int score;

	public QuizResult(String quizName, List<String> userResponses, int score) {
		this.quizName = quizName;
		this.userResponses = userResponses;
		this.score = score;
	}

	@Override
	public String toString() {
		return "Quiz: " + quizName + ", Score: " + score + "/" + userResponses.size();
	}
}
