/*
 * ServerWindow.java
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
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.options.getplugin.OptionCapabilities;
import de.dfki.km.text20.diagnosis.gui.panel.ServerPanel;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.BrainTrackingEventRingbuffer;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventRingbuffer;
import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingDevice;
import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingDeviceProvider;
import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingEvent;
import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingListener;
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
    EyeTrackingEventRingbuffer eyeTrackingRingBuffer;
    
    /** */
    BrainTrackingEventRingbuffer brainTrackingRingBuffer;

    /** */
    ServerPanel serverPanel;

    /** */
    private EyeTrackingDevice openEyeTrackingDevice;
    
    /** */
    private BrainTrackingDevice openBrainTrackingDevice;
    
    /** */
    final Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * @param applicationData
     * @param serverInfo 
     */
    public ServerWindow(final ApplicationData applicationData, final ServerInfo serverInfo) {
        super();
        
        this.applicationData = applicationData;
        this.serverInfo = serverInfo;
        this.serverInfo.setMainWindow(this);
        
        // Getting ring buffers
        this.eyeTrackingRingBuffer = this.serverInfo.getEyeTrackingRingBuffer();
        this.brainTrackingRingBuffer = this.serverInfo.getBrainTrackingRingBuffer();

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
        
        // Getting eye and brain tracking device
        final EyeTrackingDeviceProvider eyeTrackingDeviceProvider = pm.getPlugin(EyeTrackingDeviceProvider.class, new OptionCapabilities("eyetrackingdevice:trackingserver"));
        final BrainTrackingDeviceProvider brainTrackingDeviceProvider = pm.getPlugin(BrainTrackingDeviceProvider.class, new OptionCapabilities("braintrackingdevice:trackingserver"));
        
        // Opening devices
        this.openEyeTrackingDevice = eyeTrackingDeviceProvider.openDevice(this.serverInfo.getURI());
        this.openBrainTrackingDevice = brainTrackingDeviceProvider.openDevice(this.serverInfo.getURI());
        
        // Shutting down if no device is found at all
        if (this.openEyeTrackingDevice == null && this.openBrainTrackingDevice == null) {
            JOptionPane.showMessageDialog(null, "Please make sure you start the Tracking Sever before " +
            		"running the diagnosis.", "No Tracking Server Found", JOptionPane.ERROR_MESSAGE);
            shutdown();
            return;
        }
        
        // Opening eye tracking device
        if (this.openEyeTrackingDevice != null) {
            this.logger.info("Eye tracking device found");
            
            setupWithEyeTrackingDevice(this.openEyeTrackingDevice);
        } else {
            this.logger.warning("No eye tracking device found");
        }
        
        // Opening brain tracking device
        if (this.openBrainTrackingDevice != null) {
            this.logger.info("Brain tracking device found");
            
            setupWithBrainTrackingDevice(this.openBrainTrackingDevice);
        } else {
            this.logger.warning("No brain tracking device found");
        }
        
    }

    /**
     * Uses the given eye traking device to set up connections
     * @param device
     */
    private void setupWithEyeTrackingDevice(final EyeTrackingDevice device) {
        this.serverInfo.setEyeTrackingDevice(device);
        this.eyeTrackingRingBuffer.setRingbufferSize(RINGBUFFER_SIZE);
        this.eyeTrackingRingBuffer.restartRecording();

        
        device.addTrackingListener(new EyeTrackingListener() {
            @Override
            public void newTrackingEvent(final EyeTrackingEvent event) {
                try {
                    ServerWindow.this.eyeTrackingRingBuffer.addEvent(event);

                    if (ServerWindow.this.serverPanel != null) {
                        ServerWindow.this.serverPanel.newTrackingEvent(event);
                    }
                } catch (final Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Uses the given brain tracking device to set up connections
     * @param device
     */
    private void setupWithBrainTrackingDevice(final BrainTrackingDevice device) {
        this.serverInfo.setBrainTrackingDevice(device);
        this.brainTrackingRingBuffer.setRingbufferSize(RINGBUFFER_SIZE);
        this.brainTrackingRingBuffer.restartRecording();

        
        device.addTrackingListener(new BrainTrackingListener() {
            @Override
            public void newTrackingEvent(final BrainTrackingEvent event) {
                try {
                    ServerWindow.this.brainTrackingRingBuffer.addEvent(event);

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
        setTitle("Eye-/BrainTracker Diagnosis on " + this.serverInfo.getURI());
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
     * @return the openEyeTrackingDevice
     */
    public EyeTrackingDevice getOpenDevice() {
        return this.openEyeTrackingDevice;
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
			e.printStackTrace();
		}
		
    	this.openEyeTrackingDevice = null;
    	this.applicationData = null;
    	this.serverInfo = null;
    	this.serverPanel = null;
        super.dispose();
    }


}
