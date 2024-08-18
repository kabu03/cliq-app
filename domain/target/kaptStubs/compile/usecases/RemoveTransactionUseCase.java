package usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B)\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007\u00a2\u0006\u0002\u0010\u000bJ\u0015\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0002H\u0016\u00a2\u0006\u0002\u0010\u000eR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lusecases/RemoveTransactionUseCase;", "Lusecases/TransactionUseCase;", "", "", "repository", "Lmodels/TransactionRepository;", "transactionValidator", "Lvalidators/Validator;", "Lmodels/Transaction;", "aliasValidator", "Lmodels/Alias;", "(Lmodels/TransactionRepository;Lvalidators/Validator;Lvalidators/Validator;)V", "execute", "transactionID", "(I)Ljava/lang/Boolean;", "domain"})
public final class RemoveTransactionUseCase implements usecases.TransactionUseCase<java.lang.Integer, java.lang.Boolean> {
    @org.jetbrains.annotations.NotNull()
    private final models.TransactionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final validators.Validator<models.Transaction> transactionValidator = null;
    @org.jetbrains.annotations.NotNull()
    private final validators.Validator<models.Alias> aliasValidator = null;
    
    public RemoveTransactionUseCase(@org.jetbrains.annotations.NotNull()
    models.TransactionRepository repository, @org.jetbrains.annotations.NotNull()
    validators.Validator<models.Transaction> transactionValidator, @org.jetbrains.annotations.NotNull()
    validators.Validator<models.Alias> aliasValidator) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.Boolean execute(int transactionID) {
        return null;
    }
}