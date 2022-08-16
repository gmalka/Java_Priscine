import java.util.UUID;

public class User {
    private int                 id;
    private int                 balance;
    private String              name;
    private TransactionsList    list;

    public User()
    {
        list = new TransactionsLinkedList();
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = "Jhon";
    }

    public User(int balance)
    {
        list = new TransactionsLinkedList();
        this.balance = balance;
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = "Jhon";
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction)
    {
        list.addTransaction(transaction);
    }

    public Transaction[] getTransactionArray()
    {
        return list.toArray();
    }

    public Transaction getTransactionById(UUID id) throws TransactionNotFoundException {

        try {
            return list.getTransactionById(id);
        }
        catch (Exception e)
        {
            throw new TransactionNotFoundException();
        }
    }

    public void removeTransaction(UUID id)
    {
        try {
            list.removeTransaction(id);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeTransaction(Transaction transaction)
    {
        try {
            list.removeTransaction(transaction);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setBalance(int balance) {
        if (balance < 0)
        {
            System.out.println("Error: balance can't be negative number!");
            balance = 0;
            return ;
        }
        this.balance = balance;
    }
}
