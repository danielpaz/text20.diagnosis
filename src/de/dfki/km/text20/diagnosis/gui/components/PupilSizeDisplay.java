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
import java.awt.Insets;

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
    private final static int DEFAULT_PUPILSIZE_CANVAS_WIDTH = 80;
    private final static int DEFAULT_PUPILSIZE_CANVAS_HEIGHT = -1;

    private Color pupilSizeCanvasBackgroundColor = Color.BLUE;
    private Color pupilSizBarColor = Color.CYAN;

    private int pupilSizeBarWidth = 8;

    private int paddingVal = 5;

    //    private final float lowerValidDistance = EyeTrackingEventEvaluator.EYE_DISTANCE_MIN;
    //    private final float upperValidDistance = EyeTrackingEventEvaluator.EYE_DISTANCE_MAX;

    /** horizontal position of the left eye icon */
    private int leftPupilIconXPos;

    /** horizontal position of the right eye icon */
    private int rightPupilIconXPos;

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
        this.setPreferredSize(new Dimension(DEFAULT_PUPILSIZE_CANVAS_WIDTH, DEFAULT_PUPILSIZE_CANVAS_HEIGHT));
        this.renderer.setPixelPerUnit(20);
    }

    /**
     * @param g
     */
    @SuppressWarnings( { "null", "boxing" })
    @Override
    public void render(final Graphics g) {

        final EyeTrackingEvent e = (EyeTrackingEvent) this.trackingEvent;
        if ((e == null) && (this.applicationData != null)) { return; }

        Insets insets;
        try {
            insets = getBorder().getBorderInsets(this);
        } catch (final RuntimeException e1) {
            insets = new Insets(0, 0, 0, 0);
        }
        
        final int middleXVal = Math.round((getAreaWidth() / 2 + insets.left));

        // draw canvas area 
        g.setColor(getPupilSizeCanvasBackgroundColor());
        g.fillRect(insets.left, insets.top, getAreaWidth(), getAreaHeight());

        // draw middle line
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(middleXVal, insets.top, middleXVal, getAreaHeight() + insets.top);

        // draw center tick
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(insets.left, Math.round(getAreaHeight() / 2 + insets.top), getAreaWidth() + insets.left, Math.round(getAreaHeight() / 2 + insets.top));

        this.leftPupilIconXPos = middleXVal - this.paddingVal - this.pupilSizeBarWidth;
        this.rightPupilIconXPos = middleXVal + this.paddingVal;

        if (this.applicationData == null) {
            int pupilBarPixelHeight;
            // draw eye icons
            g.setColor(getPupilSizBarColor());
            pupilBarPixelHeight = (Math.round(5 * this.renderer.getPixelPerUnit()));
            g.fillRect(this.leftPupilIconXPos, Math.round((getAreaHeight() - pupilBarPixelHeight) / 2) + getInsets().top, this.pupilSizeBarWidth, pupilBarPixelHeight);
            g.fillRect(this.rightPupilIconXPos, Math.round((getAreaHeight() - pupilBarPixelHeight) / 2) + getInsets().top, this.pupilSizeBarWidth, pupilBarPixelHeight);
            this.renderer.drawCenteredString(g, insets.left + this.paddingVal, middleXVal, getHeight() - insets.bottom - 20,"3.5" );
            this.renderer.drawCenteredString(g, middleXVal, insets.left + getAreaWidth(), getHeight() - insets.bottom - 20,"3.5" );
//            g.drawString("3.5", middleXVal + this.paddingVal + calcNumericLabelInset(), getHeight() - insets.bottom - 20);
            return;
        }

        int pupilBarPixelHeight;
        final float leftSize = CommonFunctions.limitFloat(e.getPupilSizeLeft(),0.0f,8.0f);
        final float rightSize = CommonFunctions.limitFloat(e.getPupilSizeRight(),0.0f,8.0f);
        // draw eye icons
        g.setColor(getPupilSizBarColor());
        pupilBarPixelHeight = this.renderer.getLeftPupilPixelSize(leftSize);
        g.fillRect(this.leftPupilIconXPos, Math.round((getAreaHeight() - pupilBarPixelHeight) / 2) + getInsets().top, this.pupilSizeBarWidth, pupilBarPixelHeight);

        pupilBarPixelHeight = this.renderer.getRightPupilPixelSize(rightSize);
        g.fillRect(this.rightPupilIconXPos, Math.round((getAreaHeight() - pupilBarPixelHeight) / 2) + getInsets().top, this.pupilSizeBarWidth, pupilBarPixelHeight);

        // draw numeric output       
        this.renderer.drawCenteredString(g, insets.left + this.paddingVal, middleXVal, getHeight() - insets.bottom - 20,String.format("%.1f", leftSize));
        this.renderer.drawCenteredString(g, middleXVal, insets.left + getAreaWidth(), getHeight() - insets.bottom - 20,String.format("%.1f", rightSize));

    }

    /**
     * @return the paddingVal
     */
    public int getPaddingVal() {
        return this.paddingVal;
    }

    /**
     * @param paddingVal the paddingVal to set
     */
    public void setPaddingVal(final int paddingVal) {
        this.paddingVal = paddingVal;
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

    /**
     * @return the pupilSizeBarWidth
     */
    public int getPupilSizeBarWidth() {
        return this.pupilSizeBarWidth;
    }

    /**
     * @param pupilSizeBarWidth the pupilSizeBarWidth to set
     */
    public void setPupilSizeBarWidth(final int pupilSizeBarWidth) {
        this.pupilSizeBarWidth = pupilSizeBarWidth;
    }

    /**
     * @return the pupilSizBarColor
     */
    public Color getPupilSizBarColor() {
        return this.pupilSizBarColor;
    }

    /**
     * @param pupilSizBarColor the pupilSizBarColor to set
     */
    public void setPupilSizBarColor(final Color pupilSizBarColor) {
        this.pupilSizBarColor = pupilSizBarColor;
    }
}
