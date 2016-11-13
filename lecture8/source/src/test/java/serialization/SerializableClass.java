package serialization;

/**
 * @author Alpi
 * @since 13.11.16
 */
public class SerializableClass {
  /**
   * this field will be serialized
   */
  private final String field;
  /**
   * this field will be ignored during serialization and deserialization
   * via standard Serialization mechanism
   * because it is marked as transient
   */
  private final transient String transientField;

  public SerializableClass(String field, String transientField) {
    this.field = field;
    this.transientField = transientField;
  }

  @Override
  public String toString() {
    return "SerializableClass{" +
        "field='" + field + '\'' +
        ", transientField='" + transientField + '\'' +
        '}';
  }
}
