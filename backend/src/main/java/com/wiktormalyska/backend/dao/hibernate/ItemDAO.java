package com.wiktormalyska.backend.dao.hibernate;

import com.wiktormalyska.backend.dao.IItemRepository;
import com.wiktormalyska.backend.model.Item;
import com.wiktormalyska.backend.utils.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@NoArgsConstructor
@Repository
public class ItemDAO implements IItemRepository {
    private static ItemDAO instance;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @PersistenceContext
    private EntityManager entityManager;
    private final String GET_ALL_JPQL = "FROM com.wiktormalyska.backend.model.Item";
    private final String GET_BY_ID_JPQL = "FROM com.wiktormalyska.backend.model.Item WHERE id = :id";

    @Override
    public void addItem(Item item) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
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
            return session.createQuery(GET_ALL_JPQL, Item.class).getResultList();
        }
    }

    @Override
    public Item getItem(int id) {
        TypedQuery<Item> query = entityManager.createQuery(GET_BY_ID_JPQL, Item.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void saveOrUpdate(Item item) {
        System.out.println("ITEM " + item);
        if (getItem(item.getId())==null){
            entityManager.persist(item);
        } else {
            entityManager.merge(item);
        }
    }

    public static ItemDAO getInstance(){
        if(instance == null){
            instance = new ItemDAO();
        }
        return instance;
    }

    public ItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
