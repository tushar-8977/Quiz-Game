package quizgame;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String username;
	private String password;
	private List<QuizResult> quizResults = new ArrayList<>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void addQuizResult(QuizResult result) {
		quizResults.add(result);
	}

	public List<QuizResult> getQuizResults() {
		return quizResults;
	}

}
