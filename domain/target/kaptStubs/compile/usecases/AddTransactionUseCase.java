package usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B1\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0015\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0016\u00a2\u0006\u0002\u0010\u000fR\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lusecases/AddTransactionUseCase;", "Lusecases/TransactionUseCase;", "Lmodels/Transaction;", "", "repository", "Lmodels/TransactionRepository;", "transactionValidator", "Lvalidators/Validator;", "aliasValidator", "Lmodels/Alias;", "currencyValidator", "Lvalidators/CurrencyValidator;", "(Lmodels/TransactionRepository;Lvalidators/Validator;Lvalidators/Validator;Lvalidators/CurrencyValidator;)V", "execute", "transaction", "(Lmodels/Transaction;)Ljava/lang/Boolean;", "domain"})
public final class AddTransactionUseCase implements usecases.TransactionUseCase<models.Transaction, java.lang.Boolean> {
    @org.jetbrains.annotations.NotNull()
    private final models.TransactionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final validators.Validator<models.Transaction> transactionValidator = null;
    @org.jetbrains.annotations.NotNull()
    private final validators.Validator<models.Alias> aliasValidator = null;
    @org.jetbrains.annotations.NotNull()
    private final validators.CurrencyValidator currencyValidator = null;
    
    public AddTransactionUseCase(@org.jetbrains.annotations.NotNull()
    models.TransactionRepository repository, @org.jetbrains.annotations.NotNull()
    validators.Validator<models.Transaction> transactionValidator, @org.jetbrains.annotations.NotNull()
    validators.Validator<models.Alias> aliasValidator, @org.jetbrains.annotations.NotNull()
    validators.CurrencyValidator currencyValidator) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.Boolean execute(@org.jetbrains.annotations.NotNull()
    models.Transaction transaction) {
        return null;
    }
}