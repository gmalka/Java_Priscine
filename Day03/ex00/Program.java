public class Program {
    public static void main(String[] argv){
        if (argv.length != 1) {
            System.err.println("Error!");
            System.exit(-1);
        }
        StringBuilder sb = new StringBuilder();
        int separator = argv[0].indexOf("=");
        if (!argv[0].substring(0, separator + 1).equals("--count="))
        {
            System.err.println("Incorrect input parameters!");
            System.exit(-1);
        }
        for(char ch : argv[0].substring(separator + 1).toCharArray())
        {
            if (ch >= '0' && ch <= '9')
                sb.append(ch);
            else
            {
                System.err.println("Incorrect input parameters!");
                System.exit(-1);
            }
        }
        int number = Integer.parseInt(sb.toString());
        Egg egg = new Egg(number);
        Hen hen = new Hen(number);
        egg.start();
        hen.start();
        try {
            egg.join();
            hen.join();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        for (int i = 0; i < number; i++) {
            System.out.println("Human");
        }
    }
}
