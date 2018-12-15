package com.races.swing.application.participants;

import java.awt.*;

import static com.races.swing.application.Constants.*;

public class PoliceCar extends Character {

    int x;
    int y;
    int speed;

    Image image = getImage(POLICE);

    int width = getWidth(POLICE);
    int height = getHeight(POLICE);

    public PoliceCar(int x, int y, int speed, Road road) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.road = road;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public void move() {
        if (road.player.speed < 40 && road.player.mileage > 50000) {
            x = x - road.player.speed + speed;
        }
        if (y <= MAX_TOP) y = MAX_TOP;
        if (y >= MAX_BOTTOM) y = MAX_BOTTOM;
    }
}
