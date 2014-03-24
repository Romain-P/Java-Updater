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
import org.jupdater.data.DataManager;

public class FileLoader {
    //will be util
    private ExecutorService threadWorker = Executors.newSingleThreadExecutor();

    public void launchUpdate() {

        //check if folder has the last release
        if(Config.getInstance().getInstalledReleases().contains(Config.getInstance().getVersionData())) {
            System.out.println("Program already updated ! You don't need more updates.");
            return;
        }

        //starting update
        System.out.println("You will be updated to the version "+Config.getInstance().getVersionData());

        threadWorker.execute(() -> {
            String sReleases = Config.getInstance().getRequiredReleases();
            sReleases = sReleases.contains(",") ? sReleases : sReleases + ",";

            System.out.println("Downloading required old releases..");

            for(String release: sReleases.split(",")) {
                if(Config.getInstance().getInstalledReleases().contains(release))
                    continue;
                System.out.println("Downloading release "+release+"..");
                ZipFile required = loadDistantRelease(release);
                System.out.println("Extract release "+release+"..");
                updateFolder(required);
                System.out.println("Release "+release+" downloaded with success !");

                //add to local data
                DataManager.updateData(release);
            }

            System.out.println("Old releases downloaded with success !");

            System.out.println("Downloading the last release..");
            ZipFile lastRelease = loadDistantRelease(Config.getInstance().getVersionData());
            updateFolder(lastRelease);

            //add to local data
            DataManager.updateData(Config.getInstance().getVersionData());

            System.out.println("Folder updated to version "+Config.getInstance().getVersionData()+" with success !");
            threadWorker.shutdown();
        });
    }

    private void updateFolder(ZipFile zipFile) {
        try {
            Enumeration<?> list = zipFile.entries();

            byte[] buffer = new byte[512*1024];

            while(list.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)list.nextElement();
                File futureFile = new File(entry.getName());

                if(futureFile.getParentFile() != null)
                    futureFile.getParentFile().mkdirs();

                if(entry.isDirectory())
                    continue;
                else if (!futureFile.exists() || entry.getSize() != futureFile.length()) {
                    System.out.println("Extracting " + futureFile);

                    BufferedInputStream input = new BufferedInputStream(zipFile.getInputStream(entry));
                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(futureFile), 512*1024);

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
            System.out.println("An error was found :" + e.getMessage());
        }
    }

    private ZipFile loadDistantRelease(String release) {
        try {
            byte[] buffer = new byte[512*1024];
            URLConnection connection = new URL(Config.getInstance().getVersionPath()+release+".zip").openConnection();

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

            return new ZipFile(distantFile);
        } catch (Exception e) {
            System.out.println("Please check your internet connection, failed to load  " +
                    Config.getInstance().getVersionPath() + " : " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
