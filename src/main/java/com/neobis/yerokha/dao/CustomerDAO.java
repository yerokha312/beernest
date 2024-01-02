package com.neobis.yerokha.dao;

import com.neobis.yerokha.entity.user.Customer;
import com.neobis.yerokha.entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.neobis.yerokha.util.UserDtoConverter.dtoToStatement;
import static com.neobis.yerokha.util.UserDtoConverter.resultSetToDto;

public class CustomerDAO extends DataAccessObject<User> {
    private static final String INSERT = ("INSERT INTO customer (first_name, last_name, dob, " +
            "email, phone_number, password) " +
            "VALUES (?,?,?,?,?,?)");

    private static final String GET_ONE = ("SELECT customer.customer_id, first_name, last_name, dob, " +
            "email, phone_number, password " +
            "FROM customer WHERE customer_id=?");
    private static final String GET_ALL = "SELECT * FROM customer";
    private static final String UPDATE = "UPDATE customer SET first_name=?, last_name=?, dob=?, " +
            "email=?, phone_number=?, password=? " +
            "WHERE customer_id=?";

    private static final String DELETE = "DELETE FROM customer WHERE customer_id=?";


    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(User dto) {
        try {
            this.connection.setAutoCommit(false);
            try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
                dtoToStatement(dto, statement);
                statement.executeUpdate();
                this.connection.commit();
            } catch (SQLException sqlEx) {
                try {
                    this.connection.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                    throw new RuntimeException("Rollback exception");
                }
                sqlEx.printStackTrace();
                throw new RuntimeException("Unable to create object");
            }
        } catch (SQLException conEx) {
            conEx.printStackTrace();
            throw new RuntimeException(conEx);
        }
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new Customer();
                resultSetToDto(resultSet, user);
            }
            resultSet.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException("Unable to find object");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new Customer();
                resultSetToDto(resultSet, user);
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException("Unable to find customers");
        }
        return users;
    }

    @Override
    public int update(User dto) {
        int rowsAffected;
        try {
            this.connection.setAutoCommit(false);
            try (PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
                dtoToStatement(dto, statement);
                statement.setLong(7, dto.getId());
                rowsAffected = statement.executeUpdate();
                this.connection.commit();
            } catch (SQLException sqlEx) {
                try {
                    this.connection.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                    throw new RuntimeException("Rollback exception");
                }
                sqlEx.printStackTrace();
                throw new RuntimeException("Unable to update customer");
            }
        } catch (SQLException conEx) {
            conEx.printStackTrace();
            throw new RuntimeException("Connection exception");
        }

        return rowsAffected;
    }


    @Override
    public int deleteById(Long id) {
        int rowsDeleted;
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
                statement.setLong(1, id);
                rowsDeleted = statement.executeUpdate();
                connection.commit();
            } catch (SQLException sqlEx) {
                try {
                    connection.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                    throw new RuntimeException("Rollback exception");
                }
                sqlEx.printStackTrace();
                throw new RuntimeException("Unable to delete customer");
            }
        } catch (SQLException conEx) {
            conEx.printStackTrace();
            throw new RuntimeException("Connection exception");
        }
        return rowsDeleted;
    }
}
