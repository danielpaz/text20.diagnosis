/*
 * MainWindow.java
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
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.options.getplugin.OptionCapabilities;
import de.dfki.km.text20.diagnosis.gui.panel.ServerPanel;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventRingbuffer;
import de.dfki.km.text20.services.trackingdevices.common.TrackingDevice;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDevice;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDeviceProvider;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingListener;

/**
 * Contains all other widgets related to one server.
 * 
 * @author Andreas Buhl
 * @author Ralf Biedert
 * @author Nathaniel Egwu.
 */
public class ServerWindow extends JFrame {

    /** */
    private static final long serialVersionUID = 5780492510784824649L;

    /** */
    private static final int RINGBUFFER_SIZE = 600;

    /** */
    ApplicationData applicationData;

    /** */
    ServerInfo serverInfo;

    /** */
    EyeTrackingEventRingbuffer ringBuffer;

    /** */
    ServerPanel serverPanel;

    /** */
    private EyeTrackingDevice openDevice;
    

    /**
     * @param applicationData
     * @param serverInfo 
     */
    public ServerWindow(final ApplicationData applicationData, final ServerInfo serverInfo) {
        super();
        
        this.applicationData = applicationData;
        this.serverInfo = serverInfo;
        this.serverInfo.setMainWindow(this);
        this.ringBuffer = this.serverInfo.getRingBuffer();

        //TODO reactivate Tray ??   
        //  applicationData.setTray(new ServerDiagnosisSystemTray(applicationData));
        
        initConnections();
        initGUI();
    }

    /**
     * Initializes the connection to an eye tracking device.
     */
    private void initConnections() {

        // TODO: How do we handle multiple connections?
        final PluginManager pm = this.applicationData.getPluginManager();
        final EyeTrackingDeviceProvider deviceProvider = pm.getPlugin(EyeTrackingDeviceProvider.class, new OptionCapabilities("eyetrackingdevice:trackingserver"));
        this.openDevice = deviceProvider.openDevice(this.serverInfo.getURI());
        if (this.openDevice == null) {
            System.err.println("Error obtaining a tracking device");
        }
        setupWithTrackingDevice(this.openDevice);
    }

    /**
     * Uses the given device to set up connections
     * @param device
     */
    private void setupWithTrackingDevice(final EyeTrackingDevice device) {
        if (device == null) {
            System.out.println("no device; application exit");          
            shutdown();
            return;
        }
        
        this.serverInfo.setDevice(device);
        this.ringBuffer.setRingbufferSize(RINGBUFFER_SIZE);
        this.ringBuffer.restartRecording();

        
        device.addTrackingListener(new EyeTrackingListener() {
            public void newTrackingEvent(final EyeTrackingEvent event) {
                try {
                    ServerWindow.this.ringBuffer.addEvent(event);

                    if (ServerWindow.this.serverPanel != null) {
                        ServerWindow.this.serverPanel.newTrackingEvent(event);
                    }
                } catch (final Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    /**  */
    private void initGUI() {

        setTitle("EyeTracker Diagnosis on " + this.serverInfo.getURI());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // The main panel of this frame
        this.serverPanel = new ServerPanel(this.applicationData, this.serverInfo);

        // Pack 
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(this.serverPanel, BorderLayout.CENTER);
        pack();
        this.setVisible(true);
    }

    /**
     * Brings the window to the screen
     */
    public void showOnScreen() {
        this.setVisible(true);
    }

    /** */
    public void shutdown() {
        this.applicationData.getPluginManager().shutdown();
        System.exit(0);
    }

    /**
     * @return the openDevice
     */
    public TrackingDevice getOpenDevice() {
        return this.openDevice;
    }

    /**
     * @return the serverInfo
     */
    public ServerInfo getServerInfo() {
        return this.serverInfo;
    }

    /* (non-Javadoc)
     * @see java.awt.Window#dispose()
     */
    @Override
    public void dispose() {
    	try {
			getOpenDevice().closeDevice();
		} catch (Exception e) {
			//
		}
    	this.openDevice = null;
    	this.applicationData = null;
    	this.serverInfo = null;
    	this.serverPanel = null;
        super.dispose();
    }


}
