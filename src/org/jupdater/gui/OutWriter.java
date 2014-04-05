package org.jupdater.gui;

import javax.swing.*;

import com.google.inject.Inject;

import java.awt.*;

public class OutWriter {
	@Inject DefaultPanel panel;
	
    public void write(String text) {
        //send text
    	panel.getContentPanel()
               .getOutContainer().setText(text);
    }

    public void writeError(String text) {
        JLabel outContainer = panel.getContentPanel().getOutContainer();
        //draw in red
        outContainer.setForeground(Color.RED);

        //send text
        outContainer.setText(text);

        //redraw in black
        outContainer.setForeground(Color.BLACK);
    }
}
