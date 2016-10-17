package ru.atom.model;

import java.net.URL;

/**
 * Image data.
 * Immutable.
 */
public class Image {
    private URL url;
    private int width;
    private int height;

    public Image() {
    }

    public Image(URL url, int width, int hight) {
        this.url = url;
        this.width = width;
        this.height = hight;
    }

    public URL getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
