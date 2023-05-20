package com.javarush.dao;

import com.javarush.domain.Inventory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class InventoryDAO extends GenericDAO<Inventory> {
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
