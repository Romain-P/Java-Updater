package org.jupdater.gui;

import com.google.inject.Inject;
import org.jupdater.core.Config;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContentPanel {
    //elements
    private JLabel outContainer;
    private JLabel closeButton;
    //config
    @Inject Config config;
    //panel
    @Inject DefaultPanel panel;

    public ContentPanel() {
        this.outContainer = new JLabel();
        this.closeButton = new JLabel();
    }

    public void initialize() {
        //output text container
        String pos[] = config.getLocalOutputContainerPosition().split(",");

        this.outContainer.setBounds(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), 300, 14);
        this.outContainer.setFont(new Font("Microsoft JhengHei", Font.PLAIN, config.getLocalOutputTextSize()));
        panel.add(outContainer);

        //button exit
        pos = config.getLocalCloseIconPosition().split(",");
        ImageIcon image = new ImageIcon(config.getLocalCloseIconUrl());
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
