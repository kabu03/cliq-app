package services;

import requests.TransactionIdRequest;
import responses.Response;
import usecases.RemoveTransactionUseCase;

public class RemoveTransactionService implements TransactionService {
    private final RemoveTransactionUseCase removeTransactionUseCase;
    private final TransactionIdRequest transactionIdRequest;

    public RemoveTransactionService(RemoveTransactionUseCase removeTransactionUseCase, TransactionIdRequest transactionIdRequest) {
        this.removeTransactionUseCase = removeTransactionUseCase;
        this.transactionIdRequest = transactionIdRequest;
    }

    public Response processRequest() {
        int transactionIdToRemove = transactionIdRequest.getTransactionId();
        if (removeTransactionUseCase.execute(transactionIdToRemove)) {
            return new Response("Successful deletion", null, null, null);
        } else {
            return new Response("Transaction not found, deletion unsuccessful", null, null, null);
        }
    }
}
