package ru.atom.model;

import java.net.URL;

/**
 * Image data.
 * Immutable.
 */
public class Image {
    private final URL url;
    private final int width;
    private final int hight;

    public Image(URL url, int width, int hight) {
        this.url = url;
        this.width = width;
        this.hight = hight;
    }

    public URL getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHight() {
        return hight;
    }
}
