package services;

import requests.Request;
import responses.Response;
import models.Transaction;
import usecases.GetAllTransactionsUseCase;
import kotlin.Unit;

import java.util.List;

public class GetAllTransactionsService {
    private GetAllTransactionsUseCase getAllTransactionsUseCase;
    private Request request;

    public GetAllTransactionsService(GetAllTransactionsUseCase getAllTransactionUseCase, Request request) {
        this.getAllTransactionsUseCase = getAllTransactionUseCase;
        this.request = request;
    }

public Response processRequest() {
        List<Transaction> transactionsList = getAllTransactionsUseCase.execute(Unit.INSTANCE);
        return new Response("Successful operation", null, null, transactionsList);
    }
}
