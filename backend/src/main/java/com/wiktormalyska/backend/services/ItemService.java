package com.wiktormalyska.backend.services;

import com.wiktormalyska.backend.dao.IItemRepository;
import com.wiktormalyska.backend.dto.ItemDto;
import com.wiktormalyska.backend.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ItemService {
    @Autowired
    private IItemRepository itemRepository;

    @Transactional
    public Collection<ItemDto> getItems(){
        Collection<ItemDto> itemDtos= new ArrayList<>();
        Collection<Item> items = itemRepository.findAll();
        for (Item item : items) {
            ItemDto itemDto = new ItemDto(item);
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }
    @Transactional
    public ItemDto getItem(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null)
            return new ItemDto(item);
        else
            return null;
    }

    @Transactional
    public String addItem(Item item){
        Item  checkItem = itemRepository.findById(item.getId()).stream().filter(i -> i.equals(item)).findFirst().orElse(null);
        if (checkItem != null)
            return "item already exists";
        itemRepository.save(item);
        return "item added successfully";
    }

    @Transactional
    public String removeItem(Item item){
        Item checkItem = itemRepository.findById(item.getId()).stream().filter(i -> i.equals(item)).findFirst().orElse(null);
        if (checkItem == null)
            return "item does not exist";
        itemRepository.deleteById(item.getId());
        return "item removed successfully";
    }
}
