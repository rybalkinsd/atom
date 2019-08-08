package main.java.hello;

import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String exit = "y";
        while (exit.equals("y")) {
            BullsAndCows proc = new BullsAndCows();
            proc.bullsAndCows();
            int i=0;
            while(i==0) {
                System.out.println("do you want replay? y/n");
                exit = Input.in();
                if(exit.equals("y") || exit.equals("n"))
                    i++;
            }
        }
    }
}
