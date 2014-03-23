package org.jupdater.core;

import org.jupdater.connection.FileLoader;

public class Main {
    /**
     *
     * @author Romain-P
     */
    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();
        fileLoader.launchUpdate();
    }
}
