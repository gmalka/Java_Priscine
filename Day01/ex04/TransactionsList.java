import java.util.UUID;

public interface TransactionsList {
    public void addTransaction(Transaction transaction);
    public void removeTransaction(Transaction transaction) throws TransactionNotFoundException;
    public void removeTransaction(UUID id) throws TransactionNotFoundException;
    public Transaction getTransactionById(UUID id) throws TransactionNotFoundException;
    public Transaction[] toArray();
}