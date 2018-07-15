package com.races.swing.application.participants;

import com.races.swing.application.AudioThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.races.swing.application.Constants.MAX_SPEED;
import static com.races.swing.application.Constants.ROAD;

public class Road extends JPanel implements ActionListener, Runnable {

    Timer mainTimer = new Timer(20, this);

    Image image = new ImageIcon(getClass().getClassLoader().getResource(ROAD)).getImage();

    Player player = new Player();

    Thread enemiesFactory = new Thread(this);

    Thread audioThread = new Thread(new AudioThread());

    List<Enemy> enemies = new ArrayList<>();

    public Road() {
        mainTimer.start();
        enemiesFactory.start();
        audioThread.start();
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

        drawSpeed(g);
        drawMileage(g);

        drawEnemies(g);
    }

    private void drawMileage(Graphics g) {
        g.setColor(Color.WHITE);
        Font secondFont = new Font("Arial", Font.ITALIC, 40);
        g.setFont(secondFont);
        g.drawString("Mileage: " + player.mileage + " m", 600, 50);
    }

    private void drawSpeed(Graphics g) {
        double speed = (200/MAX_SPEED)*player.speed;
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("Speed: " + speed + " km/h", 300, 50);
    }

    private void drawEnemies(Graphics g) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.x >= 2400 || enemy.x <= -2400) {
                iterator.remove();
            } else {
                enemy.move();
                g.drawImage(enemy.image, enemy.x, enemy.y, null);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        player.move();
        repaint();
        testСollisionWithEnemies();
        testWin();
    }

    private void testWin() {
        if (player.mileage > 200000) {
            JOptionPane.showMessageDialog(null, "You won!!!");
            System.exit(0);
        }
    }

    private void testСollisionWithEnemies() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (player.getRectangle().intersects(enemy.getRectangle())) {
                JOptionPane.showMessageDialog(null, "You lose!!!");
                System.exit(1);
            }
        }
    }

    public void run() {
        while (true) {
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(2000));
                enemies.add(new Enemy(1200, random.nextInt(600), random.nextInt(60), this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
