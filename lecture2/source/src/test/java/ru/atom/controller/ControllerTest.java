package ru.atom.controller;


import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public class ControllerTest {
    @Test
    public void onNext() throws Exception {
        String html = Controller.onNext();

        String filename = "filename.html";
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(html);
        }

        Desktop.getDesktop().browse(new File(filename).toURI());
    }

}