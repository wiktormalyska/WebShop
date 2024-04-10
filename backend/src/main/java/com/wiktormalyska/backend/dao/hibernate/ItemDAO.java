package com.wiktormalyska.backend.dao.hibernate;

import com.wiktormalyska.backend.model.Item;
import com.wiktormalyska.backend.dao.IItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class ItemDAO implements IItemRepository {
    private static ItemDAO instance;
    SessionFactory sessionFactory;

    private ItemDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static ItemDAO getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new ItemDAO(sessionFactory);
        }
        return instance;
    }
    @Override
    public void addItem(Item item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeItem(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Item item = session.get(Item.class, id);
            session.remove(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Collection<Item> getItems() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Collection<Item> items;
        try {
            transaction = session.beginTransaction();
            items = session.createQuery("FROM Item", Item.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return items;
    }

    @Override
    public Item getItem(int id) {
        Session session = sessionFactory.openSession();
        Item item = null;
        try {
            item = session.get(Item.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }
}
