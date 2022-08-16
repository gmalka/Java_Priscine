import java.util.Scanner;
import java.util.UUID;

public class Program {
    public static void main(String[] args)
    {
        TransactionsService service = new TransactionsService();
        User user = new User(500);
        user.setName("Anton");
        service.addUser(user);
        service.addUser((new User(500)));
        service.addUser(new User(500));
        service.addUser(new User(500));
        service.addUser(new User(500));
        service.doTransaction(1, 2, 200);
        UUID i = UUID.randomUUID();
        user.addTransaction(new Transaction(i, new User(5000), user, Transaction.TransferCategory.credits, 0));
        Transaction[] transactions = service.getUserTrnsactions(2);
        for (Transaction tr : user.getTransactionArray())
            System.out.println("USer 1 = " + tr.getId());
        for (Transaction tr : transactions)
            System.out.println("USer 2 = " + tr.getId());
        user.addTransaction(new Transaction(i, new User(5000), user, Transaction.TransferCategory.debits, 0));
        Transaction[] tr = service.getIncorrectTransactions();
        try {
            for(Transaction t : tr)
                System.out.println(t.getId());
        }
        catch (Exception e) {
            System.out.println(tr);
        }
    }
}