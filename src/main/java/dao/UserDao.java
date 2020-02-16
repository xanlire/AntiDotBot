package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;


import org.telegram.telegrambots.api.objects.User;
import utils.DBConnector;

public class UserDao {
    public void save(User user) throws SQLIntegrityConstraintViolationException {

        try (PreparedStatement preparedStatement = DBConnector.getConnection()
                .prepareStatement("INSERT INTO USERS (ID, USERNAME, FIRSTNAME, LASTNAME) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, "USER");

            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(User user) {
        try (PreparedStatement statement = DBConnector.getConnection()
                .prepareStatement("UPDATE USERS SET USERNAME = ?, FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?")) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setInt(4, user.getId());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<model.user.User> getAll(){
        Set<model.user.User> userSet = new HashSet<>();
        try(PreparedStatement preparedStatement = DBConnector.getConnection()
                .prepareStatement("SELECT * FROM USERS")){

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                model.user.User user = new model.user.User();
                user.setId(rs.getInt("ID"));
                user.setFirstname(rs.getString("FIRSTNAME"));
                user.setUsername(rs.getString("USERNAME"));
                user.setLastname(rs.getString("LASTNAME"));

                userSet.add(user);
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return userSet;
    }
}
