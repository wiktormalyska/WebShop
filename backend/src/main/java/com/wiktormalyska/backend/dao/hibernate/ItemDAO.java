package com.wiktormalyska.backend.dao.hibernate;

import com.wiktormalyska.backend.dao.IItemRepository;
import com.wiktormalyska.backend.model.Item;
import com.wiktormalyska.backend.configuration.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ItemDAO implements IItemRepository {
    private static ItemDAO instance;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void addItem(Item item) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            // if item has no id, persist it, otherwise merge it
            if (item.getId() == 0) {
                session.persist(item);
            } else {
                session.merge(item);
            }
            transaction.commit();
        }
    }

    @Override
    public void removeItem(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Item item = session.get(Item.class, id);
            session.remove(item);
            transaction.commit();
        }
    }

    @Override
    public Collection<Item> getItems() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Item", Item.class).getResultList();
        }
    }

    @Override
    public Item getItem(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Item.class, id);
        }
    }
    public static ItemDAO getInstance(){
        if(instance == null){
            instance = new ItemDAO();
        }
        return instance;
    }
}
