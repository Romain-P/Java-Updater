package org.jupdater.core;

import org.jupdater.connection.FileLoader;

import java.io.File;

public class Main {
    /**
     *
     * @author Romain-P
     */
    public static void main(String[] args) {
        File requiredFile = new File(Config.getInstance().getRequiredFile());
        if(!requiredFile.exists()) {
            System.out.println("You must put the updater into the good folder (which contains "+requiredFile.getPath()+")");
            System.exit(1);
        } else {
            FileLoader fileLoader = new FileLoader();
            fileLoader.launchUpdate();
        }
    }
}
