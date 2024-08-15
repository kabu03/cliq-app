package services;

import requests.AliasRequest;
import responses.Response;
import models.Alias;
import models.Transaction;
import usecases.GetOutwardTransactionsUseCase;

import java.util.List;

public class GetOutwardTransactionsService implements TransactionService {
    private final AliasRequest aliasRequest;
    private final GetOutwardTransactionsUseCase getOutwardTransactionsUseCase;

    public GetOutwardTransactionsService(GetOutwardTransactionsUseCase getOutwardTransactionsUseCase, AliasRequest aliasRequest) {
        this.getOutwardTransactionsUseCase = getOutwardTransactionsUseCase;
        this.aliasRequest = aliasRequest;
    }

    public Response processRequest() {
        Alias alias = initializeAliasFromRequest(aliasRequest);
        List<Transaction> transactionsList = getOutwardTransactionsUseCase.execute(alias);
        return new Response("Successful operation", null, null, transactionsList);
    }

    public Alias initializeAliasFromRequest(AliasRequest aliasRequest) {
        return new Alias(Alias.AllowedAliasTypes.valueOf(aliasRequest.getAliasType()), aliasRequest.getAliasValue());
    }
}
