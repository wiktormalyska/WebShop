package com.wiktormalyska.backend.dao;

import com.wiktormalyska.backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);
}
