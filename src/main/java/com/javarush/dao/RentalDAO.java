package com.javarush.dao;

import com.javarush.domain.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getAnyUnreturnedRental() {
        String hql = "select r from Rental r where r.returnDate is null";
        Query<Rental> query = getCurrentSession().createQuery(hql, Rental.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public int getNumberOfInventoriesOfFilm(short filmId) {
        Query<Long> query = getCurrentSession()
                .createQuery("select count(r) from Rental r where r.inventory.film.id = :FILM and r.returnDate is null", Long.class);
        query.setParameter("FILM", filmId);
        query.setMaxResults(1);
        long result = query.getSingleResult();
        return (int) result;
    }

    public List<Rental> test (short filmId) {
        Query<Rental> query = getCurrentSession()
                .createQuery("select r from Rental r where r.inventory.film.id = :FILM and r.returnDate is null", Rental.class);
        query.setParameter("FILM", filmId);
        return query.list();
    }

    public int getFreeInventory(short filmId) {
        Query<Integer> query = getCurrentSession()
                .createQuery("select r.inventory.id from Rental r where r.inventory.film.id = :FILM and r.returnDate is null", Integer.class);
        query.setParameter("FILM", filmId);
        List<Integer> rentalInvId = query.list();

        Query<Integer> query2 = getCurrentSession()
                .createQuery("select i.id from Inventory i where i.id not in(:LIST)", Integer.class);
        query2.setParameterList("LIST", rentalInvId.toArray());
        query2.setMaxResults(1);
        return query2.getSingleResult();
    }
}
