package com.wiktormalyska.backend.dao;

import com.wiktormalyska.backend.dao.hibernate.Product;

import java.util.Collection;

public interface IItemRepository {
    void addItem(Product product);
    void removeItem(int id);
    Collection<Product> getItems();
    Product getItem(int id);
}
