package com.races.swing.application;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioThread implements Runnable {
    @Override
    public void run() {
        try {
            Player player = new Player(getClass().getClassLoader()
                    .getResourceAsStream("song.mp3"));
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
