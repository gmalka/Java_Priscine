import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        Transaction transaction = new Transaction(UUID.randomUUID(), new User(10, "Jhonny"), new User(500, "Nikola"), Transaction.TransferCategory.debits, 500);
        Transaction transaction2 = new Transaction(UUID.randomUUID(), new User(10, "Jhonny"), new User(500, "Nikola"), Transaction.TransferCategory.credits, 500);
        Transaction transaction3 = new Transaction(UUID.randomUUID(), new User(-10, "Jhonny"), new User(-500, "Nikola"), Transaction.TransferCategory.debits, 500);
    }
}
