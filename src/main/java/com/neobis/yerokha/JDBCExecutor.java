package com.neobis.yerokha;


import com.neobis.yerokha.dao.BeerDAO;
import com.neobis.yerokha.dao.CustomerDAO;
import com.neobis.yerokha.entity.beer.Beer;
import com.neobis.yerokha.entity.beer.Container;
import com.neobis.yerokha.entity.beer.Style;
import com.neobis.yerokha.entity.user.Customer;
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
//            readAllBeer(beerDAO);
//            updateBeerById(beerDAO);
//            deleteBeerById(beerDAO);

//            createCustomer(customerDAO);
//            readCustomerById(customerDAO);
//            realAllCustomers(customerDAO);
//            updateCustomerById(customerDAO);
//            deleteCustomerById(customerDAO);


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to establish connection");
        }
    }

    private static void deleteCustomerById(CustomerDAO customerDAO) {
        long id = 1;
        int rowsDeleted = customerDAO.deleteById(id);

        System.out.println("Rows deleted: " + rowsDeleted);
    }

    private static void updateCustomerById(CustomerDAO customerDAO) {
        long id = 1;
        User customer;

        customer = customerDAO.findById(id);
        System.out.println(customer);

        customer.setFirstName("Aqniyet");
        customer.setLastName("Boranbayeva");
        customer.setEmail("b.aqniyet@mail.ru");

        int rowsAffected = customerDAO.update(customer);

        customer = customerDAO.findById(id);

        System.out.println("Rows affected: " + rowsAffected);
        System.out.println(customer);
    }

    private static void realAllCustomers(CustomerDAO customerDAO) {
        List<User> customers;

        customers = customerDAO.findAll();

        customers.forEach(System.out::println);
    }

    private static void readCustomerById(CustomerDAO customerDAO) {
        long id = 1;

        User customer = customerDAO.findById(id);

        System.out.println(customer);
    }

    private static void createCustomer(CustomerDAO customerDAO) {
        User customer = new Customer();

        customer.setFirstName("Rakhat");
        customer.setLastName("Sabanchiyev");
        customer.setBirthDate(Date.valueOf("1993-03-03"));
        customer.setEmail("s.rakhat@mail.ru");
        customer.setPhoneNumber("+7 (702) 391-09-56");
        customer.setPassword("tell-no-one");

        customerDAO.create(customer);
    }

    private static void deleteBeerById(BeerDAO beerDAO) {
        long id = 1;

        int rowsDeleted = beerDAO.deleteById(id);

        System.out.println("Rows deleted: " + rowsDeleted);
    }

    private static void updateBeerById(BeerDAO beerDAO) {
        long id = 3;

        Beer beer = beerDAO.findById(id);

        System.out.println(beer.getName() + " " + beer.getAlcohol() + " " + beer.getCountry());

        beer.setName("Jack");
        beer.setAlcohol(5.4);
        beer.setCountry("Mexico");

        int rowsAffected = beerDAO.update(beer);

        beer = beerDAO.findById(id);

        System.out.println("Rows affected: " + rowsAffected);
        System.out.println(beer.getName() + " " + beer.getAlcohol() + " " + beer.getCountry());
    }

    private static void readAllBeer(BeerDAO beerDAO) {
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
