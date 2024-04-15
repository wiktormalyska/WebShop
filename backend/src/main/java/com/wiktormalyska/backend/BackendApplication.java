package com.wiktormalyska.backend;

import com.wiktormalyska.backend.dao.hibernate.ItemDAO;
import com.wiktormalyska.backend.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        ItemDAO itemDAO = ItemDAO.getInstance(sessionFactory);

        itemDAO.getItems().forEach(System.out::println);

    }

}
