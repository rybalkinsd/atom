package ru.atom.view;

/**
 * Created by s.rybalkin on 27.09.2016.
 */
public class MainPageView {

    public static final String html;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<body>")
                .append("<h1 style=\"color: #5e9ca0;\">Tinder</h1>")
                .append("<h2 style=\"color: #2e6c80;\">Profile</h2>")
                // image
                .append("<p><img src=\"%s\" alt=\"Image\" style=\"width: %dpx; height: %dpx;\"/></p>")
                // name
                .append("<p>%s</p>")
                // age
                .append("<p><strong>Age: </strong>%d</p>")
                // description
                .append("<p><strong>Description:</strong> %s</p>")
                // instagram
                .append("<p><a href=\"%s\">Instagram</a></p>")
                // like or nope
                .append("<p><span style=\"background-color: #FF0000; color: #fff; display: inline-block; padding: 3px 10px; font-weight: bold; border-radius: 5px;\">Nope</span>")
                .append("<span style=\"background-color: #009933; color: #fff; display: inline-block; padding: 3px 10px; font-weight: bold; border-radius: 5px;\">Like</span></p>")
                .append("</body>")
                .append("</html>");

        html = builder.toString();
    }


    private MainPageView() {
    }
}
