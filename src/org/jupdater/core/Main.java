package org.jupdater.core;

import java.io.File;

import com.sun.deploy.net.proxy.DeployProxySelector;
import com.sun.deploy.services.PlatformType;
import com.sun.deploy.services.ServiceManager;
import org.jupdater.connection.FileLoader;
import org.jupdater.connection.VersionLoader;

public class Main {
    /**
     *
     * @author Romain-P
     */
    public static void main(String[] args) {
        initializeDefaultProxy();
    	VersionLoader.initializeVersion();
    	
        File requiredFile = new File(Config.getInstance().getRequiredFile());
        if(!requiredFile.exists()) {
            System.out.println("You must put the updater into the good folder (which contains "+requiredFile.getPath()+")");
        } else {
            FileLoader fileLoader = new FileLoader();
            fileLoader.launchUpdate();
        }
    }

    private static void initializeDefaultProxy() {
        ServiceManager.setService(System.getProperty("os.name").toLowerCase().indexOf("windows") != -1
                        ? PlatformType.STANDALONE_TIGER_WIN32
                        : PlatformType.STANDALONE_TIGER_MACOSX);
        try {
            // This will call ProxySelector.setDefault():
            DeployProxySelector.reset();
        } catch (Throwable throwable) {}
    }
}
