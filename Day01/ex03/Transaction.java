import java.util.UUID;

public class Transaction {
    enum TransferCategory {
        debits,
        credits
    }
    private final UUID           id;
    private User                 Recipient;
    private User                 Sender;
    private TransferCategory     category;
    private int                  amount;

    public Transaction()
    {
        id = UUID.randomUUID();;
        Recipient = null;
        Sender = null;
        category = TransferCategory.debits;
        amount = 0;
    }

    public Transaction(int amount)
    {
        id = UUID.randomUUID();;
        Recipient = null;
        Sender = null;
        category = TransferCategory.debits;
        this.amount = amount;
    }
    public Transaction(UUID id, User recipient, User sender, TransferCategory category, int amount) {
        this.id = id;
        Recipient = recipient;
        Sender = sender;
        this.category = category;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return Recipient;
    }

    public void setRecipient(User recipient) {
        Recipient = recipient;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    public TransferCategory getCategory() {
        return category;
    }

    public void setCategory(TransferCategory category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (category == TransferCategory.debits && amount < 0)
        {
            System.out.println("Error: debit transaction can't be negative number!");
            this.amount = 0;
            return ;
        }
        if (category == TransferCategory.credits && amount > 0)
        {
            System.out.println("Error: credit transaction can't be negative number!");
            this.amount = 0;
            return ;
        }
        this.amount = amount;
    }
}
