/*
 * RecalibrationPanel.java
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
package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.options.getplugin.OptionCapabilities;
import de.dfki.km.text20.diagnosis.gui.RecalibrationWindow;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDevice;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDeviceProvider;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEventValidity;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingListener;
import de.dfki.km.text20.util.filter.AbstractFilter;
import de.dfki.km.text20.util.filter.ChainedFilter;
import de.dfki.km.text20.util.filter.centralpoint.SmoothingFilter;
import de.dfki.km.text20.util.filter.displacement.ReferenceBasedDisplacementFilter;
import de.dfki.km.text20.util.filter.displacement.ReferencePoint;

/**
 * Warning. This component should only be used fullscreen.
 * 
 * @author rb
 *
 */
public class RecalibrationDisplay extends AbstractEyeTrackingEventComponent {

    /**  */
    private static final long serialVersionUID = 9163930105068929634L;

    /** Master filter also contianing noise filter */
    final ChainedFilter globalFilter = new ChainedFilter();

    /** And we use that filter to smooth incoming events (so that we don't select displacements noisily) - dont put 
     * this in the chain! */
    final AbstractFilter filter = new SmoothingFilter(19);

    /** And that filter is used for displacemnts */
    final ReferenceBasedDisplacementFilter displacementFilter = new ReferenceBasedDisplacementFilter();

    /** */
    EyeTrackingEvent lastEvent;

    /** */
    EyeTrackingEvent lastUnfilteredEvent;

    /** */
    final RecalibrationWindow recalibrationWindow;

    private boolean transparency;

    private BufferedImage backgroundImage;

    /**
     * @param recalibrationWindow 
     * 
     */
    public RecalibrationDisplay(RecalibrationWindow recalibrationWindow) {
        this(recalibrationWindow, null, null);
    }

    /**
     * @param recalibrationWindow 
     * @param applicationData
     * @param serverInfo
     */
    public RecalibrationDisplay(final RecalibrationWindow recalibrationWindow,
                                final ApplicationData applicationData,
                                final ServerInfo serverInfo) {
        this.applicationData = applicationData;
        this.serverInfo = serverInfo;

        this.recalibrationWindow = recalibrationWindow;

        // Setup the internal filter
        this.globalFilter.addFilter(this.displacementFilter);

        initGUI();
    }

    /**
     * 
     */
    private void initGUI() {
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (RecalibrationDisplay.this.lastUnfilteredEvent == null) return;

                    final Point currentGazePoint = RecalibrationDisplay.this.lastUnfilteredEvent.getGazeCenter();
                    final Point clickPoint = e.getLocationOnScreen();

                    int dx = clickPoint.x - currentGazePoint.x;
                    int dy = clickPoint.y - currentGazePoint.y;

                    // Update the displacement filter
                    RecalibrationDisplay.this.displacementFilter.updateReferencePoint(currentGazePoint, dx, dy, System.currentTimeMillis());
                } else {
                    RecalibrationDisplay.this.recalibrationWindow.endRecalibration();
                }
            }
        });
        repaint();
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractEyeTrackingEventComponent#newEyeTrackingEvent(de.dfki.km.augmentedtext.services.trackingdevices.EyeTrackingEvent)
     */
    @Override
    public void newEyeTrackingEvent(EyeTrackingEvent evt) {
        boolean areValid = evt.areValid(EyeTrackingEventValidity.CENTER_POSITION_VALID);
        if(!areValid) return;
        Point c = evt.getGazeCenter();
        if(c.x <= 0 || c.y<=0) return;
        final EyeTrackingEvent filtered = this.filter.filterEvent(evt);
        this.lastUnfilteredEvent = filtered;
        this.lastEvent = this.globalFilter.filterEvent(filtered);
        repaint();
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractEyeTrackingEventComponent#render(java.awt.Graphics)
     */
    @Override
    public void render(Graphics g) {
        // Clear background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // Paint background
        if (this.transparency && this.backgroundImage != null) {
            g.drawImage(this.backgroundImage, 0, 0, null);
        }

        final EyeTrackingEvent event = this.lastEvent;

        if (event != null) {
            final Point center = event.getGazeCenter();

            // Draw the gaze center
            g.setColor(getForeground());
            g.fillOval(center.x - 3, center.y - 3, 6, 6);
        } else {
            g.setColor(Color.BLACK);
            g.drawString("Waiting for a tracking signal", getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    /**
     * Clears the last calibration information 
     */
    public void clearCalibraionInfo() {
        this.displacementFilter.clearReferencePoints();
    }

    /**
     * @return .
     */
    public List<ReferencePoint> getReferencePoints() {
        return this.displacementFilter.getReferencePoints();
    }

    /**
     * @param args
     * @throws URISyntaxException 
     */
    public static void main(String[] args) throws URISyntaxException {

        // Setup graphics device
        final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        final AtomicReference<RecalibrationDisplay> rc = new AtomicReference<RecalibrationDisplay>();

        // Initialize swing
        CommonFunctions.invokeAndWaitAndShutup(new Runnable() {
            @Override
            public void run() {

                final JFrame frame = new JFrame();
                final RecalibrationDisplay rd = new RecalibrationDisplay(null);

                frame.setResizable(false);
                frame.setUndecorated(true);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(rd, BorderLayout.CENTER);

                frame.addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            frame.dispose();
                        }
                    }
                });

                gd.setFullScreenWindow(frame);

                frame.setVisible(true);

                rc.set(rd);
            }
        });

        // Create plugin manager
        final PluginManager pluginManager = PluginManagerFactory.createPluginManager();
        pluginManager.addPluginsFrom(new URI("classpath://*"));

        // Obtain the proper tracking device here
        final EyeTrackingDeviceProvider deviceProvider = pluginManager.getPlugin(EyeTrackingDeviceProvider.class, new OptionCapabilities("eyetrackingdevice:trackingserver"));
        final EyeTrackingDevice openDevice = deviceProvider.openDevice("discover://youngest");

        // Listen to the flow of gaze ... 
        openDevice.addTrackingListener(new EyeTrackingListener() {
            @Override
            public void newTrackingEvent(final EyeTrackingEvent event) {
                rc.get().newEyeTrackingEvent(event);
            }
        });
    }

    /**
     * @param backgroundImage
     */
    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * @param transparency
     */
    public void setTransparency(boolean transparency) {
        this.transparency = transparency;
    }
}
