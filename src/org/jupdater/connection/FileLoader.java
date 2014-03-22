package org.jupdater.connection;

import org.jupdater.core.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileLoader {
    //path, file
    private Map<String, File> files = new HashMap<>();

    public void launchUpdate() {
        loadDirectoryFiles(Config.getInstance().getVersionPath());
    }

    private void loadDirectoryFiles(String directoryPath){
        File[] files;
        File directoryToScan = new File(directoryPath);
        files = directoryToScan.listFiles();

        for(File file: files) {
            if(file.isDirectory())
                loadDirectoryFiles(file.getPath());
            else
                this.files.put(file.getPath(), file);
        }
    }
}
