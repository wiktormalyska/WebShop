package com.wiktormalyska.backend.dao.hibernate;

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
    public void addItem(Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(product);
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
            Product product = session.get(Product.class, id);
            session.remove(product);
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
    public Collection<Product> getItems() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Collection<Product> products;
        try {
            transaction = session.beginTransaction();
            products = session.createQuery("from Product", Product.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return products;
    }

    @Override
    public Product getItem(int id) {
        Session session = sessionFactory.openSession();
        Product product = null;
        try {
            product = session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }
}
