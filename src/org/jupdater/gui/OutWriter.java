package org.jupdater.gui;

import javax.swing.*;
import java.awt.*;

public class OutWriter {
    public static void write(String text) {
        //send text
       DefaultPanel.getInstance().getContentPanel()
               .getOutContainer().setText(text);
    }

    public static void writeError(String text) {
        JLabel outContainer = DefaultPanel.getInstance().getContentPanel().getOutContainer();
        //draw in red
        outContainer.setForeground(Color.RED);

        //send text
        outContainer.setText(text);

        //redraw in black
        outContainer.setForeground(Color.BLACK);
    }
}
