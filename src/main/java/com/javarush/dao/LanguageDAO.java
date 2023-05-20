package com.javarush.dao;

import com.javarush.domain.Language;
import org.hibernate.SessionFactory;

public class LanguageDAO extends GenericDAO<Language> {
    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }

    @Override
    public Language getById(int id) {
        return getCurrentSession().get(Language.class, (byte) id);
    }
}
