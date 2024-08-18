package controller;

import models.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repositories.DatabaseConnection;
import repositories.HibernateTransactionRepository;
import repositories.JdbcTransactionRepository;
import repositories.MemoryTransactionRepository;

@Configuration
public class AppConfig {

    @Bean
    public TransactionRepository memoryTransactionRepository() {
        return new MemoryTransactionRepository();
    }

//    @Bean
//    public TransactionRepository jdbcTransactionRepository() {
//        return new JdbcTransactionRepository("transactions", new DatabaseConnection());
//    }
//
//    @Bean
//    public TransactionRepository hibernateTransactionRepository() {
//        return new HibernateTransactionRepository();
//    }
}
