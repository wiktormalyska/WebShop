package com.wiktormalyska.backend.hibernate;

import com.wiktormalyska.backend.dao.IItemRepository;
import com.wiktormalyska.backend.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.stream.Stream;

@SpringBootTest
public class ItemsTests {
    private Item lastItem;
    @Autowired
    private IItemRepository itemRepo;

    static Stream<Item> itemProvider() {
        return Stream.of(
                new Item("Item 1", 10, "Item description 1"),
                new Item("Item 2", 9, "Item description 2"),
                new Item(" ", Integer.MAX_VALUE, "sigma"),
                new Item("Item 4", 99, "Item description 4")
        );
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    void addItemTest(Item item) {
        itemRepo.addItem(item);
        lastItem = item;
        Collection<Item> items = itemRepo.getItems();
        assert items.contains(item);
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    void removeItemTest(Item item){
        itemRepo.addItem(item);
        itemRepo.removeItem(item.getId());
        Collection<Item> items = itemRepo.getItems();
        assert !items.contains(item);
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    void getItemTest(Item item){
        itemRepo.addItem(item);
        Item itemFromDb = itemRepo.getItem(item.getId());
        assert item.equals(itemFromDb);
        removeItemTest(item);
        assert itemRepo.getItem(item.getId()) == null;
    }

    @Test
    void getItemsTest(){
        Collection<Item> items = itemRepo.getItems();
        assert items != null;
    }

    @AfterEach
    void cleanUp(){
        if(lastItem != null){
            itemRepo.removeItem(lastItem.getId());
            lastItem = null;
        }
    }

}
