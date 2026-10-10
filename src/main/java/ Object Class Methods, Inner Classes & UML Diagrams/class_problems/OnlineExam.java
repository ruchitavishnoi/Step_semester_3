import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Question {
    private int questionId;
    private String text;

    public Question(int questionId, String text) {
        this.questionId = questionId;
        this.text = text;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {
    private String correctAnswer;

    public MultipleChoiceQuestion(int questionId, String text, String correctAnswer) {
        super(questionId, text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    private String correctAnswer;

    public TrueFalseQuestion(int questionId, String text, String correctAnswer) {
        super(questionId, text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Examination {
    private String title;
    private List<Question> questions;

    public Examination(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class Student {
    private String studentId;
    private String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public Attempt startExamination(Examination examination) {
        System.out.println("Examination '" + examination.getTitle() + "' started by " + name + ".");
        return new Attempt(this, examination);
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<Integer, String> answers;
    private boolean isSubmitted;
    private int score;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        this.answers = new HashMap<>();
        this.isSubmitted = false;
        this.score = 0;
    }

    public void answerQuestion(int questionNumber, String answer) {
        if (isSubmitted) {
            System.out.println("Cannot change answers after submission.");
            return;
        }
        answers.put(questionNumber, answer);
        System.out.println("Question " + questionNumber + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (isSubmitted) {
            System.out.println("Attempt has already been submitted.");
            return;
        }
        isSubmitted = true;
        calculateResult();
        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + examination.getTitle() + "' attempt: " + score + "/" + examination.getQuestions().size() + " correct");
    }

    private void calculateResult() {
        score = 0;
        List<Question> questions = examination.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int qNum = i + 1;
            String userAnswer = answers.get(qNum);
            if (userAnswer != null && q.evaluate(userAnswer)) {
                score++;
            }
        }
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public int getScore() {
        return score;
    }
}

public class OnlineExam {
    public static void main(String[] args) {
        Examination mathQuiz = new Examination("Math Quiz");
        mathQuiz.addQuestion(new MultipleChoiceQuestion(1, "What is 2+2?", "A"));
        mathQuiz.addQuestion(new MultipleChoiceQuestion(2, "What is 3x3?", "B"));

        Student student = new Student("S101", "Student");

        Attempt attempt = student.startExamination(mathQuiz);
        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");
        attempt.submit();
    }
}
