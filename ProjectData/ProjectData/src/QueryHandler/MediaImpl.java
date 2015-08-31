package QueryHandler;

import ConnectionManager.ConnectionManager;

import javax.print.attribute.standard.Media;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by jls on 2/16/15.
 */
public class MediaImpl implements Query {

    @Override
    public void createTable() {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS media(id int primary key unqiue auto_increment," +
                    "ID int(55), VISITS varchar(55), VISITORS varchar(55), AVERAGE VISITS BY PERSON int(55), APPROVAL int(55))");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void insert() {

    }

    @Override
    public Results selectById(int id) {
        return null;
    }

    @Override
    public List<Results> selectAll() {
        return null;
    }

    @Override
    public void delete() {

    }

    @Override
    public void update(Media media, int id) {

    }
}
