package com.races.swing.application.participants;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class Character {

    Road road;

    protected int getWidth(String resource) {
        return new ImageIcon(getResource(resource)).getIconWidth();
    }

    protected int getHeight(String resource) {
        return new ImageIcon(getResource(resource)).getIconHeight();
    }

    protected Image getImage(String image) {
        return new ImageIcon(getResource(image)).getImage();
    }

    public abstract Rectangle getRectangle();

    protected URL getResource(String resource) {
        return getClass().getClassLoader().getResource(resource);
    }

}
