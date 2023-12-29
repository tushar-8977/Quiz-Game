package quizgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class QuizController {

	private static Map<String, Quiz> map = new HashMap<String, Quiz>();
	private static Map<String, User> users = new HashMap<>();
	private static User currentUser = null;

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (currentUser == null) {
                System.out.println("Enter a command: (login, register, exit)");
                String command = scanner.nextLine();
                if (command.equals("login")) {
                    loginUser(scanner);
                } else if (command.equals("register")) {
                    registerUser(scanner);
                } else if (command.equals("exit")) {
                    break;
                } else {
                    System.out.println("Invalid command.");
                }
            } else {
                System.out.println("Enter a command: (create, take, view, list, results, logout, exit)");
                String command = scanner.nextLine();
                if (command.equals("create")) {
                    createQuiz(scanner);
                } else if (command.equals("take")) {
                    takeQuiz(scanner);
                } else if (command.equals("view")) {
                    viewQuiz(scanner);
                } else if (command.equals("list")) {
                    listQuizzes();
                } else if (command.equals("results")) {
                    viewResults();
                } else if (command.equals("logout")) {
                    currentUser = null;
                    System.out.println("Logged out.");
                } else if (command.equals("exit")) {
                    break;
                } else {
                    System.out.println("Invalid command.");
                }
            }
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Logged in as " + username + ".");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.println("Enter a username:");
        String username = scanner.nextLine();
        System.out.println("Enter a password:");
        String password = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose another.");
        } else {
            User newUser = new User(username, password);
            users.put(username, newUser);
            System.out.println("Registration successful. You can now login.");
        }
    }

    private static void createQuiz(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("You need to be logged in to create a quiz.");
            return;
        }
        System.out.println("Enter the name of the quiz:");
        String quizName = scanner.nextLine();
        Quiz quiz = new Quiz(quizName);
        System.out.println("Enter the number of questions:");
        int numQuestions = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numQuestions; i++) {
            System.out.println("Enter the question:");
            String question = scanner.nextLine();
            System.out.println("Enter the number of choices:");
            int numChoices = Integer.parseInt(scanner.nextLine());
            List<String> choices = new ArrayList<>();
            for (int j = 0; j < numChoices; j++) {
                System.out.println("Enter choice " + (j + 1) + ":");
                String choice = scanner.nextLine();
                choices.add(choice);
            }
            System.out.println("Enter the index of the correct choice:");
            int correctChoice = Integer.parseInt(scanner.nextLine()) - 1;
            quiz.addQuestion(new MultipleChoiceQuestion(question, choices, correctChoice));
        }
        map.put(quizName, quiz);
        System.out.println("Quiz created.");
    }

    private static void takeQuiz(Scanner scanner) {
        System.out.println("Enter the name of the quiz:");
        String quizName = scanner.nextLine();
        Quiz quiz = map.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }
        int score = 0;
        List<String> userResponses = new ArrayList<>();
        for (int i = 0; i < quiz.getNumQuestions(); i++) {
            MultipleChoiceQuestion question = (MultipleChoiceQuestion) quiz.getQuestion(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ": " + choices.get(j));
            }
            System.out.println("Enter your answer (1-" + choices.size() + "):");
            int userAnswer = Integer.parseInt(scanner.nextLine()) - 1;
            userResponses.add(choices.get(userAnswer));
            if (userAnswer == question.getCorrectChoice()) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer is " + (question.getCorrectChoice() + 1) + ".");
            }
        }
        System.out.println("Your score is " + score + " out of " + quiz.getNumQuestions() + ".");
        if (currentUser != null) {
            currentUser.addQuizResult(new QuizResult(quizName, userResponses, score));
        }
    }

    private static void viewQuiz(Scanner scanner) {
        System.out.println("Enter the name of the quiz:");
        String quizName = scanner.nextLine();
        Quiz quiz = map.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }
        System.out.println("Quiz: " + quiz.getName());
        for (int i = 0; i < quiz.getNumQuestions(); i++) {
            MultipleChoiceQuestion question = (MultipleChoiceQuestion) quiz.getQuestion(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ": " + choices.get(j));
            }
            System.out.println("Answer: " + (question.getCorrectChoice() + 1));
        }
    }

    private static void listQuizzes() {
        System.out.println("Quizzes:");
        for (String quizName : map.keySet()) {
            System.out.println("- " + quizName);
        }
    }

    private static void viewResults() {
        if (currentUser == null) {
            System.out.println("You need to be logged in to view results.");
            return;
        }
        List<QuizResult> results = currentUser.getQuizResults();
        if (results.isEmpty()) {
            System.out.println("No quiz results available.");
        } else {
            System.out.println("Quiz Results for " + currentUser.getUsername() + ":");
            for (QuizResult result : results) {
                System.out.println(result.toString());
            }
        }
    }
}
