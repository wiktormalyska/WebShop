package com.wiktormalyska.backend.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Table(name = "items")
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private String description;

    public Item() {}

    public Item(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }


}
