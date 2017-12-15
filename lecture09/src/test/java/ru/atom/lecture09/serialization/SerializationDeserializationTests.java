package ru.atom.lecture09.serialization;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

public class SerializationDeserializationTests {
    @Test
    public void serializableSerialize() throws IOException, ClassNotFoundException {
        try (FileOutputStream fos = new FileOutputStream("serializable.out");
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            SerializableClass toSerialize = new SerializableClass("field value", "transient field value");
            oos.writeObject(toSerialize);
            oos.flush();
        }
    }

    @Test
    public void serializableDeserialize() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("serializable.out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        SerializableClass serializable = (SerializableClass) oin.readObject();
        assertNotNull(serializable);
        System.out.println(serializable);
    }

    @Test(expected = NotSerializableException.class)
    public void nonSerializableSerialize() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("temp.out");
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            NonSerializableClass toSerialize = new NonSerializableClass();
            oos.writeObject(toSerialize);
            oos.flush();
        }
    }

    @Test
    public void externalizableSerialize() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("externalizable.out");
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            ExternalizableClass toSerialize = new ExternalizableClass(
                    Arrays.asList("true", "false", "привет", "меня зовут Вадим", "true", "false"));
            oos.writeObject(toSerialize);
            oos.flush();
        }
    }

    @Test
    public void externalizableDeserialize() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("externalizable.out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        ExternalizableClass deserialized = (ExternalizableClass) oin.readObject();
        assertNotNull(deserialized);
        System.out.println(deserialized);
    }
}
