package org.jupdater.connection;

import org.apache.commons.io.IOUtils;
import org.jupdater.core.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileLoader {
    //path, file
    private Map<String, File> files = new HashMap<>();

    public void launchUpdate() {
        loadDirectoryFiles(Config.getInstance().getVersionPath());

        for(Map.Entry<String, File> mFile: this.files.entrySet()) {
            File file = new File(mFile.getKey());
            try {
                File folders = new File(file.getParent());
                if(!folders.exists()) {
                    folders.mkdirs();
                }
                if(!file.isFile() || !file.exists()) {
                    file.createNewFile();
                    file.setLastModified(mFile.getValue().lastModified());
                }

                updateFile(file, mFile.getValue());
            } catch(Exception e) {
                System.out.println("An error was found.. error with the file ("+file.getName()+"): "+e.getMessage());
            }
        }
    }

    private void updateFile(File file, File futureFile) {
        if(file.length() != 0)
            file.delete();
        else if (file.length() != futureFile.length()
                || file.lastModified() < futureFile.lastModified()) {
            file.delete();

            try {
                futureFile.createNewFile();
            } catch(Exception e) {
                System.out.println("An error was found.. error with the file (" + file.getName() + "): " + e.getMessage());
            }
        }
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
