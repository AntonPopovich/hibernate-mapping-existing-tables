package com.javarush;

import com.javarush.dao.*;
import com.javarush.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class MySessionFactory {
        private final SessionFactory sessionFactory;
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmtextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public MySessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/hibernate-2");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

            sessionFactory = new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(Actor.class)
                    .addAnnotatedClass(Address.class)
                    .addAnnotatedClass(Category.class)
                    .addAnnotatedClass(City.class)
                    .addAnnotatedClass(Country.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Film.class)
                    .addAnnotatedClass(FilmText.class)
                    .addAnnotatedClass(Inventory.class)
                    .addAnnotatedClass(Language.class)
                    .addAnnotatedClass(Payment.class)
                    .addAnnotatedClass(Rental.class)
                    .addAnnotatedClass(Staff.class)
                    .addAnnotatedClass(Store.class)
                    .buildSessionFactory();

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmtextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
        }

    public Customer createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Store store = storeDAO.getItems(0, 1).get(0);
            City city = cityDAO.getByName("Akron");

            Address address = new Address();
            address.setAddress("Mountain View, 2, USA");
            address.setDistrict("North dst");
            address.setCity(city);
            address.setPhone("222-333-444");
            addressDAO.save(address);

            Customer customer = new Customer();
            customer.setStore(store);
            customer.setFirstName("John");
            customer.setLastName("Kallman");
            customer.setEmail("testABC@ya.ru");
            customer.setAddress(address);
            customer.setActive(true);
            customerDAO.save(customer);

            session.getTransaction().commit();

            return customer;
        }
    }

    public void customerReturnInventoryToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Rental rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    public void customerNewRent() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Short filmId = 1;
            int numberOfInventories = filmDAO.getInventoryOfFilmCount(filmId);
            int numberOnHands = rentalDAO.getNumberOfInventoriesOfFilm(filmId);
            Customer customer = customerDAO.getCustomerByName("SUSAN", "WILSON");
            Staff staff = staffDAO.getById((byte) 1);
            Film film = filmDAO.getById(filmId);
            int freeInventory = rentalDAO.getFreeInventory(filmId);
            Inventory inventory = inventoryDAO.getById(freeInventory);

            if (numberOfInventories >= numberOnHands) {
                Rental rental = new Rental();                           //rental
                rental.setCustomer(customer);
                rental.setInventory(inventory);
                rental.setStaff(inventory.getStore().getStaff());
                rentalDAO.save(rental);

                Payment payment = new Payment();                        //payment
                payment.setCustomer(customer);
                payment.setStaff(staff);
                payment.setRental(rental);
                payment.setAmount(film.getRentalRate());
                paymentDAO.save(payment);

                session.getTransaction().commit();

            }
        }
    }
        public int countInventoriesOfFilm(int id) {
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();
                int result = filmDAO.getInventoryOfFilmCount((short) id);
                session.getTransaction().commit();
                return result;
            }
        }

        public int countRentalsOfFilm(int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            int result = rentalDAO.getNumberOfInventoriesOfFilm((short) id);
            session.getTransaction().commit();
            return result;
        }
    }

        public void createRental() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Rental rental = new Rental();
            rental.setInventory(inventoryDAO.getById(1));
            rental.setCustomer(customerDAO.getCustomerByName("SUSAN", "WILSON"));
            rental.setStaff(staffDAO.getById((byte) 1));

            rentalDAO.save(rental);

            session.getTransaction().commit();
        }
    }

    public List<Rental> test (int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Rental> rentals = rentalDAO.test((short) id);
            session.getTransaction().commit();
            return rentals;
        }
    }

    public void addNewFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
                Set<Actor> actors = Set.of(actorDAO.getById(2), actorDAO.getById(7),
                        actorDAO.getById(4), actorDAO.getById(5));
                Set<Category> categories = Set.of(categoryDAO.getById(7), categoryDAO.getById(13));

            Film newFilm = new Film();
            newFilm.setActors(actors);
            newFilm.setCategories(categories);
            newFilm.setTitle("GoodFellas");
            newFilm.setLanguage(languageDAO.getById(2));
            newFilm.setRentalDuration((byte) 8);
            newFilm.setRentalRate(BigDecimal.valueOf(4.65));
            newFilm.setReplacementCost(BigDecimal.valueOf(20.99));
            newFilm.setRating(Rating.R);
                filmDAO.save(newFilm);

            session.getTransaction().commit();
        }
    }

    public void getRental() {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            RentalDAO rentalDAO1 = new RentalDAO(sessionFactory);
            Rental rental = rentalDAO1.getById(1);
            session.getTransaction().commit();
        }
    }
}
