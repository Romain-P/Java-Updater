package org.jupdater.connection;


import com.typesafe.config.ConfigFactory;
import org.jupdater.core.Config;

import java.io.*;
import java.net.URL;

public class VersionLoader {

    public static void initializeVersion() {
        com.typesafe.config.Config configVersion =  ConfigFactory.parseFile(getConfigFile());

        try {
            Config.getInstance().setVersionData(configVersion.getDouble("version.version"));
            Config.getInstance().setVersionPath(Config.getInstance().getVerionUrl()+configVersion.getString("version.path"));
        } catch(Exception e) {
            System.out.println("Impossible to read last version data, please contact administrator." +
                    "\n ("+e.getMessage()+")");
            System.exit(1);
        }
    }

    private static File getConfigFile() {
        File file = null;
        try {
            URL fileUrl = new URL(org.jupdater.core.Config.getInstance().getVerionUrl());
            InputStream fileIn = fileUrl.openStream();

            file = File.createTempFile("version",".conf" );
            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));

            int data;
            while ((data = fileIn.read()) != -1)
                fileOut.write(data);

            fileOut.flush();
            fileOut.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Impossible to load last version data, please check your internet connection." +
                    "\n ("+e.getMessage()+")");
            System.exit(1);
        }

        return file;
    }
}