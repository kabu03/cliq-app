package services;

import requests.AliasRequest;
import responses.Response;
import models.Alias;
import models.Transaction;
import usecases.GetInwardTransactionsUseCase;

import java.util.List;

public class GetInwardTransactionsService {
    private final AliasRequest aliasRequest;
    private final GetInwardTransactionsUseCase getInwardTransactionsUseCase;

    public GetInwardTransactionsService(GetInwardTransactionsUseCase getInwardTransactionsUseCase, AliasRequest aliasRequest) {
        this.getInwardTransactionsUseCase = getInwardTransactionsUseCase;
        this.aliasRequest = aliasRequest;
    }

    public Response processRequest() {
        Alias alias = initializeAliasFromRequest(aliasRequest);
        List<Transaction> transactionsList = getInwardTransactionsUseCase.execute(alias);
        return new Response("Successful operation", null, null, transactionsList);
    }

    public Alias initializeAliasFromRequest(AliasRequest aliasRequest) {
        return new Alias(Alias.AllowedAliasTypes.valueOf(aliasRequest.getAliasType()), aliasRequest.getAliasValue());
    }
}
