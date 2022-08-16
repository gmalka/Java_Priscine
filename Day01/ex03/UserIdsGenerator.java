public class UserIdsGenerator {
    private int id;
    private static final UserIdsGenerator generator = new UserIdsGenerator();

    private UserIdsGenerator()
    {
        id = 0;
    }

    public static UserIdsGenerator getInstance()
    {
        return generator;
    }

    public int generateId()
    {
        return ++generator.id;
    }
}
