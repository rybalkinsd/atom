package ru.atom;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class BullAndCaw {
    public static void main(String[] args) {

        ArrayList<String> dictionary = getDictionary();

        int countOfTry = 10;
        try( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Welcome to Bulls and Cows game! ");
            for(;;){
                String pazzleWord = getRandomWord(dictionary).toLowerCase();
                boolean isUserWon = false;

                for(int trying = 1; trying <=countOfTry; trying++){
                    System.out.println("Try "+ trying +". I offered a " + pazzleWord.length() +"-letter word, your guess?");
                    ///System.out.println("-----------!!!!" + pazzleWord+ "!!!!-------");
                    String userWord = bufferedReader.readLine().trim().toLowerCase();

                    if(userWord.equals(pazzleWord)){
                       isUserWon =true;
                       break;


                    }else {
                        printBullAndCow(pazzleWord, userWord);
                    }
                }
                if(isUserWon)
                    printCongratulation();
                else
                    printYouLose(pazzleWord);
                if(!isUserWantContinue(bufferedReader))
                    break;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void printYouLose(String pazzleWord) {
        System.out.println("You Looser! Word: " + pazzleWord);
    }


    private static boolean isUserWantContinue(BufferedReader bufferedReader) throws IOException {
        boolean isWantContinue;
        for(;;){
            System.out.println("Wanna play again? Y/N ");
            String answer = bufferedReader.readLine();
            if(answer.toUpperCase().equals("Y")){
                isWantContinue = true;
                break;
            }
            if(answer.toUpperCase().equals("N")){
                isWantContinue = false;
                break;
            }

        }
        return isWantContinue;
    }

    private static void printBullAndCow(String pazzleWord, String userWord) {
        if(pazzleWord.length() == userWord.length()){
            System.out.println("Bulls: " + getBulls(pazzleWord, userWord));
            System.out.println("Cows: " + getCows(pazzleWord, userWord));
        }else {
            System.out.println("Wrong word");
        }

    }

    public static int getCows(String pazzleWord, String userWord) {
        int cows=0;
        char[] pazzleArray = pazzleWord.toCharArray();
        char[] userArray = userWord.toCharArray();
        ArrayList<Integer> usedIndex = new ArrayList<>();
        for(int i = 0; i < userArray.length; i++){
            for(int j=0; j< pazzleArray.length; j++){
                if(i==j && userArray[i] == pazzleArray[j]){
                    i++;
                    continue;
                }

                if(userArray[i] == pazzleArray[j] && !usedIndex.contains(i)){
                    cows++;
                    usedIndex.add(i);
                }
            }
        }


        return cows;
    }

    public static int getBulls(String pazzleWord, String userWord) {
        int bulls = 0;
        char[] pazzleArray = pazzleWord.toCharArray();
        char[] userArray = userWord.toCharArray();
        for(int i=0; i < pazzleArray.length; i++){
            if(pazzleArray[i] == userArray[i])
                bulls++;

        }
        return bulls;
    }


    private static void printCongratulation() {
        System.out.println("You won! ");
    }

    private static String getRandomWord(ArrayList<String> dictionary) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(dictionary.size());
        return dictionary.get(index);
    }

    private static ArrayList<String> getDictionary(){

        ArrayList<String> dictionary = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\atomEx\\atom\\homeworks\\HW2\\dictionary.txt")))){
            String word;
            while ((word = bufferedReader.readLine()) != null){
                dictionary.add(word);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
