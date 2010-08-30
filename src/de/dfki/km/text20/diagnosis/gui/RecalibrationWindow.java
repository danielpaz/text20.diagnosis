/*
 * RecalibrationWindow.java
 *
 * Copyright (c) 2010, Ralf Biedert, DFKI. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 *
 */
package de.dfki.km.text20.diagnosis.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.dfki.km.text20.diagnosis.gui.components.RecalibrationDisplay;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;
import de.dfki.km.text20.trackingserver.eyes.remote.TrackingCommand;
import de.dfki.km.text20.trackingserver.eyes.remote.options.sendcommand.OptionRecalibrationPattern;
import de.dfki.km.text20.util.filter.displacement.ReferencePoint;

/**
 * @author rb
 *
 */
public class RecalibrationWindow extends JFrame {
    /** */
    private static final long serialVersionUID = 6978556053913615337L;

    /** */
    final GraphicsDevice graphicsDevice;

    /** */
    final Logger logger = Logger.getLogger(this.getClass().getName());

    /** */
    final RecalibrationDisplay recalibrationDisplay = new RecalibrationDisplay(this);

    /** */
    ServerInfo serverInfo;

    /** */
    private boolean transparency;

    /** */
    BufferedImage backgroundImage = null;

    /**
     * @param serverInfo
     * @param applicationData
     *
     */
    public RecalibrationWindow(final ApplicationData applicationData, final ServerInfo serverInfo) {

        // Save vars
        this.serverInfo = serverInfo;

        // Get device
        this.graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        // Init GUI
        setResizable(false);
        setUndecorated(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        getContentPane().setLayout(new BorderLayout());

        // Init key handling
        addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    endRecalibration();
                }

                if (e.getKeyCode() == KeyEvent.VK_PLUS) {
                    final Color old = RecalibrationWindow.this.recalibrationDisplay.getBackground();
                    int value = old.getRed();
                    value = Math.min(value + 10, 255);

                    RecalibrationWindow.this.recalibrationDisplay.setBackground(new Color(value, value, value));
                }

                if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    final Color old = RecalibrationWindow.this.recalibrationDisplay.getBackground();
                    int value = old.getRed();
                    value = Math.max(value - 10, 0);

                    RecalibrationWindow.this.recalibrationDisplay.setBackground(new Color(value, value, value));
                }
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyTyped(final KeyEvent e) {
                // TODO Auto-generated method stub
            }
        });

        add(this.recalibrationDisplay, BorderLayout.CENTER);
    }

    /**
     * Ends teh current recalibraion
     */
    public void endRecalibration() {
        try {
            final OptionRecalibrationPattern orp = new OptionRecalibrationPattern();

            final List<ReferencePoint> referencePoints = RecalibrationWindow.this.recalibrationDisplay.getReferencePoints();
            for (final ReferencePoint r : referencePoints) {
                orp.addPoint(r.getPosition(), (int) r.getxDisplacement(), (int) r.getyDisplacement(), r.getTime());
            }

            final Thread backgroundTransmit = new Thread(new Runnable() {

                @Override
                public void run() {
                    RecalibrationWindow.this.logger.fine("Sending recalibration.");
                    getServerInfo().getEyeTrackingDevice().sendLowLevelCommand(TrackingCommand.ONLINE_RECALIBRATION, orp);
                    RecalibrationWindow.this.logger.fine("Recalibration sent.");
                }
            });
            backgroundTransmit.setDaemon(true);
            backgroundTransmit.start();

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        setVisible(false);
                        dispose();
                    } catch (final Exception e) {
                        //
                    }
                }
            });

        } catch (final RuntimeException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @return the serverInfo
     */
    public ServerInfo getServerInfo() {
        return this.serverInfo;
    }

    /**
     * @param evt
     */
    public void newEyeTrackingEvent(final EyeTrackingEvent evt) {
        if (isVisible()) {
            this.recalibrationDisplay.newTrackingEvent(evt);
        }
    }

    /**
     * @param serverInfo the serverInfo to set
     */
    public void setServerInfo(final ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /* (non-Javadoc)
     * @see java.awt.Window#setVisible(boolean)
     */
    @Override
    public void setVisible(final boolean b) {

        // Take screenshot before we change visibility
        try {
            final Robot robot = new Robot();
            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            this.backgroundImage = robot.createScreenCapture(new Rectangle(0, 0, screenSize.width, screenSize.height));
            System.out.println(this.backgroundImage);
            System.out.println(this.transparency);
            this.recalibrationDisplay.setBackgroundImage(this.backgroundImage);
            this.recalibrationDisplay.setTransparency(this.transparency);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bring us in front
        super.setVisible(b);

        if (b == true) {
            if (this.serverInfo == null || this.serverInfo.getEyeTrackingDevice() == null) return;

            // Drop recalib and go fullscreen.
            this.serverInfo.getEyeTrackingDevice().sendLowLevelCommand(TrackingCommand.DROP_RECALIBRATION);
            this.recalibrationDisplay.clearCalibraionInfo();
            this.graphicsDevice.setFullScreenWindow(this);
        } else {
            try {
                this.graphicsDevice.setFullScreenWindow(null);
                bringUsBack();
            } catch (final Exception e) {
                //e.printStackTrace();
            }
        }

    }

    /**
     * Hide everything else ...
     */
    public void bringUsBack() {
        this.serverInfo.getMainWindow().setState(Frame.NORMAL);
    }

    /**
     * @param selected
     */
    public void setTransparency(boolean selected) {
        this.transparency = selected;
    }

}
