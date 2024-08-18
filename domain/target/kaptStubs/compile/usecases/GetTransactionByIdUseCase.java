package usecases;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lusecases/GetTransactionByIdUseCase;", "Lusecases/TransactionUseCase;", "", "Lmodels/Transaction;", "repository", "Lmodels/TransactionRepository;", "(Lmodels/TransactionRepository;)V", "execute", "input", "domain"})
public final class GetTransactionByIdUseCase implements usecases.TransactionUseCase<java.lang.Integer, models.Transaction> {
    @org.jetbrains.annotations.NotNull()
    private final models.TransactionRepository repository = null;
    
    public GetTransactionByIdUseCase(@org.jetbrains.annotations.NotNull()
    models.TransactionRepository repository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public models.Transaction execute(int input) {
        return null;
    }
}