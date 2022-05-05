package be.ugent.flash.jdbc;

import java.util.ArrayList;

public interface QuestionDAO {
    ArrayList<Question> allQuestionData();

    Question specificQuestion(int id);

    void removeQuestion(int questionId);

    void updateQuestion(Question question);

    Question addQuestion(String text, String s, String s1);
}
