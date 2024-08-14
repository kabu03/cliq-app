package services;

import requests.AliasRequest;
import responses.Response;
import models.Alias;
import models.Transaction;
import usecases.GetTransactionsByAliasUseCase;

import java.util.List;

public class GetTransactionsByAliasService {
    private final AliasRequest aliasRequest;
    private final GetTransactionsByAliasUseCase getTransactionsByAliasUseCase;

    public GetTransactionsByAliasService(GetTransactionsByAliasUseCase getTransactionsByAliasUseCase, AliasRequest aliasRequest) {
        this.getTransactionsByAliasUseCase = getTransactionsByAliasUseCase;
        this.aliasRequest = aliasRequest;
    }

    public Response processRequest() {
        Alias alias = initializeAliasFromRequest(aliasRequest);
        List<Transaction> transactionsList = getTransactionsByAliasUseCase.execute(alias);
        return new Response("Successful operation", null, null, transactionsList);
    }

    public Alias initializeAliasFromRequest(AliasRequest aliasRequest) {
        return new Alias(Alias.AllowedAliasTypes.valueOf(aliasRequest.getAliasType()), aliasRequest.getAliasValue());
    }
}
