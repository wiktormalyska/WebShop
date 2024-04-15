    package com.wiktormalyska.backend.model;

    import lombok.Getter;
    import lombok.Setter;

    import jakarta.persistence.*;

    @Getter
    @Table(name = "items")
    @Entity
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

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Item item) {
                return item.id == this.id && item.name.equals(this.name) && item.price == this.price && item.description.equals(this.description);
            }
            return super.equals(obj);
        }
    }
