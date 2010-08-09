/*
 * ServerInfo.java
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
package de.dfki.km.text20.diagnosis.model;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dfki.km.text20.diagnosis.util.BrainTrackingEventRingbuffer;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventRingbuffer;
import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingDevice;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDevice;

/**
 * @author rb
 *
 */
public class ServerInfo {
    /** Ring buffer with last tracking events for the eye tracker*/
    private final EyeTrackingEventRingbuffer eyeTrackingRingBuffer = new EyeTrackingEventRingbuffer();
    
    /** Ring buffer with last tracking events for the brain tracker*/
    private final BrainTrackingEventRingbuffer brainTrackingRingBuffer = new BrainTrackingEventRingbuffer();

    /** There is only one main window  */
    private JFrame mainWindow;

    /** */
    private final String uri;

    /** */
    private JPanel mainPanel;

    /** */
    private EyeTrackingDevice eyeTrackingDevice;

    /** */
    private BrainTrackingDevice brainTrackingDevice;

    
    /**
     * @param uri
     */
    public ServerInfo(final String uri) {
        this.uri = uri;
    }

    /**
     * @return the eyeTrackingRingBuffer
     */
    public EyeTrackingEventRingbuffer getEyeTrackingRingBuffer() {
        return this.eyeTrackingRingBuffer;
    }

    /**
     * @return the brainTrackingRingBuffer
     */
    public BrainTrackingEventRingbuffer getBrainTrackingRingBuffer() {
        return this.brainTrackingRingBuffer;
    }
    
    /**
     * @param mainWindow the mainWindow to set
     */
    public void setMainWindow(final JFrame mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * @return the mainWindow
     */
    public JFrame getMainWindow() {
        return this.mainWindow;
    }

    /**
     * @return the URI.
     */
    public String getURI() {
        return this.uri;
    }

    /**
     * @param panel
     */
    public void setMainWindow(final JPanel panel) {
        this.mainPanel = panel;
    }

    /**
     * @return the mainPanel
     */
    protected JPanel getMainPanel() {
        return this.mainPanel;
    }

    /**
     * @param device the deviceProvider to set
     */
    public void setEyeTrackingDevice(EyeTrackingDevice device) {
        this.eyeTrackingDevice = device;
    }

    /**
     * @return the deviceProvider
     */
    public EyeTrackingDevice getEyeTrackingDevice() {
        return this.eyeTrackingDevice;
    }
    
    /**
     * @param device the deviceProvider to set
     */
    public void setBrainTrackingDevice(BrainTrackingDevice device) {
        this.brainTrackingDevice = device;
    }

    /**
     * @return the deviceProvider
     */
    public BrainTrackingDevice getBrainTrackingDevice() {
        return this.brainTrackingDevice;
    }
}
