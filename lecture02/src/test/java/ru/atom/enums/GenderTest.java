package ru.atom.enums;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.atom.enums.Country.Russia;
import static ru.atom.enums.Country.USA;
import static ru.atom.enums.Gender.Female;


/**
 * Created by sergey on 2/28/17.
 */
public class GenderTest {

    @Test
    public void isLegal() throws Exception {
        assertThat(Female.isLegalCouple(USA, Female), is(true));
        assertThat(Female.isLegalCouple(Russia, Female), is(false));
    }

}