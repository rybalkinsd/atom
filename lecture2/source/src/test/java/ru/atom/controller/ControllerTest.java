package ru.atom.controller;


import org.junit.Test;

import java.awt.*;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public class ControllerTest {
    @Test
    public void onNext() throws Exception {
        String html = Controller.onNext();
        
        Desktop.getDesktop().browse();
    }

}