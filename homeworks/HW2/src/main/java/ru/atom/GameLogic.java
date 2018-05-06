package ru.atom;

public class GameLogic {
    private static int Cows;
    private static int Bulls;

    public static boolean checkWords(String userString, String rndString) {

        Bulls = 0;
        Cows = 0;

        if (userString.equals(rndString))
            return true;

        char[] userChars = userString.toCharArray();
        char[] rndChars = rndString.toCharArray();

        if (userChars.length != rndChars.length)
            System.out.println("Wrong word size!");
        else {
            for (int x = 0, n = userChars.length; x < n; x++) {
                for (int y = 0, m = rndChars.length; y < m; y++) {
                    if (userChars[x] == rndChars[y] && x == y)
                        Bulls++;
                    else if (userChars[x] == rndChars[y])
                        Cows++;
                }
            }
            System.out.println("Bulls: " + Bulls);
            System.out.println("Cows: " + Cows);
        }
        return false;
    }
}
