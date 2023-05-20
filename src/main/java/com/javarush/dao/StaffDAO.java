package com.javarush.dao;

import com.javarush.domain.Staff;
import org.hibernate.SessionFactory;

public class StaffDAO extends GenericDAO<Staff> {
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }

    @Override
    public Staff getById(int id) {
        return getCurrentSession().get(Staff.class, (byte) id);
    }
}
