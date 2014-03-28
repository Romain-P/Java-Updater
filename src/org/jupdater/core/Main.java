package org.jupdater.core;

import java.io.File;

import org.jupdater.connection.FileLoader;
import org.jupdater.connection.VersionLoader;
import org.jupdater.data.DataManager;

public class Main {
    /**
     *
     * @author Romain-P
     */
    public static void main(String[] args) {
        //initialize host releases path
    	VersionLoader.initializeVersion();

        //check required file
        File requiredFile = new File(Config.getInstance().getRequiredFile());
        if(!requiredFile.exists()) {
            System.out.println("You must put the updater into the good folder (which contains "+requiredFile.getPath()+")");
        } else {
            //load already installed releases
            DataManager.loadLocalData();
            //start program
            FileLoader fileLoader = new FileLoader();
            fileLoader.launchUpdate();
        }
    }
}
