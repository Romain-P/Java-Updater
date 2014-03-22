package org.jupdater.core;

import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Config {
    //config.conf
    private com.typesafe.config.Config configFile;
    //data
    private String xmlVersionUrl;

    //disable public instancing
    private Config() {
       configFile = ConfigFactory.parseFile(new File("config.conf"));
    }

    public Config initialize() {
        this.xmlVersionUrl = configFile.getString("connection.url.xmlVersion");
        return this;
    }

    private static class ConfigHolder {
        private static final Config instance = new Config().initialize();
    }

    public static Config getInstance() {
        return ConfigHolder.instance;
    }

    public String getXmlVersionUrl() {
        return this.xmlVersionUrl;
    }
}
