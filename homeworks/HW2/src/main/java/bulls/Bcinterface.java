package bulls;

import java.util.Scanner;

public class Bcinterface {
    private boolean isplaying = true;

    public boolean active() {
        return isplaying;
    }

    public void play() {
        System.out.println("Welcome to Bulls'n'Cows game!!!");

        Bcgame myGame = new Bcgame();

        Scanner input = new Scanner(System.in);
        do {
            boolean fin = false;

            if (!myGame.find_word()) {
                isplaying = false;
                return;
            }

            System.out.println("I offered a " + myGame.getLen() + "-letter word, your guess?");

            for (int i = 0; i < 10; i++) {
                int status = 0;
                try {
                    status = myGame.cmp(input.nextLine());
                } catch (Exception e) {
                    System.out.println("Unable to read your guess");
                    isplaying = false;
                    return;
                }

                if (status == 1) {
                    fin = true;
                    System.out.println("You won!");
                    break;
                }  else {
                    if (status == 2) {
                        i--;
                    }
                }
            }

            if (!fin) {
                System.out.println("You lost...");
            }

            System.out.println("Do you want to play again? Y/N");
        } while (input.nextLine().equals("Y"));

        System.out.println("Thank you for playing!");
        isplaying = false;
    }
}
