package ru.atom.controller;

/**
 * Created by s.rybalkin on 26.09.2016.
 */
public class Controller {

    public static String onNext() {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>")
            .append("<html>")
            .append("<body>")
                .append("<h2>Spectacular Mountain</h2>")
                .append("<img src=\"http://www.w3schools.com/html/pic_mountain.jpg\" alt=\"Mountain View\" style=\"width:304px;height:228px;\">")
            .append("</body>")
            .append("</html>");

        return builder.toString();
    }
}
