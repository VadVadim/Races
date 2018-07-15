package com.races.swing.application.participants;

import javax.swing.*;
import java.awt.*;

import static com.races.swing.application.Constants.*;

public class Enemy extends Character {

    int x;
    int y;
    int speed;

    Road road;

    Image image = new ImageIcon(getResource(ENEMY)).getImage();

    int width = new ImageIcon(getResource(ENEMY)).getIconWidth();
    int height = new ImageIcon(getResource(ENEMY)).getIconHeight();

    public Enemy(int x, int y, int speed, Road road) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.road = road;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public void move() {
        x = x - road.player.speed + speed;
        if (y <= MAX_TOP) y = MAX_TOP;
        if (y >= MAX_BOTTOM) y = MAX_BOTTOM;
    }

}
