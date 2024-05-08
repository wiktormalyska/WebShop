package com.wiktormalyska.backend.dao.hibernate;

import com.wiktormalyska.backend.configuration.HibernateUtil;
import com.wiktormalyska.backend.dao.IUserRepository;
import com.wiktormalyska.backend.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements IUserRepository {
    private static UserDAO instance;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void addUser(User user) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            if (user.getId() == 0) {
                session.persist(user);
            } else {
                session.merge(user);

            }
            transaction.commit();
        }
    }

    @Override
    public void removeUser(int id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
        }
    }

    @Override
    public Collection<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }

    @Override
    public User getUser(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public Optional<User> getUser(String username) {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList();
            if (!users.isEmpty()) {
                return Optional.of(users.get(0));
            }
            return null;
        }
    }

    public static UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }
}
