package com.neobis.yerokha.dao;

import com.neobis.yerokha.entity.beer.Beer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


    public BeerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Beer dto) {
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = connection.prepareStatement(INSERT);) {
            dtoToStatement(dto, statement);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                throw new RuntimeException(sqle);
            }
            e.printStackTrace();
            throw new RuntimeException("Unable to create beer");
        }
    }

    @Override
    public Beer findById(Long id) {
        Beer beer = new Beer();
        try (PreparedStatement statement = connection.prepareStatement(GET_ONE);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                resultSetToDto(resultSet, beer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to find beer");
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to find all beers");
        }
        return beerList;
    }

    @Override
    public Beer update(Beer dto) {
        Beer beer;
        int rowsAffected;
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE);) {
            dtoToStatement(dto, statement);
            statement.setLong(9, dto.getId());
            rowsAffected = statement.executeUpdate();
            this.connection.commit();
            beer = this.findById(dto.getId());
        } catch (SQLException e) {
            try {
                this.connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                throw new RuntimeException(sqle);
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("Rows affected: " + rowsAffected);
        return beer;
    }

    @Override
    public int deleteById(Long id) {
        int rowsAffected;
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE);) {
            statement.setLong(1, id);
            rowsAffected = statement.executeUpdate();
            this.connection.commit();
        } catch (SQLException e) {
            try {
                this.connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                throw new RuntimeException(sqle);
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }
}
