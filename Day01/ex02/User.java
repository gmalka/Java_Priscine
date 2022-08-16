public class User {
    private int                 id;
    private int                 balance;
    private String              name;

    public User()
    {
        this.id = UserIdsGenerator.getInstance().generateId();
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
            System.out.println("Error: balance can't be negative number!");
            balance = 0;
            return ;
        }
        this.balance = balance;
    }
}
