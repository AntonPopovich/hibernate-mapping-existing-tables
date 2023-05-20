package com.javarush.dao;

import com.javarush.domain.Actor;
import org.hibernate.SessionFactory;

public class ActorDAO extends GenericDAO<Actor> {
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }

    @Override
    public Actor getById(int id) {
        return getCurrentSession().get(Actor.class, (short) id);
    }
}
