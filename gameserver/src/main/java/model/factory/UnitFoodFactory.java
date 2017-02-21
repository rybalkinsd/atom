package model.factory;

import model.units.UnitFood;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rail on 11.10.2016.
 */
public class UnitFoodFactory {
    static final Random random = new Random();
    public static UnitFood getFood(){
        return new UnitFood(random.nextInt(), random.nextInt());
    }

    public static ArrayList<UnitFood> getFood(int count) {
        ArrayList<UnitFood> foods = new ArrayList<>();
        for (int i = 0; i < count; i++)
            foods.add(getFood());

        return foods;
    }
}
