package bullsandcows;


import java.util.Scanner;


public class CustomerInput {
    private static Scanner inpStream = new Scanner(System.in);

    public static String wordInpTry() {
        String inp;
        do {
            inp = inpStream.nextLine();
        } while (!WordPatternChecker.wordCheck(inp));
        return inp;
    }


    public static boolean assertInpTry() {
        String inp;
        do {
            inp = inpStream.next();
        } while (!WordPatternChecker.assertCheck(inp));
        return inp.equals("Y");
    }
}
