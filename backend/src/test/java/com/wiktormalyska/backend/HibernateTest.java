package com.wiktormalyska.backend;

import com.wiktormalyska.backend.dao.hibernate.ItemDAO;
import com.wiktormalyska.backend.model.Item;
import com.wiktormalyska.backend.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.stream.Stream;

public class HibernateTest {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private ItemDAO itemRepo = ItemDAO.getInstance(sessionFactory);

    static Stream<Item> itemProvider() {
        return Stream.of(
                new Item("Item 1", 10, "Item description 1"),
                new Item("Item 2", 9, "Item description 2"),
                new Item(" ", Integer.MAX_VALUE, null),
                new Item("Item 4", 99, "Item description 4")
        );
    }

    public HibernateTest() {
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    void addItemTest(Item item) {
        itemRepo.addItem(item);
        Collection<Item> items = itemRepo.getItems();
        assert items.contains(item);
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    void removeItemTest(Item item){
        addItemTest(item);
        itemRepo.removeItem(item.getId());
        Collection<Item> items = itemRepo.getItems();
        assert !items.contains(item);
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    void getItemTest(Item item){
        addItemTest(item);
        Item itemFromDb = itemRepo.getItem(item.getId());
        assert item.equals(itemFromDb);
        removeItemTest(item);
        assert itemRepo.getItem(item.getId()) == null;
    }


}