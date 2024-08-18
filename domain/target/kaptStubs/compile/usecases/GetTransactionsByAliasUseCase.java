package usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\t\u001a\u00020\u0002H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lusecases/GetTransactionsByAliasUseCase;", "Lusecases/TransactionUseCase;", "Lmodels/Alias;", "", "Lmodels/Transaction;", "repository", "Lmodels/TransactionRepository;", "(Lmodels/TransactionRepository;)V", "execute", "alias", "domain"})
public final class GetTransactionsByAliasUseCase implements usecases.TransactionUseCase<models.Alias, java.util.List<? extends models.Transaction>> {
    @org.jetbrains.annotations.NotNull()
    private final models.TransactionRepository repository = null;
    
    public GetTransactionsByAliasUseCase(@org.jetbrains.annotations.NotNull()
    models.TransactionRepository repository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<models.Transaction> execute(@org.jetbrains.annotations.NotNull()
    models.Alias alias) {
        return null;
    }
}