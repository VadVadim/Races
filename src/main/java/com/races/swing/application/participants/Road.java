package com.races.swing.application.participants;

import com.races.swing.application.AudioThread;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.races.swing.application.Constants.MAX_SPEED;
import static com.races.swing.application.Constants.ROAD;

public class Road extends JPanel implements ActionListener, Runnable {

    Timer mainTimer = new Timer(20, this);

    Image image = new ImageIcon(getClass().getClassLoader().getResource(ROAD)).getImage();

    Player player = new Player();

    Thread enemiesFactory = new Thread(this);

    Thread audioThread = new Thread(new AudioThread());

    List<PoliceCar> policemen = new ArrayList<>();

    Thread policemenFactory = new Thread(() -> {
        while (true) {
            Random random = new Random();
            try {
                TimeUnit.SECONDS.sleep(4);
                policemen.add(new PoliceCar(-2000, random.nextInt(400), random.nextInt(60), this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    List<Enemy> enemies = new ArrayList<>();

    int money;

    Rectangle health = new Rectangle(950,8, 30, 70);

    public Road() {
        mainTimer.start();
        enemiesFactory.start();
        policemenFactory.start();
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
        drawMoney(g);
        drawHealth(g);

        drawEnemies(g);
        drawPolicemen(g);
    }

    private void drawHealth(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.blue);
        g2D.fill(health);
    }

    private void drawMoney(Graphics g) {
        g.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.ITALIC, 20);
        g.setFont(font);
        if (money < 0) {
            g.drawString("Fine: " + money + " $", 40, 50);
        } else {
            g.drawString("Money: " + money + " $", 40, 50);
        }
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
                if (enemy.speed <= 20) {
                    g.drawImage(enemy.image, enemy.x, enemy.y, null);
                } else if (enemy.speed <=40) {
                    g.drawImage(enemy.redCarImage, enemy.x, enemy.y, null);
                } else {
                    g.drawImage(enemy.caparoCarImage, enemy.x, enemy.y, null);
                }
            }
        }
    }

    private void drawPolicemen(Graphics g) {
        ListIterator<PoliceCar> iterator = policemen.listIterator();
        while (iterator.hasNext()) {
            PoliceCar policeCar = iterator.next();
            if (policeCar.x >= 2400 || policeCar.x <= -2400) {
                iterator.remove();
            } else {
                policeCar.move();
                g.drawImage(policeCar.image, policeCar.x, policeCar.y, null);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        player.move();
        repaint();
        improveHealth();
        testСollisionWithEnemies();
        testСollisionWithPolicemen();
        testWin();
        increaseMoney();
    }

    private void increaseMoney() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (Math.abs(player.getRectangle().getCenterX() - enemy.getRectangle().getCenterX()) < 150
                  && Math.abs(player.getRectangle().getCenterY()
                    - enemy.getRectangle().getCenterY()) < 150
                    && player.speed >=25
                    && player.acceleration > 0
                    && !player.getRectangle().intersects(enemy.getRectangle())) {
                money += 10;
            }
        }
    }

    private void improveHealth() {
        double healthHeight = health.getHeight();
        int y = (int) health.getY();
        if (money >= 200 && healthHeight < 70) {
            healthHeight += 10;
            y-=10;
            health.setLocation((int) health.getX(), y);
            health.setSize(health.width, (int) healthHeight);
            money -= 200;
        }
        if (health.getHeight() >=70) {
            health = new Rectangle(950,8, 30, 70);
        }
    }

    private void testWin() {
        if (player.mileage > 300000) {
            JOptionPane.showMessageDialog(null, "You won!!!");
            System.exit(0);
        }
    }

    private void testСollisionWithPolicemen() {
        Iterator<PoliceCar> iterator = policemen.iterator();
        while (iterator.hasNext()) {
            PoliceCar policeCar = iterator.next();
            if (player.getRectangle().intersects(policeCar.getRectangle())) {
                money -=1;
            }
        }
    }

    private void testСollisionWithEnemies() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (player.getRectangle().intersects(enemy.getRectangle())) {
                double healthHeight = health.getHeight();
                int y = (int) health.getY();
                healthHeight -= 1;
                y+=1;
                health.setLocation((int) health.getX(), y);
                health.setSize(health.width, (int) healthHeight);
                if (health.getHeight() ==0) {
                    JOptionPane.showMessageDialog(null, "You lose!!!");
                    System.exit(1);
                }
            }
        }
    }

    public void run() {
        while (true) {
            Random random = new Random();
            try {
                TimeUnit.SECONDS.sleep(2);
                enemies.add(new Enemy(1200, random.nextInt(600), random.nextInt(60), this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
