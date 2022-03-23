package be.ugent.flash.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class JDBCQuestionDAO extends JDBCAbstractDAO implements QuestionDAO {
    public JDBCQuestionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Queue<Question> allQuestionData() throws DataAccesException {
        try (PreparedStatement ps = prepare("SELECT question_id, title, text_part, image_part, correct_answer, " +
                "question_type FROM questions ORDER BY question_id")) {
            ResultSet data= ps.executeQuery();

            Queue<Question> questions=new LinkedList<>();

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
}
