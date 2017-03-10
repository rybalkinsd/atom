package ru.atom.model;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class LevelTest {

    @Test
    public void standardLevelConsistency() throws Exception {
        assertThat(Level.STANDARD.getSpawnPlaces().size()).isGreaterThanOrEqualTo(Level.MAX_PLAYERS);
        assertThat(Level.STANDARD.toString()).isEqualTo(
                "Level STANDARD {\n" +
                        "XXXXXXXXXXXXXXXXX\n" +
                        "XS ooooooooooo SX\n" +
                        "X XoXoXoXoXoXoX X\n" +
                        "XoooooooooooooooX\n" +
                        "XoXoXoXoXoXoXoXoX\n" +
                        "XoooooooooooooooX\n" +
                        "XoXoXoXoXoXoXoXoX\n" +
                        "XoooooooooooooooX\n" +
                        "XoXoXoXoXoXoXoXoX\n" +
                        "XoooooooooooooooX\n" +
                        "X XoXoXoXoXoXoX X\n" +
                        "XS ooooooooooo SX\n" +
                        "XXXXXXXXXXXXXXXXX\n" +
                "}");
    }

}