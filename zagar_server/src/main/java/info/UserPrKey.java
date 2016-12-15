package info;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserPrKey implements Serializable {
    private int id;
    @NotNull
    private String name;

    @Override
    public boolean equals(Object obj){
        if (obj instanceof UserPrKey) {
            UserPrKey _key = (UserPrKey)obj;
            return (name == _key.name || id == _key.id);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return id * 31 + name.hashCode() * 17;
    }
}