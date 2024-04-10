package com.wiktormalyska.backend.dao;

import com.wiktormalyska.backend.model.Item;

import java.util.Collection;

public interface IItemRepository {
    void addItem(Item item);
    void removeItem(int id);
    Collection<Item> getItems();
    Item getItem(int id);
}
