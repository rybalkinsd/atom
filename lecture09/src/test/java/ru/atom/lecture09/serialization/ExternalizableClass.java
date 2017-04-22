package ru.atom.lecture09.serialization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExternalizableClass implements Externalizable {
    /**
     * Suppose this list contains strings "true" and "false"
     * We want to serialize this list efficiently
     */
    private List<String> strings = new ArrayList<>();

    public ExternalizableClass() {
    }

    public ExternalizableClass(List<String> strings) {
        this.strings = strings;
    }

    /**
     * So instead of marking class as Serializable we mark it as Externalizable and implement
     * writeExternal (custom serialization) and readExternal (custom deserialization)
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        byte[] dataToSerialize = new byte[strings.size() + 1];
        int writeIndex = 0;
        for (int i = 0; i < strings.size(); ++i) {
            String str = strings.get(i);
            if ("true".equals(str)) {
                dataToSerialize[writeIndex++] = 1;
            } else if ("false".equals(str)) {
                dataToSerialize[writeIndex++] = 0;
            }
        }
        dataToSerialize[writeIndex] = -1;
        out.write(Arrays.copyOf(dataToSerialize, writeIndex));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        while (true) {
            int read = in.read();
            if (read == 1) {
                strings.add("true");
            } else if (read == 0) {
                strings.add("false");
            } else if (read == -1) {
                break;
            } else {
                strings.add("undefined");
            }
        }
    }

    @Override
    public String toString() {
        return "ExternalizableClass{" +
                "strings='" + strings + '\'' +
                '}';
    }
}
