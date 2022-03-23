package be.ugent.flash.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class JDBCPartDAO extends JDBCAbstractDAO implements PartDAO {

    public JDBCPartDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Queue<Parts> specificPart(int id) throws DataAccesException {
        try (PreparedStatement ps = prepare("SELECT part_id, part FROM parts WHERE question_id=? ORDER BY part_id")) {
            ps.setInt(1, id);
            ResultSet data= ps.executeQuery();

            Queue<Parts> parts=new LinkedList<>();

            while(data.next()){
                parts.add(new Parts(data.getInt(1),data.getInt(2),data.getString(3)));
            }
            return parts;
        }catch (SQLException e){
            throw new DataAccesException("Could not find parts for question with id: "+id,e);
        }
    }
}
