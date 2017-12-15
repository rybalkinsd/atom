package ru.atom.lecture09.serialization;

import java.io.Serializable;

public class SerializableClass implements Serializable {
    private static final long serialVersionUID = 321321321L;

    /**
     * this field will be serialized
     */
    private final String field;
    /**
     * this field will be ignored during ru.atom.lecture09.serialization and deserialization
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
