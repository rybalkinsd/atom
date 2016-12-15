package utils;

import model.Field;
import model.GameConstants;
import model.Virus;
import java.util.ArrayList;
import java.util.Random;

public class RandomVirusGenerator implements VirusGenerator {
  private Field field;
  private final int numberOfViruses;

  public RandomVirusGenerator(int numberOfViruses) {
    this.numberOfViruses = numberOfViruses;
  }

  @Override
  public void setField(Field field){
    this.field = field;
  }

  @Override
  public void generate() {
    Random random = new Random();
    int virusRadius = (int) Math.sqrt(GameConstants.VIRUS_MASS / Math.PI);
    ArrayList<Virus> viruses = field.getViruses();
    boolean acceptable = false;
    for (int i = 0; i < numberOfViruses; i++) {
      int newX, newY;
      while(!acceptable) {
        acceptable = true;
        newX = random.nextInt(field.getWidth() - 2 * virusRadius);
        newY = random.nextInt(field.getHeight() - 2 * virusRadius);
        int j = 0;
        for (; j < viruses.size(); j++) {
          if (!(Math.sqrt(((Math.abs(viruses.get(j).getX() - newX)) ^ 2) +
                  ((Math.abs(viruses.get(j).getY() - newY)) ^ 2)) > 4 * virusRadius)) {
            acceptable = false;
            break;
          }
        }
        if(j == viruses.size() && acceptable){
          viruses.add(new Virus(newX, newY));
          acceptable = false;
          break;
        }
      }
    }
  }
}