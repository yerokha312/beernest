package com.neobis.yerokha.dao;

import com.neobis.yerokha.entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends DataAccessObject<User> {

    private static final String INSERT = "INSERT INTO ";

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(User dto) {
        try {
            this.connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(INSERT);) {

            } catch (SQLException e) {

                try {
                    connection.rollback();
                } catch (SQLException rbe) {
                    rbe.printStackTrace();
                    throw new RuntimeException("Unable to rollback");
                }
                throw e;
            } finally {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(User dto) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
