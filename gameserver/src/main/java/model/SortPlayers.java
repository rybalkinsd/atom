package model;

import java.util.Comparator;

/**
 * Created by Егор on 14.10.2016.
 */
public class SortPlayers implements Comparator<Player> {

    @Override
    public int compare(Player playerOne,Player playerTwo) {
        int massOne = 0;
        for (Cell elem :
                playerOne.getCells()) {
            massOne += elem.getMass();
        }
        int massTwo = 0;
        for (Cell elem :
                playerTwo.getCells()) {
            massTwo += elem.getMass();
        }
        if (massOne - massTwo > 0) {
            return 1;
        } else if (massOne - massTwo < 0) {
            return -1;
        } else {
            return 0;
        }
    }

}
