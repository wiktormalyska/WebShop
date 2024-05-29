package com.wiktormalyska.backend.dao;

import com.wiktormalyska.backend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


public interface ICartRepository {
    void addCart(Cart cart);
    void removeCart(int id);
    Collection<Cart> getCarts();
    Cart getCart(int id);
    void saveOrUpdate(Cart cart);
    void addToCart(Cart cart, int itemId, int quantity);
    void removeFromCart(int itemId);
}
