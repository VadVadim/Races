package com.races.swing.application.participants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.races.swing.application.Constants.*;

public class Player extends Character {

    Image imageCenter = new ImageIcon(getResource(PLAYER)).getImage();
    Image imageLeft = new ImageIcon(getResource(PLAYER_LEFT)).getImage();
    Image imageRight = new ImageIcon(getResource(PLAYER_RIGHT)).getImage();

    Image image = imageCenter;

    int width = new ImageIcon(getResource(PLAYER)).getIconWidth();
    int height = new ImageIcon(getResource(PLAYER)).getIconHeight();

    int speed;
    private int acceleration;
    int mileage;

    int x = 30;
    int y = 300;
    int dy;
    int dx;

    int layer;
    int otherLayer = WINDOW_LENGTH;


    void move() {
        mileage += speed;
        speed += acceleration;
        if (speed <= 0) speed = 0;
        if (speed >= MAX_SPEED) speed = MAX_SPEED;
        y -= dy;
        if (y <= MAX_TOP) y = MAX_TOP;
        if (y >= MAX_BOTTOM) y = MAX_BOTTOM;
        x+= dx;
        if (x <= MAX_LEFT) x = MAX_LEFT;
        if (x >= MAX_RIGHT) x = MAX_RIGHT;
        if (otherLayer - speed <= 0) {
            layer = 0;
            otherLayer = WINDOW_LENGTH;
        } else {
            layer -= speed;
            otherLayer -= speed;
        }
    }

    void keyPressed(KeyEvent e)  {
        int key = e.getKeyCode();
        if(KeyEvent.VK_RIGHT == key) {
           acceleration = 5;
           dx = 3;
        }
        if(KeyEvent.VK_LEFT == key) {
            acceleration = -5;
            dx = -6;
        }
        if(KeyEvent.VK_UP == key) {
            dy = 10;
            image = imageLeft;
        }
        if(KeyEvent.VK_DOWN == key) {
            dy = -10;
            image = imageRight;
        }
    }

    void keyReleased(KeyEvent e)  {
        int key = e.getKeyCode();
        if(KeyEvent.VK_RIGHT == key || KeyEvent.VK_LEFT == key) {
            acceleration = 0;
            dx = 0;
        }
        if(KeyEvent.VK_UP == key || KeyEvent.VK_DOWN == key) {
            dy = 0;
            image = imageCenter;
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
}
