package configuration_via_reflection;

/**
 * @author Alpi
 * @since 13.11.16
 */
public class FirstServiceImplementation implements Service {
  @Override
  public void serve() {
    System.out.println("First service serve()");
  }
}
