import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // private static final Logger log = LogManager.getLogger(Main.class);
    // private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Scanner reader;
        try {
            File file = new File("./dictionary.txt");
            reader = new Scanner(file);
            while (reader.hasNext()) {
                list.add(reader.next());
            }
            Game.game(list);
            // log.warn("msg");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            //log.warn("Null-pointer variable used as parameter. Dictionary may be missing.");
            return;
        }
    }
}
