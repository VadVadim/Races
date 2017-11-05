package com.races.swing.application.participants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.races.swing.application.Constants.ROAD;

public class Road extends JPanel implements ActionListener {

    Timer mainTimer = new Timer(20, this);

    Image image = new ImageIcon(ROAD).getImage();

    Player player = new Player();

    public Road() {
        mainTimer.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, player.layer, 0, null);
        g.drawImage(image, player.otherLayer, 0, null);
        g.drawImage(player.image, player.x, player.y, null);
    }

    public void actionPerformed(ActionEvent e) {
        player.move();
        repaint();
    }
}
