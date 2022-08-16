import java.util.UUID;

public class TransactionsService {
    private UsersList list;

    public TransactionsService() {
        list = new UsersArrayList();
    }

    public void addUser(User user) {
        list.addUser(user);
    }

    public int getUserBalance(int id) {
        int balance = 0;
        try {
            balance = list.getUserById(id).getBalance();
        } catch (Exception e) {
            try {
                balance = list.getUserByIndex(id).getBalance();
            } catch (Exception q) {
                System.out.println("User not found");
            }
        }
        return balance;
    }

    public void doTransaction(int id_sender, int id_recipient, int count) {
        UUID id = UUID.randomUUID();
        try {
            System.out.println("Transaction!");
            User sender = list.getUserById(id_sender);
            User recipient = list.getUserById(id_recipient);
            sender.setBalance(sender.getBalance() - count);
            recipient.setBalance(recipient.getBalance() + count);
            sender.addTransaction(new Transaction(id, recipient, sender, Transaction.TransferCategory.credits, -count));
            recipient.addTransaction(new Transaction(id, recipient, sender, Transaction.TransferCategory.debits, count));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Transaction[] getUserTrnsactions(int id) {
        Transaction[] transactions = null;
        try {
            transactions = list.getUserById(id).getTransactionArray();
        } catch (Exception e) {
            try {
                transactions = list.getUserByIndex(id).getTransactionArray();
            } catch (Exception q) {
                System.out.println("User not found");
            }
        }
        return transactions;
    }

    public void removeTransactionById(UUID t_id, int u_id) {
        try {
            list.getUserById(u_id).removeTransaction(t_id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Transaction[] getIncorrectTransactions() {
        int count = list.getUsersAmount();
        TransactionsLinkedList l = new TransactionsLinkedList();
        for (User user : list.toArray()) {
            try {
                for (Transaction tr : user.getTransactionArray()) {
                    try {
                        User U = list.getUserById(tr.getRecipient().getId());
                        if (U.getTransactionById(tr.getId()) != tr && U.getTransactionById(tr.getId()).getCategory() == tr.getCategory()) {
                            l.addTransaction(tr);
                        }
                    } catch (Exception e) {
                        l.addTransaction(tr);
                    }
                }
            }
            catch (Exception ignored) {}
        }
        return l.toArray();
    }
}
