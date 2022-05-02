package be.ugent.flash.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCPartDAO extends JDBCAbstractDAO implements PartDAO {

    public JDBCPartDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Parts> specificPart(int id) throws DataAccesException {
        try (PreparedStatement ps = prepare("SELECT question_id,part_id, part FROM parts WHERE question_id=? ORDER BY part_id")) {
            ps.setInt(1, id);
            ResultSet data= ps.executeQuery();

            ArrayList<Parts> parts=new ArrayList<>();

            while(data.next()){
                parts.add(new Parts(data.getInt(2),data.getString(3)));
            }
            return parts;
        }catch (SQLException e){
            throw new DataAccesException("Could not find answers for question with id: "+id,e);
        }
    }

    public ArrayList<ImageParts> specificImagepart(int id) throws DataAccesException {
        try (PreparedStatement ps = prepare("SELECT question_id,part_id, part FROM parts WHERE question_id=? ORDER BY part_id")) {
            ps.setInt(1, id);
            ResultSet data= ps.executeQuery();

            ArrayList<ImageParts> parts=new ArrayList<>();

            while(data.next()){
                parts.add(new ImageParts(data.getInt(1),data.getInt(2),data.getBytes(3)));
            }
            return parts;
        }catch (SQLException e){
            throw new DataAccesException("Could not find answers for question with id: "+id,e);
        }
    }

        @Override
        public void removeParts(int questionID) throws DataAccesException {
            try (PreparedStatement ps = prepare("DELETE FROM parts WHERE question_id = ?")){
                ps.setInt(1,questionID);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccesException("could not find answers for question with id " +questionID,e);
            }
        }

    @Override
    public void addParts(ArrayList<Parts> parts) throws DataAccesException {
        if ( parts!=null){
        for(Parts part:parts) {
            try (PreparedStatement ps = prepare("INSERT INTO parts(question_id,part) VALUES(?, ?)")) {
                ps.setInt(1, part.question_id());
                ps.setString(2,part.part());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccesException("could not find answers for question with id " + part.question_id(), e);
            }
        }
    }
    }


}
