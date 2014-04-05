package org.jupdater.gui;

import org.jupdater.core.Config;

import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DefaultPanel extends JFrame {
	private static final long serialVersionUID = 8253094830589266836L;
	//used to move window
    private Point mousePointMover;
    //components
    @Inject ContentPanel contentPanel;
    @Inject Config config;
    
    public DefaultPanel() {
    	this.setUndecorated(true);
    }
    
    public DefaultPanel initialize() {
        this.setResizable(false);
        //this.setUndecorated(true);

        //the program must always work to check releases
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //active transparent
        setBackground(new Color(0,0,0,0));

        //create window items
        this.contentPanel.initialize();
        
        //add the background
        ImageIcon image = new ImageIcon(config.getLocalBackgroundUrl());

        JLabel background = new JLabel(image);
        background.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        this.add(background);

        //adapting window to background size
        this.setBounds(background.getBounds().x, background.getBounds().y, background.getBounds().width, background.getBounds().height);
        
        //active window moving
        this.activeMoving();
        return this;
    }

    private void activeMoving() {
        //detect point selected
        addMouseListener(new MouseListener(){
            public void mouseReleased(MouseEvent e) {
                mousePointMover = null;
            }
            public void mousePressed(MouseEvent e) {
                mousePointMover = e.getPoint();
            }

            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
        });

        //motive the window to point location
        addMouseMotionListener(new MouseMotionListener(){
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mousePointMover.x, currCoords.y - mousePointMover.y);
                // contentPane.setLocation(getLocation());
            }

            public void mouseMoved(MouseEvent e) {}
        });
    }

    public ContentPanel getContentPanel() {
        return this.contentPanel;
    }
}
