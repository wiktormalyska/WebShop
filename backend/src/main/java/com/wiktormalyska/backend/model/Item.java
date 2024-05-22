    package com.wiktormalyska.backend.model;

    import lombok.*;

    import jakarta.persistence.*;

    @Getter
    @Table(name = "items")
    @Entity
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public class Item {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Setter
        private String name;
        @Setter
        private int price;
        @Setter
        private String description;
        @Setter
        private int quantity;


        public Item(String name, int price, String description, int quantity) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.quantity = quantity;
        }
        public Item(int id){
            this.id = id;
        }
    }
