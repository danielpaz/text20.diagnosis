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
    private final static int DEFAULT_EYEDISTANCE_CANVAS_WIDTH = 100;
    private final static int DEFAULT_EYEDISTANCE_CANVAS_HEIGHT = 200;

    private static final Color DEFAULT_EYEDISTANCE_CANVAS_BACKGROUNDCOLOR = Color.BLUE;

    private int eyeIconHeight = 7;
    private int eyeIconWidth = 20;
    private int paddingVal = 3;

    private final float lowerValidDistance = EyeTrackingEventEvaluator.EYE_DISTANCE_MIN;
    private final float upperValidDistance = EyeTrackingEventEvaluator.EYE_DISTANCE_MAX;

    /** horizontal position of the left eye icon */
    private int leftEyeXPos;

    /** horizontal position of the right eye icon */
    private int rightEyeXPos;

    private Color eyeDistanceCanvasColor = Color.BLACK;
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
        setBackground(DEFAULT_EYEDISTANCE_CANVAS_BACKGROUNDCOLOR);
        this.setPreferredSize(new Dimension(DEFAULT_EYEDISTANCE_CANVAS_WIDTH, DEFAULT_EYEDISTANCE_CANVAS_HEIGHT));
    }

    /**
     * @param g
     */
    @SuppressWarnings({ "null", "boxing" })
    @Override
    public void render(final Graphics g) {

//      final EyeTrackingEvent e = this.EyeTrackingEvent;
        final EyeTrackingEvent e = (EyeTrackingEvent) this.trackingEvent;

        if ((e == null)  && (this.applicationData != null)) { return; }

        Insets insets;
        try {
            insets = getBorder().getBorderInsets(this);
        } catch (final RuntimeException e1) {
             insets = new Insets(0,0,0,0);
        }
        // draw canvas area 
        g.setColor(getEyeDistanceCanvasColor());
        g.fillRect(insets.left, insets.top, getAreaWidth(), getAreaHeight());

        // get and calculate scale parameters
        final int middleXVal = Math.round((getAreaWidth()/ 2 + insets.left));
        this.leftEyeXPos = middleXVal - this.eyeIconWidth - this.paddingVal;
        this.rightEyeXPos = middleXVal + this.paddingVal;
        final float offset = this.lowerValidDistance - (1.0f - this.upperValidDistance  + this.lowerValidDistance) /2;

        // draw middle line
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(middleXVal, insets.top, middleXVal, getAreaHeight()+ insets.top);

        //draw valid range indicator rectangle
        g.setColor(this.validRectColor);

        g.drawRect(insets.left + getPaddingVal(), 
                   insets.top + Math.round((this.lowerValidDistance - offset)* getAreaHeight()), 
                   getWidth() - insets.left - insets.right - getPaddingVal() * 2, 
                   Math.round((this.upperValidDistance - this.lowerValidDistance) * getAreaHeight()));


        if (this.applicationData == null){
            g.setColor(this.stateColor[DiagState.OK.ordinal()]);
            g.fillRect(this.leftEyeXPos, Math.round((0.5f - offset) * getAreaHeight()) + getInsets().top , this.eyeIconWidth, this.eyeIconHeight);
            g.fillRect(this.rightEyeXPos, Math.round((0.5f - offset) * getAreaHeight()) + getInsets().top, this.eyeIconWidth, this.eyeIconHeight);
            g.setColor(this.stateColor[DiagState.OK.ordinal()]);
            this.renderer.drawCenteredString (g, insets.left + this.paddingVal, middleXVal, getHeight() - insets.bottom - 20, "55");
            this.renderer.drawCenteredString (g, middleXVal, insets.left + getAreaWidth(), getHeight() - insets.bottom - 20, "123");           
            return;
        }
        
        final float leftEyeDistance = CommonFunctions.limitFloat(e.getLeftEyePosition()[2]); 
        final float rightEyeDistance = CommonFunctions.limitFloat(e.getRightEyePosition()[2]); 

        final DiagState diagnosisState = EyeTrackingEventEvaluator.evaluateEvent(e);

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
        final String sLeft = String.format("%.0f", (CommonFunctions.limitFloat(leftEyeDistance, 0.0f, 1.0f) * 100));
        if (leftEyeDistance >= 0) {
            this.renderer.drawCenteredString (g, insets.left + this.paddingVal, middleXVal, getHeight() - insets.bottom - 20, sLeft);
            g.fillRect(this.leftEyeXPos, Math.round((leftEyeDistance- offset) * getAreaHeight()) + getInsets().top , this.eyeIconWidth, this.eyeIconHeight);
        } else {
            this.renderer.drawCenteredString (g, insets.left + this.paddingVal, middleXVal, getHeight() - insets.bottom - 20, "- -");
        }

        final String sRight = String.format("%.0f", (CommonFunctions.limitFloat(rightEyeDistance, 0.0f, 1.0f) * 100));
        if (rightEyeDistance >= 0) {
            this.renderer.drawCenteredString (g, middleXVal, insets.left + getAreaWidth(), getHeight() - insets.bottom - 20, sRight);
            g.fillRect(this.rightEyeXPos, Math.round((rightEyeDistance - offset) * getAreaHeight()) + getInsets().top, this.eyeIconWidth, this.eyeIconHeight);
        } else {
            this.renderer.drawCenteredString (g, middleXVal, insets.left + getAreaWidth(), getHeight() - insets.bottom - 20, "- -");
        }
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
     * @return the eyeIconHeight
     */
    public int getEyeIconHeight() {
        return this.eyeIconHeight;
    }

    /**
     * @param eyeIconHeight the eyeIconHeight to set
     */
    public void setEyeIconHeight(final int eyeIconHeight) {
        this.eyeIconHeight = eyeIconHeight;
    }

    /**
     * @return the eyeIconWidth
     */
    public int getEyeIconWidth() {
        return this.eyeIconWidth;
    }

    /**
     * @param eyeIconWidth the eyeIconWidth to set
     */
    public void setEyeIconWidth(final int eyeIconWidth) {
        this.eyeIconWidth = eyeIconWidth;
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
}
