package org.jupdater.data;

import org.apache.commons.io.IOUtils;
import org.jupdater.core.Config;

import java.io.*;
import java.nio.charset.Charset;

public class DataManager {
    public static void loadLocalData() {
        try {
            File file = new File("data.dat");
            if(!file.exists())
                file.createNewFile();
            else if (file.length() != 0) {
                InputStream stream = new FileInputStream(file);
                String data = IOUtils.readLines(stream, Charset.forName("UTF8")).get(0).split(":")[1].trim();

                Config.getInstance().setInstalledReleases(data);
            }
        } catch (Exception e) {
            System.out.println("Failed to read local data: "+e.getMessage());
        }
    }

    public static void updateData(String installedRelease) {
        try {
            Config.getInstance().addInstalledRelease(installedRelease);

            File file = new File("data.dat");
            if(!file.exists())
                file.createNewFile();

            OutputStream stream = new FileOutputStream(file);

            IOUtils.write("-installed-releases: ", stream, Charset.forName("UTF8"));
            IOUtils.write(Config.getInstance().getInstalledReleases(), stream, Charset.forName("UTF8"));

            stream.close();
        } catch(Exception e) {
            System.out.println("Failed to update local data: "+e.getMessage());
        }
    }
}
