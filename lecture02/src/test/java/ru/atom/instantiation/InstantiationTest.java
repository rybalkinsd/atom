package ru.atom.instantiation;

import org.junit.Test;

public class InstantiationTest {

    // Важно обратить внимание на блоки статической инициализации
    // при выполнении всех тестов в одном запуске

    /**
     *  Порядок инициализации при инстанцировании класса Sub.
     */
    @Test
    public void testSubClassInstantiation() throws Exception {
        Base sub = new Sub();
    }

    /**
     * Порядок инициализации при инстанцировании класса Sub.
     */
    @Test
    public void testBaseClassInstantiation() throws Exception {
        Base base = new Base();
    }
}