import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;


public class Bulls_and_Cows {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Bulls_and_Cows.class);
    public static void main(String[] args) {
        String file_name = "/home/svasilyev/TS/atom/homeworks/HW2/src/main/resources/dictionary.txt";
        int lines_counter = 0;

        //Counting the number of lines in dictionary.txt
        try(LineNumberReader lreader = new LineNumberReader(new FileReader(file_name))) {
            while(lreader.readLine() != null) {
                lines_counter++;
            }
        } catch (FileNotFoundException e) {
            log.warn("Warning1!");
            return;
        } catch (IOException e) {
            log.warn("Warning2!");
            return;
        }

        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        String str = null;
        String input = null;
        boolean check = false;
        boolean victory = false;
        boolean next_game = true;
        int str_num = 0;
        final int attemp_number = 10;
        int letter_number = 0;
        int bulls = 0;
        int cows = 0;

        System.out.println("Welcome to Bulls and Cows game!\n\n");

        while(next_game) {
            //Taking the word from dictionary.txt
            str_num = rand.nextInt(lines_counter);
            try (BufferedReader reader = new BufferedReader(new FileReader(file_name))) {
                for(int i = 0; i < (str_num - 1); i++) {
                    reader.readLine();
                }
                str = reader.readLine().toLowerCase();
                letter_number = str.length();
            } catch (IOException e) {
                log.warn("Warning!3");
                return;
            }

            System.out.println("For check: " + str);

            //Inviting message
            System.out.println("Try to guess the " + letter_number + "-letter word.\n");
            //Game
            for(int i = 1; i <= attemp_number; i++) {
                System.out.println("Attemp number " + i);
                //Input and check
                check = false;
                while (!check) {
                    System.out.print("Enter " + letter_number + "-letter word: ");
                    input = sc.nextLine().toLowerCase();
                    check = input.length() == letter_number;
                    for (int j = 0; j < letter_number && check; j++) {
                        check = input.charAt(j) >= 'a' && input.charAt(j) <= 'z';
                    }
                    if (!check) {
                        System.out.println("Wrong input! Try again.");
                    }
                }



                //Analysis of the word
                bulls = 0;
                cows = 0;
                int[] check_arr = new int[letter_number];
                for (int e : check_arr) {
                    e = 0;
                }
                for (int j = 0; j < letter_number; j++) {
                    if (input.charAt(j) == str.charAt(j)) {
                        check_arr[j] = 2;
                        bulls++;
                    }
                }
                for (int j = 0; j < letter_number; j++) {
                    for (int k = 0; k < letter_number; k++) {
                        if(check_arr[j] != 2 && check_arr[k] == 0 && input.charAt(k) == str.charAt(j)) {
                            check_arr[k] = 1;
                            cows++;
                            break;
                        }
                    }
                }

                //Attemp results
                System.out.println("Bulls: " + bulls);
                System.out.println("Cows: " + cows + "\n");

                if (bulls == letter_number) {
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
                    next_game = true;
                    check = false;
                    System.out.println("\n");
                } else if (input.equals("n")) {
                    next_game = false;
                    check = false;
                    System.out.println("Good luck!");
                }
            }
        }
    }
}
