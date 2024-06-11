package com.wiktormalyska.backend.dto;

import com.wiktormalyska.backend.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private String description;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
    }

    public Item toItem() {
        return new Item(this.id, this.name, this.price, this.description);
    }
}


