package dao;

import java.sql.*;

import utils.DBConnector;

public class BlockDao {

    public void addBlock(Integer to, Integer from, byte direction) throws SQLIntegrityConstraintViolationException {
        try (PreparedStatement statement = DBConnector.getConnection()
                .prepareStatement("INSERT INTO USERS_BLOCKS (USER_TO_BLOCK, USER_FROM_BLOCK, DIRECTION) VALUES (?, ?, ?)")) {

            statement.setInt(1, to);
            statement.setInt(2, from);
            statement.setByte(3, direction);

            statement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Byte getBlockSum(Integer userId) {
        try (PreparedStatement statement = DBConnector.getConnection()
                .prepareStatement("SELECT SUM(DIRECTION) as BLOCK_SUM FROM USERS_BLOCKS WHERE USER_TO_BLOCK = ? GROUP BY USER_TO_BLOCK")) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return rs.getByte("BLOCK_SUM");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -100;
    }
}
