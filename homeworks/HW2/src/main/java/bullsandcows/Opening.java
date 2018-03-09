package bullsandcows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Opening {


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        String path;
        Scanner s;

        try{
            path = Opening.class.getClassLoader().getResource("dictionary.txt").getPath();
            s = new Scanner(new File(path));
            while (s.hasNext()){
                list.add(s.next());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("nof found!");
            /*log.warn("File not found!Check your resources")*/
            return;
        } catch (IOException ex) {
            /*log.warn("IOException!Something's gone wrong")*/
            return;
        }

        MainGameCycle.Start(list);
    }


}
