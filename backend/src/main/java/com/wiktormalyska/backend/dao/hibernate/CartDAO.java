package com.wiktormalyska.backend.dao.hibernate;

import com.wiktormalyska.backend.dao.ICartRepository;
import com.wiktormalyska.backend.model.Cart;
import com.wiktormalyska.backend.model.CartItem;
import com.wiktormalyska.backend.model.Item;
import com.wiktormalyska.backend.utils.HibernateUtil;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Repository
@Table(name = "cart")
public class CartDAO implements ICartRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void addCart(Cart cart) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            if (cart.getId() == 0) {
                session.persist(cart);
            } else {
                session.merge(cart);
            }
            transaction.commit();
        }
    }

    @Override
    public void removeCart(int id) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var cart = session.get(Cart.class, id);
            session.remove(cart);
            transaction.commit();
        }
    }

    @Override
    public Collection<Cart> getCarts() {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("FROM com.wiktormalyska.backend.model.Cart", Cart.class).getResultList();
        }
    }

    @Override
    public Cart getCart(int id) {
        try (var session = sessionFactory.openSession()) {
            return session.get(Cart.class, id);
        }
    }

    @Override
    public void saveOrUpdate(Cart cart) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.saveOrUpdate(cart);
            transaction.commit();
        }
    }

    @Override
    public void addToCart(Cart cart, int itemId, int quantity) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var item = session.get(Item.class, itemId);
            cart.getItems().add(new CartItem(cart,item,quantity));
            session.saveOrUpdate(cart);
            transaction.commit();
        }
    }

    @Override
    public void removeFromCart(int itemId) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var item = session.get(Cart.class, itemId);
            session.remove(item);
            transaction.commit();
        }
    }
}
