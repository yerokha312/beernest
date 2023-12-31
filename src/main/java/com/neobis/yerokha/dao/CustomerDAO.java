package com.neobis.yerokha.dao;

import com.neobis.yerokha.entity.user.Customer;
import com.neobis.yerokha.entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.neobis.yerokha.util.UserDtoConverter.dtoToStatement;
import static com.neobis.yerokha.util.UserDtoConverter.resultSetToDto;

public class CustomerDAO extends DataAccessObject<User> {
    private static final String INSERT = ("INSERT INTO customer (first_name, last_name, dob, " +
            "email, phone_number, password) " +
            "VALUES (?,?,?,?,?,?)");

    private static final String GET_ONE = ("SELECT customer.customer_id, first_name, last_name, dob, email, phone_number, password " +
            "FROM customer WHERE customer_id=?");

    private static final String GET_ONE_EMPLOYEE = ("SELECT first_name, last_name, dob, email, phone_number, password " +
            "FROM employee WHERE employee_id=?");
    private static final Logger log = Logger.getLogger(CustomerDAO.class.getName());


    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(User dto) {
        try {
            this.connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(INSERT);) {
                dtoToStatement(dto, statement);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException sqlEx) {
                try {
                    connection.rollback();
                } catch (SQLException rbe) {
                    rbe.printStackTrace();
                    throw new RuntimeException("Unable to rollback");
                }
                sqlEx.printStackTrace();
                throw new RuntimeException("Unable to create object");
            } finally {
                connection.close();
            }

        } catch (SQLException conEx) {
            conEx.printStackTrace();
            throw new RuntimeException(conEx);
        }
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement(GET_ONE);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new Customer();
                resultSetToDto(resultSet, user);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException("Unable to find object");
        } finally {
            try {
                connection.close();
            } catch (SQLException conEx) {
                log.log(Level.SEVERE, "Unable to close connection", conEx);
            }
        }
        return user;
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
