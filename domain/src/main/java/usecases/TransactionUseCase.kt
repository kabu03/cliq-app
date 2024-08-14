package usecases

interface TransactionUseCase <Input, Output> {
    fun execute(input: Input): Output
}