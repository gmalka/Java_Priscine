import java.util.UUID;

public class User {
    private int                 id;
    private int                 balance;
    private String              name;

    public User()
    {
        this.balance = 0;
        this.name = "Jhon";
    }

    public User(int balance, String name)
    {
        if (balance < 0) {
            System.err.println("Balance can't be negative!");
            this.balance = 0;
        }
        else {
            this.balance = balance;
        }
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
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


    public void setBalance(int balance) {
        if (balance < 0)
        {
            System.err.println("Error: balance can't be negative number!");
            this.balance = 0;
            return ;
        }
        this.balance = balance;
    }
}
