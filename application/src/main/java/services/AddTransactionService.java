package services;

import requests.TransactionRequest;
import responses.Response;
import models.Alias;
import models.Transaction;
import usecases.AddTransactionUseCase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddTransactionService implements TransactionService {
    private final AddTransactionUseCase addTransactionUseCase;
    private TransactionRequest request;

    public AddTransactionService(AddTransactionUseCase addTransactionUseCase, TransactionRequest request) {
        this.addTransactionUseCase = addTransactionUseCase;
        this.request = request;
    }

    public Response processRequest() {
        Alias debtor = new Alias(Alias.AllowedAliasTypes.valueOf(request.getDebtorAliasType().toUpperCase()), request.getDebtorAliasValue());
        Alias creditor = new Alias(Alias.AllowedAliasTypes.valueOf(request.getCreditorAliasType().toUpperCase()), request.getCreditorAliasValue());
        String currency = request.getCurrency();

        // Parsing with the simple format
        LocalDateTime localDateTime = LocalDateTime.parse(request.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Date timestamp = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Transaction transactionToAdd = new Transaction(debtor, creditor, request.getAmount(), currency, request.getPurpose(), timestamp);
        boolean success = addTransactionUseCase.execute(transactionToAdd);
        if (success) {
            return new Response("Successful addition", transactionToAdd.getId(), null, null);
        } else {
            return new Response("An error has occurred\nThe transaction was not added", null, null, null);
        }
    }
}



