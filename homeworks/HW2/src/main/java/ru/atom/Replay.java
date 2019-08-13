package main.java.ru.atom;

public class Replay {
    public static String replay () {
        String exit="y";
        int i = 0;
        while (i == 0) {
            System.out.println("do you want replay? y/n");
            exit = Input.in();
            if (exit.equals("y") || exit.equals("n"))
                i++;
        }
        return exit;
    }
}
