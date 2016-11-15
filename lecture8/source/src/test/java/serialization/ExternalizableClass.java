package serialization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Alpi
 * @since 13.11.16
 */
public class ExternalizableClass implements Externalizable {
  private String field = "true";

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.write(new byte[]{1});
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    int read = in.readByte();
    if(read == 1){
      this.field = "deserialized true";
    } else {
      this.field = "deserialized true";
    }
  }

  @Override
  public String toString() {
    return "ExternalizableClass{" +
        "field='" + field + '\'' +
        '}';
  }
}
