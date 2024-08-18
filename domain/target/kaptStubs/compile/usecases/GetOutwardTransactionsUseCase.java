package usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001B)\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\b\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lusecases/GetOutwardTransactionsUseCase;", "Lusecases/TransactionUseCase;", "Lmodels/Alias;", "", "Lmodels/Transaction;", "repository", "Lmodels/TransactionRepository;", "transactionValidator", "Lvalidators/Validator;", "aliasValidator", "(Lmodels/TransactionRepository;Lvalidators/Validator;Lvalidators/Validator;)V", "execute", "alias", "domain"})
public final class GetOutwardTransactionsUseCase implements usecases.TransactionUseCase<models.Alias, java.util.List<? extends models.Transaction>> {
    @org.jetbrains.annotations.NotNull()
    private final models.TransactionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final validators.Validator<models.Transaction> transactionValidator = null;
    @org.jetbrains.annotations.NotNull()
    private final validators.Validator<models.Alias> aliasValidator = null;
    
    public GetOutwardTransactionsUseCase(@org.jetbrains.annotations.NotNull()
    models.TransactionRepository repository, @org.jetbrains.annotations.NotNull()
    validators.Validator<models.Transaction> transactionValidator, @org.jetbrains.annotations.NotNull()
    validators.Validator<models.Alias> aliasValidator) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<models.Transaction> execute(@org.jetbrains.annotations.Nullable()
    models.Alias alias) {
        return null;
    }
}