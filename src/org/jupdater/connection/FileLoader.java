package org.jupdater.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.inject.Inject;
import org.jupdater.core.Config;
import org.jupdater.data.DataManager;
import org.jupdater.gui.DefaultPanel;
import org.jupdater.gui.OutWriter;

public class FileLoader {
    //Threads
    private ExecutorService threadWorker = Executors.newSingleThreadExecutor();
    private ScheduledExecutorService timeWorker = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> timerTask;
    //guice
    @Inject Config config;
    @Inject VersionLoader loader;
    @Inject DataManager manager;
    @Inject DefaultPanel panel;
    @Inject OutWriter writer;

    public void launchUpdate() {
        //check if folder has the last release
        if(!needUpdate(false))
            return;
        else 
        	panel.initialize().setVisible(true);
        
        
        //starting update
        writer.write("You will be updated to " + config.getVersionData());
        threadWorker.execute(new Runnable() {
        	@Override
        	public void run() {
	            String sReleases = config.getRequiredReleases();
	            sReleases = sReleases.contains(",") ? sReleases : sReleases + ",";
	
	            writer.write("Downloading required old releases..");
	
	            for(String release: sReleases.split(",")) {
	                if(config.getInstalledReleases().contains(release))
	                    continue;
	                writer.write("Downloading release " + release + "..");
	                ZipFile required = loadDistantRelease(release);
	                writer.write("Extract release " + release + "..");
	                updateFolder(required);
	                writer.write("Release " + release + " downloaded success !");
	
	                //add to local data
	                manager.updateData(release);
	            }
	
	            writer.write("Old releases downloaded success !");
	
	            writer.write("Downloading the last release..");
	            ZipFile lastRelease = loadDistantRelease(config.getVersionData());
	            updateFolder(lastRelease);
	
	            //add to local data
	            manager.updateData(config.getVersionData());
	
	            //starting requiredFile
	            if(config.isLaunchRequiredFileAfterUpdate())
	                launchRequiredFile();
	
	            writer.write("Update to version " + config.getVersionData()+" finished");
	            panel.setVisible(false);
	            launchCheckTimer();
        	}
        });
    }

    private void updateFolder(ZipFile zipFile) {
        try {
            Enumeration<?> list = zipFile.entries();

            byte[] buffer = new byte[512*1024];

            while(list.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)list.nextElement();
                File futureFile = new File(entry.getName());

                if(futureFile.getParentFile() != null)
                    futureFile.getParentFile().mkdirs();

                if(entry.isDirectory())
                    continue;
                else if (!futureFile.exists() || entry.getSize() != futureFile.length()) {
                    writer.write("Extracting " + futureFile);

                    BufferedInputStream input = new BufferedInputStream(zipFile.getInputStream(entry));
                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(futureFile), 512*1024);

                    int count;
                    while ((count = input.read(buffer, 0, 512*1024)) != -1)
                        output.write(buffer, 0, count);

                    output.flush();
                    output.close();
                    input.close();

                    futureFile.setLastModified(entry.getTime());
                }
            }
            zipFile.close();
        } catch(Exception e) {
            writer.writeError("An error was found :" + e.getMessage());
        }
    }

    private ZipFile loadDistantRelease(String release) {
        try {
            byte[] buffer = new byte[512*1024];
            URLConnection connection = new URL(config.getVersionPath()+release+".zip").openConnection();

            BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
            File distantFile = File.createTempFile("zips", ".zip");
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(distantFile));

            writer.write("Loading " + release +" package..");
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1)
                output.write(buffer, 0, bytesRead);

            input.close();
            output.close();

            writer.write("Extract in progress..");

            return new ZipFile(distantFile);
        } catch (Exception e) {
            writer.writeError("Please check your internet connection, failed to load  " +
                    config.getVersionPath() + " : " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private boolean needUpdate(boolean launchProgram) {
        if(config.getInstalledReleases().contains(config.getVersionData())) {
            if(launchProgram && config.isLaunchRequiredFileAfterUpdate())
                launchRequiredFile();
            return false;
        }
        panel.setVisible(true);
        return true;
    }

    private void launchRequiredFile() {
        String program = config.getRequiredFile();
        try {
            Runtime.getRuntime().exec(config.getRequiredFile());
        } catch(Exception e) {
            writer.writeError("Can't start "+program+": "+e.getMessage());
        }
    }
    
    private void launchCheckTimer() {
    	if(timerTask != null)
    		return;
    	timerTask = this.timeWorker.scheduleWithFixedDelay(new Runnable() {
    		public void run() {
    			if(loader.newReleaseAvailable()) {
    				//reload configuration
    				loader.initializeVersion();
    				launchUpdate();
    			}
    		}
    	}, 1, 1, TimeUnit.MINUTES);
    }
}
