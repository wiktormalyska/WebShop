package com.wiktormalyska.backend.hibernate;

import com.wiktormalyska.backend.dao.IItemRepository;
import com.wiktormalyska.backend.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.stream.Stream;

@Disabled
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

    @Disabled
    @ParameterizedTest
    @MethodSource("itemProvider")
    void addItemTest(Item item) {
        itemRepo.save(item);
        lastItem = item;
        Collection<Item> items = itemRepo.findAll();
        assert items.contains(item);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("itemProvider")
    void removeItemTest(Item item){
        itemRepo.save(item);
        itemRepo.deleteById(item.getId());
        Collection<Item> items = itemRepo.findAll();
        assert !items.contains(item);
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("itemProvider")
    void getItemTest(Item item){
        itemRepo.save(item);
        Item itemFromDb = itemRepo.findById(item.getId()).orElse(null);
        assert item.equals(itemFromDb);
        removeItemTest(item);
        assert itemRepo.findById(item.getId()).orElse(null) == null;
    }

    @Disabled
    @Test
    void getItemsTest(){
        Collection<Item> items = itemRepo.findAll();
        assert items.isEmpty();
    }

    @AfterEach
    void cleanUp(){
        if(lastItem != null){
            itemRepo.deleteById(lastItem.getId());
            lastItem = null;
        }
    }

}
