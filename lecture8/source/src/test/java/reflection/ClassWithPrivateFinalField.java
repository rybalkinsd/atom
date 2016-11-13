package reflection;

import com.sun.istack.internal.NotNull;

/**
 * @author Alpi
 * @since 13.11.16
 */
public class ClassWithPrivateFinalField {
  @NotNull
  private final String privateFinalField// = "This is my final state";
      ;

  /**
   * Reflection collides with optimizations
   * According to JLS 17.5.3. Subsequent Modification of final Fields
   * we can not observe changes of final fields via reflection
   * if they replaced at compile time with the value of the constant expression
   * https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html
   *
   * So we use initializing Constructor as a workaround
   *
   * That is, reflection is a dirty technique in most cases
   */
  public ClassWithPrivateFinalField() {
    privateFinalField = "This is my final state";
  }

  public String getPrivateFinalField() {
    return privateFinalField;
  }
}
