package engine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alpi
 * @since 03.10.16
 */
public class GlobalContext {
  private final Map<Class<?>, Object> objects = new ConcurrentHashMap<Class<?>, Object>();
  public <T> void put(Class<T> key, T value){
    objects.put(key, value);
  }
  public <T> T get(Class<T> key){
    return (T)objects.get(key);
  }
  /*public static void processElements(List<?> elements){
     for(Object o : elements){
        System.out.println(o);
     }
  }*/
  public static <T> void processElements(List<T> elements){
  for(T o : elements){
  System.out.println(o.toString());
  }
  }

  public static void main(String[] args) {
    processElements(Arrays.asList("1","2"));
  }
}
