/*
 * EyeDistancePanel.java
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

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * Displays the pupil size inside a black box.
 *
 * @author Nathaniel Egwu, ABu
 *
 */
public class PupilSizeDisplay extends AbstractTrackingEventComponent {

    /** */
    private static final long serialVersionUID = -8142292583304142828L;

    /** */
    private final int defaultPupilsizeCanvasWidth = 80;

    /** */
    private final int defaultPupilsizeCanvasHeight = -1;

    /** */
    private final int pupilSizeBarWidth = 8;

    /** */
    private final int paddingVal = 5;

    /** */
    private Color pupilSizeCanvasBackgroundColor = Color.BLUE;

    /** */
    private final Color pupilSizBarColor = Color.CYAN;


    /**
     * Default Constructor;
     * initializes Panel with default Values
    */
    public PupilSizeDisplay() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public PupilSizeDisplay(final ApplicationData applicationData,
                            final ServerInfo serverInfo) {
        super(applicationData, serverInfo);

        this.setPreferredSize(new Dimension(this.defaultPupilsizeCanvasWidth, this.defaultPupilsizeCanvasHeight));
        this.renderer.setPixelPerUnit(20);
    }

    /**
     * @param g
     */
    @Override
    public void render(final Graphics g) {
        final EyeTrackingEvent event = (EyeTrackingEvent) this.trackingEvent;
        if ((event == null) && (this.applicationData != null)) {
            return;
        }

        final int leftBorder = getInsets().left;
        final int topBorder = getInsets().top;
        final int areaWidth = getAreaWidth();
        final int areaHeight = getAreaHeight();

        final int middleXVal = Math.round(areaWidth / 2 + leftBorder);
        final int middleYVal = Math.round(areaHeight / 2 + topBorder);

        // draw canvas area
        g.setColor(this.pupilSizeCanvasBackgroundColor);
        g.fillRect(leftBorder, topBorder, areaWidth, areaHeight);

        // draw middle line and center tick
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(middleXVal, topBorder, middleXVal, areaHeight);
        g.drawLine(leftBorder, middleYVal, areaWidth, middleYVal);

        // Horizontal position of the left and right eye icon
        final int leftPupilIconXPos = middleXVal - this.paddingVal - this.pupilSizeBarWidth;
        final int rightPupilIconXPos = middleXVal + this.paddingVal;

        if (this.applicationData == null) {
            final int pupilBarPixelHeight = Math.round(5 * this.renderer.getPixelPerUnit());

            // Draw eye icons
            g.setColor(this.pupilSizBarColor);
            g.fillRect(leftPupilIconXPos, Math.round((areaHeight - pupilBarPixelHeight) / 2) + topBorder, this.pupilSizeBarWidth, pupilBarPixelHeight);
            g.fillRect(rightPupilIconXPos, Math.round((areaHeight - pupilBarPixelHeight) / 2) + topBorder, this.pupilSizeBarWidth, pupilBarPixelHeight);

            // Draw numeric output
            this.renderer.drawCenteredString(g, leftBorder + this.paddingVal, middleXVal, areaHeight - 20, "3.5");
            this.renderer.drawCenteredString(g, middleXVal, leftBorder + areaWidth, areaHeight - 20, "3.5");

            return;
        }

        // TODO: Why can be event.getPupilSizeLeft be null, but event.getPupilSizeRight not?
        @SuppressWarnings("null")
        final float leftSize = CommonFunctions.limitFloat(event.getPupilSizeLeft(), 0.0f, 8.0f);
        final float rightSize = CommonFunctions.limitFloat(event.getPupilSizeRight(), 0.0f, 8.0f);

        // Draw left eye icons
        g.setColor(this.pupilSizBarColor);
        final int leftPupilBarPixelHeight = this.renderer.getLeftPupilPixelSize(leftSize);
        g.fillRect(leftPupilIconXPos, Math.round((areaHeight - leftPupilBarPixelHeight) / 2) + topBorder, this.pupilSizeBarWidth, leftPupilBarPixelHeight);

        // Draw right eye icons
        final int rightPupilBarPixelHeight = this.renderer.getRightPupilPixelSize(rightSize);
        g.fillRect(rightPupilIconXPos, Math.round((areaHeight - rightPupilBarPixelHeight) / 2) + topBorder, this.pupilSizeBarWidth, rightPupilBarPixelHeight);

        // Draw numeric output
        this.renderer.drawCenteredString(g, leftBorder + this.paddingVal, middleXVal, areaHeight - 20, String.format("%.1f", new Float(leftSize)));
        this.renderer.drawCenteredString(g, middleXVal, areaWidth, areaHeight - 20, String.format("%.1f", new Float(rightSize)));
    }

    /**
     * @return the pupilSizeCanvasBackgroundColor
     */
    public Color getPupilSizeCanvasBackgroundColor() {
        return this.pupilSizeCanvasBackgroundColor;
    }

    /**
     * @param pupilSizeCanvasBackgroundColor the pupilSizeCanvasBackgroundColor to set
     */
    public void setPupilSizeCanvasBackgroundColor(final Color pupilSizeCanvasBackgroundColor) {
        this.pupilSizeCanvasBackgroundColor = pupilSizeCanvasBackgroundColor;
    }
}
