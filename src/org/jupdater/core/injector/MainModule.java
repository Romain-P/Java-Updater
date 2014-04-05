/*
 * Created by IntelliJ IDEA.
 * User: Return
 * Date: 05/04/14
 * Time: 17:04
 */
package org.jupdater.core.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.jupdater.connection.FileLoader;
import org.jupdater.connection.VersionLoader;
import org.jupdater.core.Config;
import org.jupdater.data.DataManager;

public class MainModule extends AbstractModule {
    protected void configure() {
        bind(Config.class).in(Singleton.class);
        bind(VersionLoader.class).in(Singleton.class);
        bind(DataManager.class).in(Singleton.class);
        bind(FileLoader.class).in(Singleton.class);
    }
}
