package org.jupdater.connection;


import com.typesafe.config.ConfigFactory;
import org.apache.commons.io.IOUtils;
import org.jupdater.core.Config;

import java.io.*;
import java.net.URL;

public class VersionLoader {

    public static void initializeVersion() {
        com.typesafe.config.Config configVersion =  ConfigFactory.parseFile(getConfigFile());

        try {
            Config.getInstance().setVersionData(configVersion.getString("release.current"));
            Config.getInstance().setVersionPath(Config.getInstance().getVerionUrl() + "/"
            		+ configVersion.getString("release.folder") + "/");
            Config.getInstance().setRequiredFile(configVersion.getString("release.files.required"));
            Config.getInstance().setRequiredReleases(configVersion.getString("release.required_lasts"));
        } catch(Exception e) {
            System.out.println("Impossible to read last version data, please contact administrator." +
                    "\n ("+e.getMessage()+")");
            System.exit(1);
        }
    }

    private static File getConfigFile() {
        File file = null;
        try {
            URL fileUrl = new URL(org.jupdater.core.Config.getInstance().getVerionUrl()+"/config.conf");
            InputStream input = fileUrl.openStream();

            file = File.createTempFile("version",".conf" );
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));

            IOUtils.copy(input, output);

            output.close();
            input.close();
        } catch (Exception e) {
            System.out.println("Impossible to load last version data, please check your internet connection." +
                    "\n ("+e.getMessage()+")");
            System.exit(1);
        }

        return file;
    }
}