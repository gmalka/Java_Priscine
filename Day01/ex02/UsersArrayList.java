public class UsersArrayList implements UsersList {
    private User[]  users;
    private int     index;
    private int     max;

    public UsersArrayList()
    {
        users = new User[10];
        index = 0;
        max = 10;
    }

    public UsersArrayList(User user)
    {
        users = new User[10];
        users[0] = user;
        index = 1;
        max = 10;
    }

    @Override
    public void addUser(User user) {
        if (index == max)
        {
            User[] copy = users;
            max = max * 2;
            users = new User[max];
            System.arraycopy(copy, 0, users, 0, copy.length);
        }
        users[index++] = user;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        for(User u : users)
        {
            if (u.getId() == id)
                return u;
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException {
        if (index > this.index || index < 0)
            throw new UserNotFoundException();
        return users[index];
    }

    @Override
    public int getUsersAmount() {
        return index;
    }
}

