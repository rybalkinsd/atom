package reflection;

/**
 * @author Alpi
 * @since 13.11.16
 */
@CompileTimeAnnotation
@RuntimeAnnotation
public class ClassToIntrospect<T> {
  private static final String STATIC_FIELD = "STATIC_FIELD value";

  private final String stringField = "stringField value";
  int intField = 42;

  public void ClassToIntrospect(){}
  public void ClassToIntrospect(int intField){this.intField = intField;}


  private void privateMethod(){}
  public T genericMethod(){return null;}
  public void methodWithException() throws Exception {}

  public static final class StaticInnerClass {}
  public final class InnerClass{}
}
