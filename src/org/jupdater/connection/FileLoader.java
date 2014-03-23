package org.jupdater.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jupdater.core.Config;

public class FileLoader {
    //will be util
    private ExecutorService threadWorker = Executors.newSingleThreadExecutor();

    public void launchUpdate() {
        System.out.println("You will be updated to the version "+Config.getInstance().getVersionData());

        threadWorker.execute(() -> {
            try {
                byte[] buffer = new byte[512*1024];
                URLConnection connection = new URL(Config.getInstance().getVersionPath()).openConnection();

                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                File distantFile = File.createTempFile("zips", "zip");
                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(distantFile));

                System.out.println("Loading "+Config.getInstance().getVersionPath());
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1)
                  output.write(buffer, 0, bytesRead);

                input.close();
                output.close();

                System.out.println("Extract in progress..");

                ZipFile zipFile = new ZipFile(distantFile);
                Enumeration<?> list = zipFile.entries();

                while(list.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry)list.nextElement();
                    File futureFile = new File(entry.getName());

                    if(futureFile.getParentFile() != null)
                        futureFile.getParentFile().mkdirs();

                    if(entry.isDirectory())
                        continue;
                    else if (!futureFile.exists() || entry.getSize() != futureFile.length()) {
                        System.out.println("Extracting " + futureFile);

                        input = new BufferedInputStream(zipFile.getInputStream(entry));
                        output = new BufferedOutputStream(new FileOutputStream(futureFile), 512*1024);

                        int count;
                        while ((count = input.read(buffer, 0, 512*1024)) != -1)
                            output.write(buffer, 0, count);

                        output.flush();
                        output.close();
                        input.close();

                        futureFile.setLastModified(entry.getTime());
                    }
                }
                zipFile.close();
            } catch(Exception e) {
                System.out.println("An error was found, the program will stop :" + e.getMessage());
            }
            System.out.println("Folder updated to version "+Config.getInstance().getVersionData()+" with success !");
            threadWorker.shutdown();
        });
    }
}
