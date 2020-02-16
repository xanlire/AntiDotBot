package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.telegram.telegrambots.api.objects.Message;

import model.user.User;
import utils.DBConnector;

public class MessageDao {

    public void save(Message message){

        try (PreparedStatement preparedStatement = DBConnector.getConnection()
                .prepareStatement("INSERT INTO MESSAGES (ID, USER_ID, TEXT, DATE_RECEIVE) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setInt(1, message.getMessageId());
            preparedStatement.setInt(2, message.getFrom().getId());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4, Timestamp.from(Instant.now()));

            preparedStatement.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<User> getTop10() {
        Set<User> topUsers = new HashSet<>();
        try (PreparedStatement preparedStatement = DBConnector.getConnection()
                .prepareStatement("SELECT U.*, MESS.MESSAGE_CNT FROM USERS U, " +
                        "  (SELECT USER_ID, COUNT(ID) MESSAGE_CNT FROM MESSAGES GROUP BY USER_ID) MESS " +
                        "WHERE U.ID = MESS.USER_ID ORDER BY MESS.MESSAGE_CNT DESC")) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                User user = new User();

                user.setUsername(rs.getString("USERNAME"));
                user.setFirstname(rs.getString("FIRSTNAME"));
                user.setLastname(rs.getString("LASTNAME"));
                user.setMessageCount(rs.getInt("MESSAGE_CNT"));

                topUsers.add(user);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return topUsers;
    }
}
