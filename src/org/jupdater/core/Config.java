package org.jupdater.core;

import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Config {
    //singleton
    private static Config config;
    //config.conf
    private com.typesafe.config.Config configFile;
    //data
    private String xmlVersionUrl;

    //disable public instancing
    private Config() {
       configFile = ConfigFactory.parseFile(new File("config.conf"));
    };

    public Config initialize() {
        this.xmlVersionUrl = configFile.getString("connection.url.xmlVersion");
        return this;
    }

    public static Config getInstance() {
        if(config == null)
            config = new Config().initialize();
        return config;
    }

    public String getXmlVersionUrl() {
        return this.xmlVersionUrl;
    }
}
