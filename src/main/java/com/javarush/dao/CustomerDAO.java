package com.javarush.dao;

import com.javarush.domain.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CustomerDAO extends GenericDAO<Customer> {
    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }

    public Customer getCustomerByName(String firstName, String lastName) {
        Query<Customer> query = getCurrentSession().createQuery("select c from Customer c where c.firstName = :FIRSTNAME and c.lastName = :LASTNAME", Customer.class);
        query.setParameter("FIRSTNAME", firstName);
        query.setParameter("LASTNAME", lastName);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
