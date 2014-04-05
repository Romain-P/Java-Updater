package org.jupdater.core;

import java.io.File;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jupdater.connection.FileLoader;
import org.jupdater.connection.VersionLoader;
import org.jupdater.core.injector.MainModule;
import org.jupdater.data.DataManager;

public class Main {
    /**
     *
     * @author Romain-P
     */
    public static void main(String[] args) {
        //build singletons
        Injector injector = Guice.createInjector(new MainModule());
        Config config = injector.getInstance(Config.class);

        //initialize host releases path
        VersionLoader versionLoader = injector.getInstance(VersionLoader.class);
        versionLoader.initializeVersion();

        //check required file
        File requiredFile = new File(config.getRequiredFile());
        if(!requiredFile.exists()) {
            System.out.println("You must put the updater into the good folder (which contains "+requiredFile.getPath()+")");
        } else {
            //get instance
            DataManager manager = injector.getInstance(DataManager.class);
            //load already installed releases
            manager.loadLocalData();
            //start program
            FileLoader fileLoader = injector.getInstance(FileLoader.class);
            fileLoader.launchUpdate();
        }
    }
}
