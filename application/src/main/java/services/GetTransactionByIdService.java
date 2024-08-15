package services;

import requests.TransactionIdRequest;
import responses.Response;
import usecases.GetTransactionByIdUseCase;

public class GetTransactionByIdService implements TransactionService {
private final TransactionIdRequest transactionIdRequest;
private final GetTransactionByIdUseCase getTransactionByIdUseCase;

public GetTransactionByIdService(GetTransactionByIdUseCase getTransactionByIdUseCase, TransactionIdRequest transactionIdRequest) {
    this.getTransactionByIdUseCase = getTransactionByIdUseCase;
    this.transactionIdRequest = transactionIdRequest;
}
public Response processRequest() {
    int transactionId = transactionIdRequest.getTransactionId();
    return new Response("Successful operation", null, getTransactionByIdUseCase.execute(transactionId), null);
}

}
