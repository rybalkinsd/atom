package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.controller.GameController;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(Config.class)
public class GameControllerTest {
    @Autowired
    private GameController controller;

    @Test
    public void list() throws Exception {
        assertTrue(controller.list().equals("[]"));
    }

}