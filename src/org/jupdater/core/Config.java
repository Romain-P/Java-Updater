package org.jupdater.core;

import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Config {
    //config.conf
    private com.typesafe.config.Config configFile;
    //data
    private String verionUrl;
    private String versionData;
    private String versionPath;
    private String requiredFile;

    //disable public instancing
    private Config() {
       configFile = ConfigFactory.parseFile(new File("config.conf"));
    }

    public Config initialize() {
        this.verionUrl = configFile.getString("connection.url.version");
        this.requiredFile = configFile.getString("connection.files.required");
        return this;
    }

    private static class ConfigHolder {
        private static final Config instance = new Config().initialize();
    }

    public static Config getInstance() {
        return ConfigHolder.instance;
    }

    public String getVerionUrl() {
        return this.verionUrl;
    }

    public String getVersionPath() {
        return versionPath;
    }

    public void setVersionPath(String versionPath) {
        this.versionPath = versionPath;
    }

    public String getVersionData() {
        return versionData;
    }

    public void setVersionData(String versionData) {
        this.versionData = versionData;
    }

    public String getRequiredFile() {
        return this.requiredFile;
    }
}
