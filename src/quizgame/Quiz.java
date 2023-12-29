package quizgame;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
	private String name;
	private List<Question> questions = new ArrayList<Question>();

	public Quiz(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}

	public Question getQuestion(int index) {
		return questions.get(index);
	}
	
	public int getNumQuestions() {
		return questions.size() ;
	}
}
