package be.ugent.flash.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCQuestionDAO extends JDBCAbstractDAO implements QuestionDAO {
    public JDBCQuestionDAO(Connection connection) {
        super(connection);
    }

    //Vraag alle algemene data op van de vragen uit de databank
    @Override
    public ArrayList<Question> allQuestionData() throws DataAccesException {
        try (PreparedStatement ps = prepare("SELECT question_id, title, text_part, image_part, correct_answer, " +
                "question_type FROM questions ORDER BY question_id")) {
            ResultSet data= ps.executeQuery();

            ArrayList<Question> questions=new ArrayList<>();

            while(data.next()){
                Question question= new Question(data.getInt(1),data.getString(2),
                        data.getString(3),data.getBytes(4),data.getString(6),
                        data.getString(5));
                questions.add(question);
            }
            return questions;
        }catch (SQLException e){
            throw new DataAccesException("Could not find any questions",e);
        }
    }

    //retrieve data from a question with a specific question id
    @Override
    public Question specificQuestion(int id) throws DataAccesException {
        try (PreparedStatement ps = prepare("SELECT question_id, title, text_part, image_part, correct_answer," +
                " question_type FROM questions WHERE question_id = ?")) {
            ps.setInt(1, id);
            ResultSet data= ps.executeQuery();

            return new Question(data.getInt(1),data.getString(2),data.getString(3),
                    data.getBytes(4),data.getString(6),data.getString(5));
        }catch (SQLException e){
            throw new DataAccesException("Could not find question with id: "+id,e);
        }
    }


    @Override
    public void removeQuestion(int questionID) throws DataAccesException {
        try (PreparedStatement ps = prepare("DELETE FROM questions WHERE question_id = ?")){
            ps.setInt(1,questionID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccesException("could not find answers for question with id " +questionID,e);
        }
    }

    @Override
    public void updateQuestion(Question question) throws DataAccesException {
        try (PreparedStatement ps =prepare("UPDATE questions SET title= ?, text_part= ?, image_part= ?, question_type= ? , correct_answer= ? WHERE question_id = ?")){
            ps.setString(1,question.title());
            ps.setString(2,question.text_part());
            ps.setBytes(3,question.image_part());
            ps.setString(4,question.question_type());
            ps.setString(5,question.correct_answer());
            ps.setInt(6,question.question_id());
            ps.executeUpdate();
        } catch(SQLException e){
            throw new DataAccesException("could not find question with id " +question.question_id(),e);
        }
    }

    @Override
    public Question addQuestion(String title, String type) throws DataAccesException {
        Question generatedQuestion;
        try(PreparedStatement ps = prepare("Insert INTO questions(title,question_type) Values(?,?)")){
            ps.setString(1,title);
            ps.setString(2,type);
            ps.executeUpdate();

            generatedQuestion=specificQuestion(ps.getGeneratedKeys().getInt(1));
        } catch (SQLException e) {
            throw new DataAccesException("could not create question",e);
        }
        return generatedQuestion;
    }
}
