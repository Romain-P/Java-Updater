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
    private String requiredReleases;
    //local data
    private String installedReleases;

    //disable public instancing
    private Config() {
       configFile = ConfigFactory.parseFile(new File("config.conf"));
    }

    public Config initialize() {
        this.verionUrl = configFile.getString("connection.url.config");
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

    public void setRequiredFile(String requiredFile) {
        this.requiredFile = requiredFile;
    }

    public String getRequiredReleases() {
        return requiredReleases;
    }

    public void setRequiredReleases(String requiredReleases) {
        this.requiredReleases = requiredReleases;
    }

    public String getInstalledReleases() {
        return installedReleases;
    }

    public void setInstalledReleases(String installedReleases) {
        this.installedReleases = installedReleases;
    }

    public void addInstalledRelease(String release) {
        this.installedReleases += release+",";
    }
}
