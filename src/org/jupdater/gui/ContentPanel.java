package org.jupdater.gui;

import org.jupdater.core.Config;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContentPanel {
    //the default panel
    private DefaultPanel panel;
    //elements
    private JLabel outContainer;
    private JLabel closeButton;

    public ContentPanel(DefaultPanel panel) {
        this.panel = panel;
        this.outContainer = new JLabel();
        this.closeButton = new JLabel();
    }

    public void initialize() {
        //output text container
        String pos[] = Config.getInstance().getLocalOutputContainerPosition().split(",");

        this.outContainer.setBounds(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), 300, 14);
        this.outContainer.setFont(new Font("Microsoft JhengHei", Font.PLAIN, Config.getInstance().getLocalOutputTextSize()));
        panel.add(outContainer);

        //button exit
        pos = Config.getInstance().getLocalCloseIconPosition().split(",");
        ImageIcon image = new ImageIcon(Config.getInstance().getLocalCloseIconUrl());
        closeButton.setIcon(image);
        
        closeButton.setBounds(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), image.getIconWidth(), image.getIconHeight());
        panel.add(closeButton);
        //active the exit button
        activeExitListener();
    }

    public void activeExitListener() {
        this.closeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                panel.setState(Frame.ICONIFIED);
            }
        });
    }

    public JLabel getOutContainer() {
        return this.outContainer;
    }
}
