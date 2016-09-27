package ru.atom.controller;


import org.junit.Before;
import org.junit.Test;
import ru.atom.model.Gender;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public class ControllerTest {

    private Controller controller;

    @Before
    public void setUp() throws Exception {
        controller = new Controller(new RestClientImplMock(), Gender.FEMALE);
    }

    @Test
    public void onNext() throws Exception {
        String html = controller.onNext();

        String filename = "filename.html";
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(html);
        }

        Desktop.getDesktop().browse(new File(filename).toURI());
    }

}