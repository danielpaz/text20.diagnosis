/*
 * RenderUtil.java
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
package de.dfki.km.text20.diagnosis.gui.components.util;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import de.dfki.km.text20.diagnosis.gui.components.AbstractEyeTrackingEventComponent;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * @author rb
 *
 */
public class AbstractRenderUtil {

    /** */
    private final AbstractEyeTrackingEventComponent component;

    /** pixel per mm of pupilSize*/
    private int pixelPerUnit = 10;

    /**
     * @param component
     */
    public AbstractRenderUtil(final AbstractEyeTrackingEventComponent component) {
        this.component = component;
    }

    /**
     * @param f
     * @return .
     */
    public int getYPixelPos(final float f) {
        return Math.round(f * this.component.getAreaHeight() + this.component.getBorder().getBorderInsets(this.component).top);
    }

    /**
     * @param f
     * @return .
     */
    public int getXPixelPos(final float f) {
        return Math.round(f * this.component.getAreaWidth() + this.component.getBorder().getBorderInsets(this.component).left);
    }

    /**
     * @param f
     * @return
     */
    int getReverseXPixelPos(final float f) {
        //return Math.round((1.0f - f) * this.component.getAreaWidth() + this.component.getBorder().getBorderInsets(this.component).left);
    	return Math.round((f) * this.component.getAreaWidth() + this.component.getBorder().getBorderInsets(this.component).left);
    }

    /**
     * @return .
     */
    public float getPixelPerUnit() {
        return this.pixelPerUnit;
    }

    /**
     * @param pixelperUnit
     */
    public void setPixelPerUnit(final int pixelperUnit) {
        this.pixelPerUnit = pixelperUnit;
    }

    /**
     * @param leftSize an Eyetracker Event
     * @return .
     */
    public int getLeftPupilPixelSize(final float leftSize) {
        return Math.round(CommonFunctions.limitFloat(leftSize, EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE) * getPixelPerUnit());
    }

    /**
     * @param rightSize the tracking event 
     * @return the calculated pixel size for the right pupil of the provided event
     */
    public int getRightPupilPixelSize(final float rightSize) {
        return Math.round(CommonFunctions.limitFloat(rightSize, EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE) * getPixelPerUnit());
    }

    /**
     * @param e an Eyetracker Event
     * @return .
     */
    public int getRightEyeYPixelPos(final EyeTrackingEvent e) {
        return getYPixelPos(e.getRightEyePosition()[1]);
    }

    /**
     * @param e an Eyetracker Event
     * @return .
     */
    public int getRightEyeXPixelPos(final EyeTrackingEvent e) {
        return getReverseXPixelPos(e.getRightEyePosition()[0]);
    }

    /**
     * @param e an Eyetracker Event
     * @return .
     */
    public int getLeftEyeYPixelPos(final EyeTrackingEvent e) {
        return getYPixelPos(e.getLeftEyePosition()[1]);
    }

    /**
     * @param e an Eyetracker Event
     * @return .
     */
    public int getLeftEyeXPixelPos(final EyeTrackingEvent e) {
        return getReverseXPixelPos(e.getLeftEyePosition()[0]);
    }

    /**
     * @param g
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawDottedLine(final Graphics2D g, final int x1, final int y1,
                               final int x2, final int y2) {
        final float[] dash = { 2, 3 };
        final Stroke oldStroke = g.getStroke();

        final BasicStroke stroke = new BasicStroke(0.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, dash, 0);

        g.setStroke(stroke);
        g.drawLine(x1, y1, x2, y2);
        g.setStroke(oldStroke);
    }

    /**
     * @param g
     * @param leftBound
     * @param rightBound
     * @param y
     * @param s
     */
    public void drawCenteredString(final Graphics g, final int leftBound,
                                   final int rightBound, final int y, final String s) {
        final FontMetrics metrics = g.getFontMetrics();
        final int w = metrics.stringWidth(s);
        final int x = leftBound + Math.round(rightBound - leftBound) / 2 - Math.round(w / 2);
        g.drawString(s, x, y);
    }
}
