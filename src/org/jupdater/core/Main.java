package org.jupdater.core;

import java.io.File;

import org.jupdater.connection.FileLoader;
import org.jupdater.connection.VersionLoader;

public class Main {
    /**
     *
     * @author Romain-P
     */
    public static void main(String[] args) {
    	VersionLoader.initializeVersion();
    	
        File requiredFile = new File(Config.getInstance().getRequiredFile());
        if(!requiredFile.exists()) {
            System.out.println("You must put the updater into the good folder (which contains "+requiredFile.getPath()+")");
        } else {
            FileLoader fileLoader = new FileLoader();
            fileLoader.launchUpdate();
        }
    }
}
