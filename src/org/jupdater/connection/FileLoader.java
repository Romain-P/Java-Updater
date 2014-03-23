package org.jupdater.connection;

import org.apache.commons.io.IOUtils;
import org.jupdater.core.Config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileLoader {
    //path, file
    private Map<String, File> files = new HashMap<>();
    private ExecutorService threadWorker = Executors.newSingleThreadExecutor();

    public void launchUpdate() {
        System.out.println("You will be updated to the version "+Config.getInstance().getVersionData());

        threadWorker.execute(() -> {
            loadDirectoryFiles(Config.getInstance().getVerionUrl()+"//"+Config.getInstance().getVersionData());

            for(Map.Entry<String, File> mFile: files.entrySet()) {
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

            System.out.println("Folder updated to version "+Config.getInstance().getVersionData()+" with success !");
            threadWorker.shutdown();
            System.exit(0);
        });
    }

    private void updateFile(File file, File futureFile) {
        if (file.length() != futureFile.length()
                || file.lastModified() < futureFile.lastModified()) {

            if(file.length() != 0) {
                while(!file.delete()) {
                    try {
                        Thread.sleep(50);
                    } catch(Exception e) {}
                }
            }

            try {
                InputStream input = new FileInputStream(futureFile);
                OutputStream output = new FileOutputStream(file);

                if(!file.exists())
                    file.createNewFile();

                IOUtils.copy(input, output);

                input.close();
                output.close();
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
