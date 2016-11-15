package serialization;

import java.io.Serializable;

/**
 * @author Alpi
 * @since 13.11.16
 */
public class SerializableClass implements Serializable {
  private static final long serialVersionUID = 321321321L;

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
