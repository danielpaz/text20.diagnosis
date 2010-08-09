/*
 * TrackingStatusMiniPanel
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
import java.awt.Graphics;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * Displays the tracking server status in a minified version.  
 * 
 * @author Ralf Biedert
 *
 */
public class TrackingStatusMiniPanel extends AbstractTrackingEventComponent {
    /** */
    private static final long serialVersionUID = -5311489627085110951L;

    /**
     * @param applicationData
     * @param serverInfo
     */
    public TrackingStatusMiniPanel(final ApplicationData applicationData,
                                   final ServerInfo serverInfo) {
        super(applicationData, serverInfo);
    }

    @Override
    public void render(Graphics g) {
        final EyeTrackingEvent event = (EyeTrackingEvent) this.trackingEvent;

        if (event == null) return;

        final float[] headPosition = event.getHeadPosition();
        final int w = getWidth();
        final int h = getHeight();

        final float x = headPosition[0];
        final float y = headPosition[1];
        final float z = headPosition[2];

        g.setColor(Color.BLACK);

        g.drawLine(10, h / 2, w - 30, h / 2);
        g.drawLine((w - 30) / 2, 10, (w - 30) / 2, h - 10);
        g.drawLine(w - 15, 10, w - 15, h - 10);

        g.fillOval((int) (10 + x * (w - 40)) - 3, (int) (10 + y * (h - 20)) - 3, 5, 5);
        g.drawLine(w - 17, (int) (10 + z * (h - 20)), w - 13, (int) (10 + z * (h - 20)));
    }
}
