package com.races.swing.application;

import com.races.swing.application.participants.Road;

import javax.swing.*;

public class GameView extends JFrame {
    public void setFrameParameters() {
        this.setTitle("Races");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 684);
        this.setResizable(false);
        this.add(new Road());
        this.setVisible(true);
    }
}
