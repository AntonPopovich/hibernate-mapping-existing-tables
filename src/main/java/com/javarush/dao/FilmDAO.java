package com.javarush.dao;

import com.javarush.domain.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends GenericDAO<Film> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public int getInventoryOfFilmCount(short filmId) {
        Query<Long> query = getCurrentSession().
                createQuery("select count(film) from Inventory where film.id = :FILM", Long.class);
        query.setParameter("FILM", filmId);
        query.setMaxResults(1);
        long res = query.getSingleResult();
        return (int) res;
    }

    @Override
    public Film getById(int id) {
        return getCurrentSession().get(Film.class, (short) id);
    }
}
