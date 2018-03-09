package bullsandcows;


import java.util.*;
import java.util.Random;


public class MainGameCycle {


    public static void Start(ArrayList<String> list){
        String anotherTry;
        String word;
        Random generator = new Random();
        boolean nextTry;
        int listSize = list.size();
        int wordLength;
        int wordNum;

        System.out.println("Welcome to Bulls and Cows game!");
        do{
            wordNum = generator.nextInt(listSize);
            word = list.get(wordNum);
            System.out.println(word);
            wordLength = word.length();
            WordPatternChecker.setPattern(wordLength);
            System.out.println("I offered a "+wordLength+"-letter word, your guess?");
            for(int i = 0;i < 10;i++){
                anotherTry = CustomerInput.wordInpTry();
                if (WordPatternChecker.ansComparer(word,anotherTry)){
                    System.out.println("You won!My congratulations!");
                    break;
                }
            }
            System.out.println("You have lost! Wanna play again? Y/N");
            nextTry = CustomerInput.assertInpTry();
        } while(nextTry);

        return;
    }


}
