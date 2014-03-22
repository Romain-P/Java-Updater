package org.jupdater.core;

import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Config {
    //config.conf
    private com.typesafe.config.Config configFile;
    //data
    private String verionUrl;
    private double versionData;
    private String versionPath;

    //disable public instancing
    private Config() {
       configFile = ConfigFactory.parseFile(new File("config.conf"));
    }

    public Config initialize() {
        this.verionUrl = configFile.getString("connection.url.version");
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

    public double getVersionData() {
        return versionData;
    }

    public void setVersionData(double versionData) {
        this.versionData = versionData;
    }
}
