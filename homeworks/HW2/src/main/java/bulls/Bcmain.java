package bulls;

public class Bcmain {
    public static void main(String[] args) {
        Bcinterface curgame = new Bcinterface();
        do {
            curgame.play();
        } while (curgame.active());
    }
}