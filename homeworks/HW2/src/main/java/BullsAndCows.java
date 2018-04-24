import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.File;
import java.util.Random;
import java.util.Scanner;


public class BullsAndCows {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BullsAndCows.class);

    public static void main(String[] args) {
        String fileName = "dictionary.txt";
        int linesCounter = 0;

        //Counting the number of lines in dictionary.txt
        try (LineNumberReader lreader
                     = new LineNumberReader(
                             new FileReader(new File(BullsAndCows.class.getResource(fileName).getPath()))
        )
        ) {
            while (lreader.readLine() != null) {
                linesCounter++;
            }
        } catch (FileNotFoundException e) {
            log.warn(e.toString());
            return;
        } catch (IOException e) {
            log.warn(e.toString());
            return;
        }

        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        String str = null;
        String input = null;
        boolean check = false;
        boolean victory = false;
        boolean nextGame = true;
        int strNum = 0;
        final int attempNumber = 10;
        int letterNumber = 0;
        int bulls = 0;
        int cows = 0;

        System.out.println("Welcome to Bulls and Cows game!\n\n");

        while (nextGame) {
            //Taking the word from dictionary.txt
            strNum = rand.nextInt(linesCounter);
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(BullsAndCows.class.getResource(fileName).getPath()))
            ) {
                for (int i = 0; i < (strNum - 1); i++) {
                    reader.readLine();
                }
                str = reader.readLine().toLowerCase();
                letterNumber = str.length();
            } catch (IOException e) {
                log.warn(e.toString());
                return;
            }

            System.out.println("For check: " + str);

            //Inviting message
            System.out.println("Try to guess the " + letterNumber + "-letter word.\n");
            //Game
            for (int i = 1; i <= attempNumber; i++) {
                System.out.println("Attemp number " + i);
                //Input and check
                check = false;
                while (!check) {
                    System.out.print("Enter " + letterNumber + "-letter word: ");
                    input = sc.nextLine().toLowerCase();
                    check = input.length() == letterNumber;
                    for (int j = 0; j < letterNumber && check; j++) {
                        check = input.charAt(j) >= 'a' && input.charAt(j) <= 'z';
                    }
                    if (!check) {
                        System.out.println("Wrong input! Try again.");
                    }
                }



                //Analysis of the word
                bulls = 0;
                cows = 0;
                int[] checkArr = new int[letterNumber];
                for (int e : checkArr) {
                    e = 0;
                }
                for (int j = 0; j < letterNumber; j++) {
                    if (input.charAt(j) == str.charAt(j)) {
                        checkArr[j] = 2;
                        bulls++;
                    }
                }
                for (int j = 0; j < letterNumber; j++) {
                    for (int k = 0; k < letterNumber; k++) {
                        if (checkArr[j] != 2 && checkArr[k] == 0 && input.charAt(k) == str.charAt(j)) {
                            checkArr[k] = 1;
                            cows++;
                            break;
                        }
                    }
                }

                //Attemp results
                System.out.println("Bulls: " + bulls);
                System.out.println("Cows: " + cows + "\n");

                if (bulls == letterNumber) {
                    victory = true;
                    break;
                }
            }

            //Game results
            if (victory) {
                System.out.println("You won!!!\n\n");
            } else {
                System.out.println("Unfortunately, you lost.\n\n");
            }

            //Restart
            check = true;
            while (check) {
                System.out.print("Wanna play again?[Y/N]:");
                input = sc.nextLine().toLowerCase();
                if (input.equals("y")) {
                    nextGame = true;
                    check = false;
                    System.out.println("\n");
                } else if (input.equals("n")) {
                    nextGame = false;
                    check = false;
                    System.out.println("Good luck!");
                }
            }
        }
    }
}

