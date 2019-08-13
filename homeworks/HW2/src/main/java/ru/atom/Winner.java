package main.java.ru.atom;

public class Winner {
    public int winner(int size,int bull,int chance) {
        if(bull==size) {
            System.out.println("Congratulations!!! You win");
            return 0;
        }
        return chance;
    }
}