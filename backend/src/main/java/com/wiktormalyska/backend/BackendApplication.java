package com.wiktormalyska.backend;

import com.wiktormalyska.backend.dao.hibernate.ItemDAO;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        System.out.println("Items in the database:");
        SpringApplication.run(BackendApplication.class, args);
        SessionFactory sessionFactory = com.wiktormalyska.backend.utils.HibernateUtil.getSessionFactory();
        ItemDAO itemDAO = ItemDAO.getInstance(sessionFactory);

        itemDAO.getItems().forEach(System.out::println);

    }

}
