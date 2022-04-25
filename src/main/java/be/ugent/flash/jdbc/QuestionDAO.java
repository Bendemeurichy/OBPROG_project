package be.ugent.flash.jdbc;

import java.util.ArrayList;

public interface QuestionDAO {
    //Vraag alle algemene data op van de vragen uit de databank
    ArrayList<Question> allQuestionData() throws DataAccesException;

    //retrieve data from a question with a specific question id
    Question specificQuestion(int id) throws DataAccesException;

    //verwijder een specifieke vraag uit de db
    void removeQuestion(int questionID) throws DataAccesException;
}
