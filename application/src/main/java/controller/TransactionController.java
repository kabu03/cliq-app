package controller;

import models.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import requests.AliasRequest;
import requests.TransactionIdRequest;
import requests.TransactionRequest;
import responses.Response;
import services.*;
import usecases.*;
import validators.AliasValidator;
import validators.CurrencyValidator;
import validators.TransactionValidator;

@RestController
@RequestMapping("/transactions")

public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;
    private final AliasValidator aliasValidator;
    private final CurrencyValidator currencyValidator;

    @Autowired
    public TransactionController(@Qualifier("memoryTransactionRepository") TransactionRepository transactionRepository,
                                 TransactionValidator transactionValidator, AliasValidator aliasValidator, CurrencyValidator currencyValidator) {
        this.transactionRepository = transactionRepository;
        this.transactionValidator = transactionValidator;
        this.aliasValidator = aliasValidator;
        this.currencyValidator = currencyValidator;
    }

    @GetMapping
    public ResponseEntity<Response> getAllTransactions() {
        GetAllTransactionsService getAllTransactionsService =
                new GetAllTransactionsService(new GetAllTransactionsUseCase
                        (transactionRepository, transactionValidator, aliasValidator), null);

        Response response = getAllTransactionsService.processRequest();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getTransactionById(@PathVariable int id) {
        GetTransactionByIdService getTransactionByIdService =
                new GetTransactionByIdService(
                        new GetTransactionByIdUseCase(transactionRepository),
                        new TransactionIdRequest(id, null));
        Response response = getTransactionByIdService.processRequest();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addTransaction(@RequestBody TransactionRequest request) {
        AddTransactionService addTransactionService = new AddTransactionService(
                new AddTransactionUseCase(transactionRepository, transactionValidator, aliasValidator, currencyValidator),
                request
        );
        Response response = addTransactionService.processRequest();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> removeTransactionById(@PathVariable int id) {
        RemoveTransactionService removeTransactionService = new RemoveTransactionService(
                new RemoveTransactionUseCase(transactionRepository, transactionValidator, aliasValidator),
                new TransactionIdRequest(id, null)
        );
        Response response = removeTransactionService.processRequest();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/incoming/{aliasType}/{aliasValue}")
    public ResponseEntity<Response> getInwardTransactions(@PathVariable String aliasType, @PathVariable String aliasValue) {
        try {
            GetInwardTransactionsService inwardTransactionsService =
                    new GetInwardTransactionsService(
                            new GetInwardTransactionsUseCase(
                                    transactionRepository, aliasValidator),
                            new AliasRequest(aliasType.toUpperCase(), aliasValue, null));
            Response response = inwardTransactionsService.processRequest();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("Alias not found: " + aliasValue));
        }
    }

    @GetMapping("/outgoing/{aliasType}/{aliasValue}")
    public ResponseEntity<Response> getOutwardTransactions(@PathVariable String aliasType, @PathVariable String aliasValue) {
        try {
            GetOutwardTransactionsService outwardTransactionsService =
                    new GetOutwardTransactionsService(
                            new GetOutwardTransactionsUseCase(
                                    transactionRepository, aliasValidator),
                            new AliasRequest(aliasType.toUpperCase(), aliasValue, null));
            Response response = outwardTransactionsService.processRequest();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("Alias not found: " + aliasValue));
        }
    }

    @GetMapping("/alias/{aliasType}/{aliasValue}")
    public ResponseEntity<Response> getTransactionsByAlias(@PathVariable String aliasType, @PathVariable String aliasValue) {
        try {
            GetTransactionsByAliasService transactionsByAliasService =
                    new GetTransactionsByAliasService(
                            new GetTransactionsByAliasUseCase(
                                    transactionRepository, aliasValidator),
                            new AliasRequest(aliasType.toUpperCase(), aliasValue, null));
            Response response = transactionsByAliasService.processRequest();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("Alias not found: " + aliasValue));
        }
    }

}
