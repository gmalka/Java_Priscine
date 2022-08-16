import java.util.Scanner;

public class Program {
    public static void main(String[] args)
    {
        UsersArrayList list = new UsersArrayList(new User());
        list.addUser(new User());
        for (int i = 0; i < 100; i++) {
            list.addUser(new User());
        }
        try {
            User user1 = list.getUserById(1);
            User user2 = list.getUserByIndex(1);
            User user3 = list.getUserById(84);
            System.out.println(user1.getId());
            System.out.println(user2.getId());
            System.out.println(user3.getId());
            User user4 = list.getUserByIndex(0);
            System.out.println(user4.getId());
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(list.getUsersAmount());
    }
}

