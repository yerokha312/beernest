package com.neobis.yerokha.dao;

import com.neobis.yerokha.entity.beer.Beer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.neobis.yerokha.util.BeerDtoConverter.dtoToStatement;
import static com.neobis.yerokha.util.BeerDtoConverter.resultSetToDto;

public class BeerDAO extends DataAccessObject<Beer> {

    private static final String GET_ONE = "SELECT b.beer_id, b.name, s.name, b.alcohol, c.type, b.size, " +
            "b.sell_price, b.country, b.stock_available " +
            "FROM beer b " +
            "JOIN styles s ON b.style_id = s.style_id " +
            "JOIN container c ON b.container_id = c.container_id " +
            "WHERE b.beer_id=?";
    private static final String INSERT = "INSERT INTO beer (name, style_id, alcohol, container_id, size, " +
            "purchase_price, country, stock_available) " +
            "VALUES (?,?,?,?,?,?,?,?)";
    private static final String GET_ALL = "SELECT b.beer_id, b.name, s.name, b.alcohol, c.type, b.size, " +
            "b.sell_price, b.country, b.stock_available " +
            "FROM beer b " +
            "JOIN styles s ON b.style_id = s.style_id " +
            "JOIN container c ON b.container_id = c.container_id";
    private static final String UPDATE = "UPDATE beer SET name=?, style_id=?, alcohol=?, container_id=?, size=?, " +
            "purchase_price=?, country=?, stock_available=? WHERE beer_id=?";
    private static final String DELETE = "DELETE FROM beer WHERE beer_id=?";

    private static final Logger log = Logger.getLogger(BeerDAO.class.getName());

    public BeerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Beer dto) {
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(INSERT);) {
                dtoToStatement(dto, statement);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException sqlEx) {
                try {
                    connection.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                    throw new RuntimeException("Unable to rollback");
                }
                sqlEx.printStackTrace();
                throw new RuntimeException("Unable to create the object");
            } finally {
                connection.close();
            }
        } catch (SQLException conEx) {
            throw new RuntimeException("Connection exception");
        }
    }

    @Override
    public Beer findById(Long id) {
        Beer beer = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_ONE);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                beer = new Beer();
                resultSetToDto(resultSet, beer);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException("Unable to find the object");
        } finally {
            try {
                connection.close();
            } catch (SQLException conEx) {
                log.log(Level.SEVERE, "Unable to close connection", conEx);
            }
        }
        return beer;
    }

    @Override
    public List<Beer> findAll() {
        List<Beer> beerList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Beer beer = new Beer();
                resultSetToDto(resultSet, beer);
                beerList.add(beer);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException("Unable to find the object");
        } finally {
            try {
                connection.close();
            } catch (SQLException conEx) {
                log.log(Level.SEVERE, "Unable to close connection", conEx);
            }
        }
        return beerList;
    }

    @Override
    public Beer update(Beer dto) {
        Beer beer;
        int rowsAffected;
        try {
            this.connection.setAutoCommit(false);
            try (PreparedStatement statement = this.connection.prepareStatement(UPDATE);) {
                dtoToStatement(dto, statement);
                statement.setLong(9, dto.getId());
                rowsAffected = statement.executeUpdate();
                this.connection.commit();
                beer = this.findById(dto.getId());
            } catch (SQLException sqlEx) {
                try {
                    this.connection.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                    throw new RuntimeException(rbEx);
                }
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            } finally {
                connection.close();
            }
        } catch (SQLException conEx) {
            conEx.printStackTrace();
            throw new RuntimeException("Connection exception");
        }
        System.out.println("Rows affected: " + rowsAffected);

        return beer;
    }


    @Override
    public int deleteById(Long id) {
        int rowsAffected;
        try {
            this.connection.setAutoCommit(false);
            try (PreparedStatement statement = this.connection.prepareStatement(DELETE);) {
                statement.setLong(1, id);
                rowsAffected = statement.executeUpdate();
                this.connection.commit();
            } catch (SQLException sqlEx) {
                try {
                    this.connection.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                    throw new RuntimeException(rbEx);
                }
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            } finally {
                connection.close();
            }
        } catch (SQLException conEx) {
            conEx.printStackTrace();
            throw new RuntimeException("Connection exception");
        }
        return rowsAffected;
    }
}