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
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DiagState;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * Displays the eye-distance inside a black box.
 *
 * @author Nathaniel Egwu, ABu
 *
 */
public class EyeDistanceDisplay extends AbstractTrackingEventComponent {

    /** */
    private static final long serialVersionUID = -8142292583304142828L;

    /** */
    private final int defaultEyedistanceCanvasWidth = 100;

    /** */
    private final int defaultEyedistanceCanvasHeight = 200;

    /** */
    private final float lowerValidDistance = EyeTrackingEventEvaluator.EYE_DISTANCE_MIN;

    /** */
    private final float upperValidDistance = EyeTrackingEventEvaluator.EYE_DISTANCE_MAX;

    /** */
    private final int eyeIconHeight = 7;

    /** */
    private final int eyeIconWidth = 20;

    /** */
    private int paddingVal = 3;

    /** horizontal position of the left eye icon */
    private int leftEyeXPos;

    /** horizontal position of the right eye icon */
    private int rightEyeXPos;

    /** */
    private Color eyeDistanceCanvasColor = Color.BLACK;

    /** */
    private Color validRectColor = Color.RED;

    /**
     * Default Constructor;
     * initializes Panel with default Values
     * */
    public EyeDistanceDisplay() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public EyeDistanceDisplay(final ApplicationData applicationData, final ServerInfo serverInfo) {
        super(applicationData, serverInfo);

        setBackground(Color.BLUE);
        this.setPreferredSize(new Dimension(this.defaultEyedistanceCanvasWidth, this.defaultEyedistanceCanvasHeight));
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

        // Draw canvas area
        g.setColor(this.eyeDistanceCanvasColor);
        g.fillRect(leftBorder, topBorder, areaWidth, areaHeight);

        // get and calculate scale parameters
        final int middleXPosition = Math.round((areaWidth / 2 + leftBorder));
        this.leftEyeXPos = middleXPosition - this.eyeIconWidth - this.paddingVal;
        this.rightEyeXPos = middleXPosition + this.paddingVal;
        final float offset = this.lowerValidDistance - (1.0f - this.upperValidDistance  + this.lowerValidDistance) /2;

        // draw middle line
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(middleXPosition, topBorder, middleXPosition, areaHeight+ topBorder);

        //draw valid range indicator rectangle
        g.setColor(this.validRectColor);

        g.drawRect(leftBorder + this.paddingVal, topBorder + Math.round((this.lowerValidDistance - offset) * areaHeight),
                   areaWidth - this.paddingVal * 2, Math.round((this.upperValidDistance - this.lowerValidDistance) * areaHeight));


        if (this.applicationData == null){
            g.setColor(this.stateColor[DiagState.OK.ordinal()]);

            g.fillRect(this.leftEyeXPos, Math.round((0.5f - offset) * areaHeight) + topBorder, this.eyeIconWidth, this.eyeIconHeight);
            g.fillRect(this.rightEyeXPos, Math.round((0.5f - offset) * areaHeight) + topBorder, this.eyeIconWidth, this.eyeIconHeight);

            g.setColor(this.stateColor[DiagState.OK.ordinal()]);
            this.renderer.drawCenteredString(g, leftBorder + this.paddingVal, middleXPosition, areaHeight - 20, "55");
            this.renderer.drawCenteredString(g, middleXPosition, leftBorder + areaWidth, areaHeight - 20, "123");

            return;
        }

        // TODO: Why can be event.getLeftEyePosition be null, but event.getRightEyePosition not?
        @SuppressWarnings("null")
        final float leftEyeDistance = CommonFunctions.limitFloat(event.getLeftEyePosition()[2]);
        final float rightEyeDistance = CommonFunctions.limitFloat(event.getRightEyePosition()[2]);

        final DiagState diagnosisState = EyeTrackingEventEvaluator.evaluateEvent(event);

        Color iconColor;
        switch (diagnosisState) {
            case BAD:
                iconColor = this.stateBadColor;
                break;
            case OK:
                iconColor = this.stateOkColor;
                break;
            case VAGUE:
                iconColor = this.stateVagueColor;
                break;
            default:
                iconColor = this.stateVagueColor;
                break;
        }


        // draw eye icons and numeric output
        g.setColor(iconColor);

        final String sLeft = String.format("%.0f", new Float(CommonFunctions.limitFloat(leftEyeDistance, 0.0f, 1.0f) * 100));
        this.renderer.drawCenteredString (g, leftBorder + this.paddingVal, middleXPosition, areaHeight - 20, (leftEyeDistance >= 0) ? sLeft : "- -");

        if (leftEyeDistance >= 0) {
            g.fillRect(this.leftEyeXPos, Math.round((leftEyeDistance - offset) * areaHeight) + topBorder , this.eyeIconWidth, this.eyeIconHeight);
        }

        final String sRight = String.format("%.0f", new Float(CommonFunctions.limitFloat(rightEyeDistance, 0.0f, 1.0f) * 100));
        this.renderer.drawCenteredString (g, middleXPosition, leftBorder + areaWidth, areaHeight - 20, (rightEyeDistance >= 0) ? sRight : "- -");

        if (rightEyeDistance >= 0) {
            g.fillRect(this.rightEyeXPos, Math.round((rightEyeDistance - offset) * areaHeight) + topBorder, this.eyeIconWidth, this.eyeIconHeight);
        }
    }

    /**
     * @return the eyeDistanceCanvasColor
     */
    public Color getEyeDistanceCanvasColor() {
        return this.eyeDistanceCanvasColor;
    }

    /**
     * @param eyeDistanceCanvasColor the eyeDistanceCanvasColor to set
     */
    public void setEyeDistanceCanvasColor(final Color eyeDistanceCanvasColor) {
        this.eyeDistanceCanvasColor = eyeDistanceCanvasColor;
    }

    /**
     * @return the validRectColor
     */
    public Color getValidRectColor() {
        return this.validRectColor;
    }

    /**
     * @param validRectColor the validRectColor to set
     */
    public void setValidRectColor(final Color validRectColor) {
        this.validRectColor = validRectColor;
    }

    /**
     * @return
     */
    public int getPaddingVal() {
        return this.paddingVal;
    }

    /**
     * @param paddingVal
     */
    public void setPaddingVal(final int paddingVal) {
        this.paddingVal = paddingVal;
    }
}
