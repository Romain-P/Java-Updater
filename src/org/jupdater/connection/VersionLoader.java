package org.jupdater.connection;


import com.typesafe.config.ConfigFactory;
import org.apache.commons.io.IOUtils;
import org.jupdater.core.Config;
import org.jupdater.gui.OutWriter;

import java.io.*;
import java.net.URL;

public class VersionLoader {
	private static long lastConfigModification;

    public static void initializeVersion() {
    	File config = getConfigFile();
        com.typesafe.config.Config configVersion =  ConfigFactory.parseFile(config);
        
        try {
        	//set last modification
        	lastConfigModification = config.lastModified();
        	
        	//configure updater
            Config.getInstance().setVersionData(configVersion.getString("release.current"));
            Config.getInstance().setVersionPath(Config.getInstance().getVersionUrl() + "/"
                    + configVersion.getString("release.folder") + "/");
            Config.getInstance().setRequiredFile(configVersion.getString("release.files.required"));
            Config.getInstance().setLaunchRequiredFileAfterUpdate(configVersion.getBoolean("release.files.launch_required_after_update"));
            Config.getInstance().setRequiredReleases(configVersion.getString("release.required_lasts"));
            Config.getInstance().setLocalBackgroundUrl(configVersion.getString("release.design.url.local_background_url"));
            Config.getInstance().setLocalCloseIconUrl(configVersion.getString("release.design.url.local_close_icon"));
            Config.getInstance().setLocalCloseIconPosition(configVersion.getString("release.design.position.close_icon_position"));
            Config.getInstance().setLocalOutputContainerPosition(configVersion.getString("release.design.position.output_text_position"));
            Config.getInstance().setLocalOutputTextSize(configVersion.getInt("release.design.size.output_text_size"));
        } catch(Exception e) {
            System.out.println("Impossible to read last version data, please contact administrator." +
                   "\n (" + e.getMessage() + ")");
            System.exit(1);
        }
    }

    private static File getConfigFile() {
        File file = null;
        try {
            URL fileUrl = new URL(Config.getInstance().getVersionUrl()+"/config.conf");
            InputStream input = fileUrl.openStream();

            file = File.createTempFile("version",".conf" );
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));

            IOUtils.copy(input, output);

            output.close();
            input.close();
        } catch (Exception e) {
            OutWriter.writeError("Impossible to load last version data, please check your internet connection." +
                    "\n ("+e.getMessage()+")");
            System.exit(1);
        }

        return file;
    }
    
    public static boolean newReleaseAvailable() {
    	File newConfig = getConfigFile();
    	return newConfig.lastModified() != lastConfigModification;
    }
}