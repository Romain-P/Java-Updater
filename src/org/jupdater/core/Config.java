package org.jupdater.core;

import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Config {
    //config.conf
    private com.typesafe.config.Config configFile = ConfigFactory.parseFile(new File("config.conf"));
    //data
    private String versionUrl = configFile.getString("connection.url.config");
    private String versionData;
    private String versionPath;
    private String requiredFile;
    private String requiredReleases = "";
    private boolean launchRequiredFileAfterUpdate;
    //design data
    private String localBackgroundUrl;
    private String localCloseIconUrl;
    private String localCloseIconPosition; //x,y
    private String localOutputContainerPosition; //x,y
    private int localOutputTextSize;
    //local data
    private String installedReleases = "";

    public String getVersionUrl() {
        return this.versionUrl;
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

    public String getLocalCloseIconUrl() {
        return localCloseIconUrl;
    }

    public void setLocalCloseIconUrl(String localCloseIconUrl) {
        this.localCloseIconUrl = localCloseIconUrl;
    }

    public String getLocalBackgroundUrl() {
        return localBackgroundUrl;
    }

    public void setLocalBackgroundUrl(String localBackgroundUrl) {
        this.localBackgroundUrl = localBackgroundUrl;
    }

    public String getLocalCloseIconPosition() {
        return localCloseIconPosition;
    }

    public void setLocalCloseIconPosition(String localCloseIconPosition) {
        this.localCloseIconPosition = localCloseIconPosition;
    }

    public String getLocalOutputContainerPosition() {
        return localOutputContainerPosition;
    }

    public void setLocalOutputContainerPosition(String localOutputContainerPosition) {
        this.localOutputContainerPosition = localOutputContainerPosition;
    }

    public boolean isLaunchRequiredFileAfterUpdate() {
        return launchRequiredFileAfterUpdate;
    }

    public void setLaunchRequiredFileAfterUpdate(boolean launchRequiredFileAfterUpdate) {
        this.launchRequiredFileAfterUpdate = launchRequiredFileAfterUpdate;
    }

    public int getLocalOutputTextSize() {
        return localOutputTextSize;
    }

    public void setLocalOutputTextSize(int localOutputTextSize) {
        this.localOutputTextSize = localOutputTextSize;
    }
}
