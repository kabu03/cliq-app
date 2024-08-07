class Main {
    public static void main(String[] args) {
        MemoryTransactionRepository record = new MemoryTransactionRepository(); // MemoryTransactionRepository implements TransactionRepository.
        Validator<Transaction> transactionValidator = new TransactionValidator(); // TransactionValidator implements Validator<Transaction>.
        Validator<Alias> aliasValidator = new AliasValidator(); // AliasValidator implements Validator<Alias>.
        TransactionService service = new TransactionService(record, transactionValidator, aliasValidator);
        // TransactionService uses TransactionRepository, Validator<Transaction>, and Validator<Alias>.
        // TransactionService follows the Dependency Inversion Principle.

        Alias karam = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Karam");
        Alias jebreen = new Alias(Alias.AllowedAliasTypes.ALPHANUMERIC, "Jebreen");
        Transaction t = new Transaction(karam, jebreen, 100.0, Transaction.Currency.USD, "Payment");
        Transaction t1 = new Transaction(karam, jebreen, 200.0, Transaction.Currency.USD, "Payment");

        System.out.println("Adding transaction t");
        service.addTransaction(t); // The service will validate everything using the validator instance and then add the transaction to the repository.
        record.printAllTransactions();

        System.out.println("Adding transaction t1");
        service.addTransaction(t1);
        record.printAllTransactions();

        System.out.println("Getting all transactions");
        System.out.println(service.getAllTransactions());
    }
}