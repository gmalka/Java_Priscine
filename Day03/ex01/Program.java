public class Program {
    public static void main(String[] argv){
        if (argv.length != 1) {
            System.err.println("Error! Incorrect number of  arguments.");
            System.exit(-1);
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder text = new StringBuilder();
        for(char ch : argv[0].toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                if (!text.toString().equals("--count="))
                {
                    System.err.println("Error, incorrect argument!");
                    System.exit(-1);
                }
                sb.append(ch);
            }
            else
                text.append(ch);
        }
        if (!text.toString().equals("--count="))
        {
            System.out.println("Error moth%#@er!");
            System.exit(-1);
        }
        int number = Integer.parseInt(sb.toString());
        Object obj = new Object();
        Hen egg = new Hen(number, obj, "Egg");
        Hen hen = new Hen(number, obj, "Hen");
        egg.start();
        hen.start();
    }
}
