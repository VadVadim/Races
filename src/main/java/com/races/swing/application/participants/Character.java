package com.races.swing.application.participants;

import java.net.URL;

public class Character {

    protected URL getResource(String resource) {
        return getClass().getClassLoader().getResource(resource);
    }

}
