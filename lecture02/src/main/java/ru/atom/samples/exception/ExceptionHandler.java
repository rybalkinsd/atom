package ru.atom.samples.exception;

import java.io.*;

public class ExceptionHandler {

    /**
     * Простая обработка исключения
     */
    public static void simpleHandle() {
        try {
            // какие-то вызовы
            throw new SubException("problem in ExceptionHandler::simpleHandle");
        } catch (SubException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Finally section");
        }
    }

    public static void exceptionThrower() throws SubException {
        try {
            Integer integer = null;
            // неудачная попытка обратиться по нулевому указателю
            integer.toString();
        } catch (NullPointerException npe) {
            throw new SubException("Handled NPE");
        }
    }

    public static String multipleCatch(Throwable throwable) {
        try {
            throw throwable;
        } catch (BaseException baseEx) {
            return  baseEx.getMessage();
        } catch (Exception ex) {
            return ex.getMessage();
        } catch (Throwable th) {
            return th.getMessage();
        }
    }

    public static String readOneLineFromFile(String filename) {
        File file = new File(filename);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException in readOneLineFromFile, filename = " + filename);
            return null;
        } catch (IOException e) {
            System.out.println("IOException in readOneLineFromFile, filename = " + filename);
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * @See {@link java.lang.AutoCloseable}
     */
    public static String readOneLineFromFile_TheNewWay(String filename) {
        File file = new File(filename);
        try (BufferedReader bufferedReader
                     = new BufferedReader(new FileReader(file))) {
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException in readOneLineFromFile_TheNewWay, filename = " + filename);
            return null;
        } catch (IOException e) {
            System.out.println("IOException in readOneLineFromFile_TheNewWay, filename = " + filename);
            return null;
        }
    }
}
