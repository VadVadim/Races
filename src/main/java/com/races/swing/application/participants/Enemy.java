package com.races.swing.application.participants;

import java.awt.*;

import static com.races.swing.application.Constants.*;

public class Enemy extends Character {

    int x;
    int y;
    int speed;

    Road road;

    Image image = getImage(ENEMY);
    Image redCarImage = getImage(RED_CAR);
    Image caparoCarImage = getImage(CAPARO);

    int width = getWidth(ENEMY);
    int height = getHeight(ENEMY);

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
