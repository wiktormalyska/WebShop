package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.dao.IItemRepository;
import com.wiktormalyska.backend.dao.hibernate.ItemDAO;
import com.wiktormalyska.backend.dto.ItemDto;
import com.wiktormalyska.backend.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService implements IItemService {
    @Autowired
    private IItemRepository itemRepository;

    public Collection<ItemDto> getItems(){
        Collection<ItemDto> itemDtos= new ArrayList<>();
        Collection<Item> items = itemRepository.getItems();
        for (Item item : items) {
            ItemDto itemDto = new ItemDto(item);
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    public ItemDto getItem(int id) {
        Item item = itemRepository.getItem(id);
        if (item != null)
            return new ItemDto(item);
        else
            return null;
    }

    public String addItem(Item item){
        Item  checkItem = itemRepository.getItems().stream().filter(i -> i.equals(item)).findFirst().orElse(null);
        if (checkItem != null)
            return "item already exists";
        itemRepository.addItem(item);
        return "item added successfully";
    }
    public String removeItem(Item item){
        Item checkItem = itemRepository.getItems().stream().filter(i -> i.equals(item)).findFirst().orElse(null);
        if (checkItem == null)
            return "item does not exist";
        itemRepository.removeItem(item.getId());
        return "item removed successfully";
    }

    @Override
    @Transactional
    public void saveOrUpdate(Item item) {
        this.itemRepository.saveOrUpdate(item);
    }

    @Override
    @Transactional
    public Item getById(int id) {
        return this.itemRepository.getItem(id);
    }

    @Override
    @Transactional
    public Collection<Item> getAll() {
        return this.itemRepository.getItems();
    }

    @Override
    @Transactional
    public void delete(int id) {
        this.itemRepository.removeItem(id);
    }
}
