import java.util.Scanner;
import java.util.UUID;

public class Program {
    public static void main(String[] args)
    {
        TransactionsList list = new TransactionsLinkedList();
        list.addTransaction(new Transaction(UUID.randomUUID(), new User(10, "Jhon"), new User(500, "ha"), Transaction.TransferCategory.debits, 100));
        list.addTransaction(new Transaction(UUID.randomUUID(), new User(10, "Jhon"), new User(500, "ha"), Transaction.TransferCategory.credits, 100));
        Transaction[] tr =  list.toArray();
        try {
            list.removeTransaction(tr[0].getId());
			System.out.println(tr[1].getAmount());
            list.removeTransaction(tr[0].getId());
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
