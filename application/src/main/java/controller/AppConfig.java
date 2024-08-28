package controller;

import models.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import repositories.MemoryTransactionRepository;
import validators.AliasValidator;
import validators.CurrencyValidator;
import validators.TransactionValidator;

@Configuration
public class AppConfig {

    @Bean
    public TransactionRepository memoryTransactionRepository() {
        return new MemoryTransactionRepository();
    }


    @Bean
    public TransactionValidator transactionValidator() {
        return new TransactionValidator();
    }

    @Bean
    public AliasValidator aliasValidator() {
        return new AliasValidator();
    }

    @Bean
    public CurrencyValidator currencyValidator() {
        return new CurrencyValidator();
    }

}
