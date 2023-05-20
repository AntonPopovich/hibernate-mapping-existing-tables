package com.javarush;

import com.javarush.dao.FilmDAO;
import com.javarush.domain.Customer;
import com.javarush.domain.Rental;

public class Main {
    public static void main(String[] args) {
        MySessionFactory mySessionFactory = new MySessionFactory();
        //Customer customer = mySessionFactory.createCustomer();

        //mySessionFactory.customerReturnInventoryToStore();
//        mySessionFactory.customerNewRent();
//        System.out.println(mySessionFactory.countInventoriesOfFilm(1));
//        System.out.println(mySessionFactory.countRentalsOfFilm(1));
        //mySessionFactory.addNewFilm();
        mySessionFactory.getRental();
    }
}
