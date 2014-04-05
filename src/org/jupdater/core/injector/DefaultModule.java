/*
 * Created by IntelliJ IDEA.
 * User: Return
 * Date: 05/04/14
 * Time: 17:04
 */
package org.jupdater.core.injector;

import org.jupdater.connection.FileLoader;
import org.jupdater.connection.VersionLoader;
import org.jupdater.core.Config;
import org.jupdater.data.DataManager;
import org.jupdater.gui.ContentPanel;
import org.jupdater.gui.DefaultPanel;
import org.jupdater.gui.OutWriter;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DefaultModule extends AbstractModule {
    protected void configure() {
        bind(Config.class).in(Singleton.class);
        bind(VersionLoader.class).in(Singleton.class);
        bind(DataManager.class).in(Singleton.class);
        bind(FileLoader.class).in(Singleton.class);
        bind(DefaultPanel.class).in(Singleton.class);
        bind(OutWriter.class).in(Singleton.class);
        bind(ContentPanel.class).in(Singleton.class);
    }
}
