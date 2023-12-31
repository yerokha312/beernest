package com.neobis.yerokha;


import com.neobis.yerokha.dao.BeerDAO;
import com.neobis.yerokha.dao.CustomerDAO;
import com.neobis.yerokha.entity.beer.Beer;
import com.neobis.yerokha.entity.beer.Container;
import com.neobis.yerokha.entity.beer.Style;
import com.neobis.yerokha.entity.user.Customer;
import com.neobis.yerokha.entity.user.Employee;
import com.neobis.yerokha.entity.user.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class JDBCExecutor {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager(
                "localhost", "beernestdb", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            BeerDAO beerDAO = new BeerDAO(connection);
            CustomerDAO customerDAO = new CustomerDAO(connection);

//            createBeer(beerDAO);
//            readBeerById(beerDAO);
//            readAllBear(beerDAO);
//            updateBeerById(beerDAO);
//            deleteBeerById(beerDAO);

//            createCustomer(userDAO);
            readCustomerById(customerDAO);

//            createEmployee(userDAO);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to establish connection");
        }
    }

    private static void readCustomerById(CustomerDAO customerDAO) {
        long id = 1;
        User customer = customerDAO.findById(id);
        System.out.println(customer);
    }

    private static void createEmployee(CustomerDAO customerDAO) {
        User employee = new Employee();

        employee.setFirstName("Arman");
        employee.setLastName("Khassenov");
        employee.setBirthDate(Date.valueOf("1999-02-02"));
        employee.setEmail("a.khassenov@mail.ru");
        employee.setPhoneNumber("+7 (777) 333-44-55");
        employee.setPassword("nobody-knows");

        customerDAO.create(employee);
    }

    private static void createCustomer(CustomerDAO customerDAO) {
        User customer = new Customer();

        customer.setFirstName("Bolat");
        customer.setLastName("Askarov");
        customer.setBirthDate(Date.valueOf("1970-01-01"));
        customer.setEmail("b.askarov@mail.ru");
        customer.setPhoneNumber("+7 (701) 123-45-67");
        customer.setPassword("top-secret");

        customerDAO.create(customer);
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
        long id = 2;

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
