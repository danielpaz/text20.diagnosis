/*
 * EyeTrackingEventPanel.java
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

import de.dfki.km.text20.diagnosis.gui.components.util.AbstractRenderUtil;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.services.trackingdevices.common.TrackingEvent;

/**
 * Base class of all tracking event panels.
 *
 * @author Ralf Biedert, Andreas Buhl
 *
 */

public abstract class AbstractTrackingEventComponent extends JComponent {

    /** */
    private static final long serialVersionUID = -1963935140962471831L;

    /** */
    final static int DEFAULT_MARGIN_X = 20;

    /** */
    final static int DEFAULT_MARGIN_Y = 20;

    /** */
    final static int DEFAULT_PADDING = 5;

    /** */
    final int DEFAULT_PANEL_HEIGHT = 300;

    /** */
    final int DEFAULT_PANEL_WIDTH = 500;

    /** */
    final Color stateBadColor = Color.RED;

    /** */
    final Color stateOkColor = Color.GREEN;

    /** */
    final Color stateVagueColor = Color.YELLOW;

    // TODO: What for is
    /** Tracking event we use for visualization. */
    protected TrackingEvent trackingEvent;

    /** Tracking event history */
    protected TrackingEvent previousTrackingEvent;

    /** */
    ApplicationData applicationData;

    /** */
    ServerInfo serverInfo;

    /** */
    final AbstractRenderUtil renderer = new AbstractRenderUtil(this);

    /** */
    final Color stateColor[] = { Color.RED, Color.YELLOW, Color.GREEN };


    /** Default Constructor
     *
     * */
    public AbstractTrackingEventComponent() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public AbstractTrackingEventComponent(final ApplicationData applicationData, final ServerInfo serverInfo) {
        setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setPreferredSize(new Dimension(this.DEFAULT_PANEL_WIDTH, this.DEFAULT_PANEL_HEIGHT));
        this.applicationData = applicationData;
        this.serverInfo = serverInfo;
    }

    /**
     * Updates a new tracking event.
     *
     * @param evt
     */
    public void newTrackingEvent(final TrackingEvent evt) {
        this.previousTrackingEvent = this.trackingEvent;
        this.trackingEvent = evt;
        if (isVisible())
            repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isVisible()){
            render(g);
        }
    }

    /**
     * Can be overridden by subclasses to render.
     *
     * @param g
     */
    public abstract void render(Graphics g);


    // TODO: AAAAAAAAAAAHHHHHHHHHHHHHHHHHH. If the borders are set like above, i.e. 0,0,0,0, the getAreaWidth and getAreaHeight are useless...
    /**
     * @return the width of the drawable area
     */
    public int getAreaWidth() {
        return getWidth() - getInsets().left - getInsets().right;
    }

    /**
     * @return the height of the drawable area
     */
    public int getAreaHeight() {
        return getHeight() - getInsets().top - getInsets().bottom;
    }

    /**
     * @return the applicationData
     */
    public ApplicationData getApplicationData() {
        return this.applicationData;
    }

    /**
     * @param applicationData the applicationData to set
     */
    public void setApplicationData(final ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    /**
     * @return the serverInfo
     */
    public ServerInfo getServerInfo() {
        return this.serverInfo;
    }

    /**
     * @param serverInfo the serverInfo to set
     */
    public void setServerInfo(final ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
     */
    @Override
    public void setPreferredSize(final Dimension preferredSize) {
        final Dimension d = new Dimension();

        d.width = (preferredSize.width > 0) ? preferredSize.width : this.DEFAULT_PANEL_WIDTH;
        d.height = (preferredSize.height > 0) ? preferredSize.height : this.DEFAULT_PANEL_HEIGHT;

        super.setPreferredSize(d);
    }

}
