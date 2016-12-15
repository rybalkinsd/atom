package utils;

import model.Field;
import ticker.Tickable;

public interface FoodGenerator extends Tickable {
    void setField(Field field);
}