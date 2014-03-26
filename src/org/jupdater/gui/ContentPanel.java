package org.jupdater.gui;

import org.jupdater.core.Config;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContentPanel {
    //the default panel
    private DefaultPanel panel;
    //elements
    private JLabel outContainer;
    private JButton closeButton;

    public ContentPanel(DefaultPanel panel) {
        this.panel = panel;
        this.outContainer = new JLabel("Initialize");
        this.closeButton = new JButton();
    }

    public void initialize() {
        //output text container
        String pos[] = Config.getInstance().getLocalOutputContainerPosition().split(",");

        this.outContainer.setBounds(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), panel.getWidth(), 14);
        panel.add(outContainer);

        //button exit
        pos = Config.getInstance().getLocalCloseIconPosition().split(",");
        ImageIcon image = new ImageIcon(Config.getInstance().getLocalCloseIconUrl());

        closeButton.setBounds(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), image.getIconWidth(), image.getIconHeight());
        panel.add(closeButton);

        //active the exit button
        activeExitListener();
    }

    public void activeExitListener() {
        this.closeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                //TODO run app in background
            }
        });
    }

    public JLabel getOutContainer() {
        return this.outContainer;
    }
}
