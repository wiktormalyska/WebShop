package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IItemService {
    void saveOrUpdate(Item item);
    Item getById(int id);
    Collection<Item> getAll();
    void delete(int id);
}
