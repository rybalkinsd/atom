package ru.atom.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.atom.enums.Country.Russia;
import static ru.atom.enums.Country.USA;
import static ru.atom.enums.Gender.Female;


/**
 * Created by sergey on 2/28/17.
 */
public class GenderTest {

    @Test
    public void isLegal() throws Exception {
        assertTrue(Female.isLegalCouple(USA, Female));
        assertFalse(Female.isLegalCouple(Russia, Female));
    }

}