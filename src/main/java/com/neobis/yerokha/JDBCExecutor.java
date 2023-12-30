package com.neobis.yerokha;


import com.neobis.yerokha.dao.BeerDAO;
import com.neobis.yerokha.entity.beer.Beer;
import com.neobis.yerokha.entity.beer.Container;
import com.neobis.yerokha.entity.beer.Style;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCExecutor {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(
                "localhost", "beernestdb", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            BeerDAO beerDAO = new BeerDAO(connection);

//            createBeer(beerDAO);
//            readBeerById(beerDAO);
//            readAllBear(beerDAO);
//            updateBeerById(beerDAO);
            deleteBeerById(beerDAO);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to establish connection");
        }
    }

    private static void deleteBeerById(BeerDAO beerDAO) {
        long id = 1;
        int rowsDeleted = beerDAO.deleteById(id);
        System.out.println("Rows deleted: " + rowsDeleted);
    }

    private static void updateBeerById(BeerDAO beerDAO) {
        long id = 1;
        Beer beer = beerDAO.findById(id);
        System.out.println(beer.getName() + " " + beer.getAlcohol() + " " + beer.getCountry());
        beer.setName("Guinness");
        beer.setAlcohol(4.2);
        beer.setCountry("Ireland");
        beer = beerDAO.update(beer);
        System.out.println(beer.getName() + " " + beer.getAlcohol() + " " + beer.getCountry());
    }

    private static void readAllBear(BeerDAO beerDAO) {
        List<Beer> beerList = beerDAO.findAll();
        beerList.forEach(System.out::println);

    }

    private static void readBeerById(BeerDAO beerDAO) {
        long id = 1;

        Beer beer = beerDAO.findById(id);
        System.out.println(beer);
    }

    private static void createBeer(BeerDAO beerDAO) {
        Beer beer = new Beer();
        beer.setName("Corona");
        beer.setStyle(Style.LAGER);
        beer.setAlcohol(4.6);
        beer.setContainer(Container.BOTTLE);
        beer.setSize(355);
        beer.setPrice(BigDecimal.valueOf(0.7));
        beer.setCountry("Mexico");
        beer.setStockAmount(120);

        beerDAO.create(beer);

        List<Beer> beerList = beerDAO.findAll();
        beerList.forEach(System.out::println);
    }
}
