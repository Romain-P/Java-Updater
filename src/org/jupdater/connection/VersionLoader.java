package org.jupdater.connection;


import com.typesafe.config.ConfigFactory;
import org.apache.commons.io.IOUtils;
import org.jupdater.core.Config;
import org.jupdater.gui.OutWriter;

import java.io.*;
import java.net.URL;

public class VersionLoader {
	private long lastConfigModification;
    private org.jupdater.core.Config config;

    public VersionLoader(org.jupdater.core.Config config) {
        this.config = config;
    }

    public void initializeVersion() {
    	File config = getConfigFile(this.config);
        com.typesafe.config.Config configVersion =  ConfigFactory.parseFile(config);
        
        try {
        	//set last modification
        	lastConfigModification = config.lastModified();
        	
        	//configure updater
            this.config.setVersionData(configVersion.getString("release.current"));
            this.config.setVersionPath(this.config.getVersionUrl() + "/" + configVersion.getString("release.folder") + "/");
            this.config.setRequiredFile(configVersion.getString("release.files.required"));
            this.config.setLaunchRequiredFileAfterUpdate(configVersion.getBoolean("release.files.launch_required_after_update"));
            this.config.setRequiredReleases(configVersion.getString("release.required_lasts"));
            this.config.setLocalBackgroundUrl(configVersion.getString("release.design.url.local_background_url"));
            this.config.setLocalCloseIconUrl(configVersion.getString("release.design.url.local_close_icon"));
            this.config.setLocalCloseIconPosition(configVersion.getString("release.design.position.close_icon_position"));
            this.config.setLocalOutputContainerPosition(configVersion.getString("release.design.position.output_text_position"));
            this.config.setLocalOutputTextSize(configVersion.getInt("release.design.size.output_text_size"));
        } catch(Exception e) {
            System.out.println("Impossible to read last version data, please contact administrator." +
                   "\n (" + e.getMessage() + ")");
            System.exit(1);
        }
    }

    private File getConfigFile(org.jupdater.core.Config config) {
        File file = null;
        try {
            URL fileUrl = new URL(config.getVersionUrl()+"/config.conf");
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
    
    public boolean newReleaseAvailable() {
    	File newConfig = getConfigFile(config);
    	return newConfig.lastModified() != lastConfigModification;
    }
}