/*
 * EyePositionPanel.java
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
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DiagState;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 *
 * Displays the eye-positions inside a black box.
 *
 * @author Nathaniel Egwu, Andreas Buhl
 *
 */
public class EyePositionDisplay extends AbstractTrackingEventComponent {

	/** */
	private static final long serialVersionUID = -8142292583304142838L;

	/** */
	private final int defaultEyepositionCanvasWidth = 500;

	/** */
	private final int defaultEyepositionCanvasHeight = 400;

	/** */
	private final int fixationPointSize = 12;

	/** */
	private final int gazeTrackLength = 10;

	/** */
    private final Point[] gazeTrack = new Point[this.gazeTrackLength];


	/** */
	private int gazeTrackPointer = 0;

	/** the pixel resolution of the eyetracker monitor */
	private final int screenXResolution = Toolkit.getDefaultToolkit().getScreenSize().width;

	/** */
	private final int screenYResolution = Toolkit.getDefaultToolkit().getScreenSize().height;


    /** */
    private Color eyePostionCanvasBackgroundColor = Color.BLACK;

	/** */
	private Color validRectColor = Color.RED;



	/** */
	public EyePositionDisplay() {
		this(null, null);
	}

	/**
	 * @param applicationData
	 * @param serverInfo
	 */
	public EyePositionDisplay(final ApplicationData applicationData, final ServerInfo serverInfo) {
		super(applicationData, serverInfo);

		setBackground(this.eyePostionCanvasBackgroundColor);
		this.setPreferredSize(new Dimension(this.defaultEyepositionCanvasWidth, this.defaultEyepositionCanvasHeight));

		// Initializing gaze tracking point array
		for(int i = 0; i < this.gazeTrack.length; i++){
            this.gazeTrack[i] = new Point(0,0);
		}

	}

	/**
	 * @param g
	 */
	@Override
	public void render(final Graphics g) {
		final EyeTrackingEvent event = (EyeTrackingEvent) this.trackingEvent;
        if (this.applicationData == null || event == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();

        // Getting borders and are sizes once
        final int leftBorder = getInsets().left;
        final int topBorder = getInsets().top;

        final int areaWidth = getAreaWidth();
        final int areaHeight = getAreaHeight();

		// Draw Background
		g2d.setBackground(this.eyePostionCanvasBackgroundColor);
		g2d.clearRect(leftBorder, topBorder, areaWidth, areaHeight);

		// Draw valid area rectangle
		g2d.setColor(this.validRectColor);
		final int x = this.renderer.getXPixelPos(EyeTrackingEventEvaluator.X_EYE_POSITION_MIN);
		final int y = this.renderer.getYPixelPos(EyeTrackingEventEvaluator.Y_EYE_POSITION_MIN);
		final int w = this.renderer.getXPixelPos(EyeTrackingEventEvaluator.X_EYE_POSITION_MAX - EyeTrackingEventEvaluator.X_EYE_POSITION_MIN);
		final int h = this.renderer.getYPixelPos(EyeTrackingEventEvaluator.Y_EYE_POSITION_MAX - EyeTrackingEventEvaluator.Y_EYE_POSITION_MIN);
		g2d.drawRect(x, y, w, h);


		// Calculate quality for state color
		final DiagState diagnosisState = EyeTrackingEventEvaluator.evaluateEvent(event);
		final Color currentStateColor;
		switch (diagnosisState) {
    		case BAD:
    			currentStateColor = Color.RED;
    			break;
    		case OK:
    			currentStateColor = Color.GREEN;
    			break;
    		case VAGUE:
    			currentStateColor = Color.YELLOW;
    			break;
    		default:
    			currentStateColor = Color.YELLOW;
    			break;
		}


        g2d.setColor(currentStateColor);

		// Draw left eye
		float pupilSizeLeft = CommonFunctions.limitFloat(event.getPupilSizeLeft(), EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);
		if (CommonFunctions.isBetweenIncludes(0, 1.0f, event.getLeftEyePosition()[0]) &&
		    CommonFunctions.isBetweenIncludes(0, 1.0f, event.getLeftEyePosition()[1]) &&
		    CommonFunctions.isBetweenIncludes(10, 40, (int) (pupilSizeLeft * 10))) {

			g2d.fillOval(this.renderer.getLeftEyeXPixelPos(event), this.renderer.getLeftEyeYPixelPos(event),
			           (int) (pupilSizeLeft * 10), (int) (pupilSizeLeft * 10));
		} else {
			try {
				pupilSizeLeft = CommonFunctions.limitFloat(
						((EyeTrackingEvent) this.previousTrackingEvent).getPupilSizeLeft(),
						EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);

				g2d.fillOval(this.renderer.getLeftEyeXPixelPos((EyeTrackingEvent) this.previousTrackingEvent), this.renderer.getLeftEyeYPixelPos((EyeTrackingEvent) this.previousTrackingEvent),
				           this.renderer.getLeftPupilPixelSize(pupilSizeLeft), this.renderer.getLeftPupilPixelSize(pupilSizeLeft));
			} catch (final RuntimeException e) { /* */ }
		}

		// Draw right eye
		float pupilSizeRight = CommonFunctions.limitFloat(event.getPupilSizeRight(), EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);
		if (CommonFunctions.isBetweenIncludes(0, 1.0f, event.getRightEyePosition()[0]) &&
		    CommonFunctions.isBetweenIncludes(0, 1.0f, event.getRightEyePosition()[1]) &&
		    CommonFunctions.isBetweenIncludes(10, 40, (int) (pupilSizeRight * 10))) {

			g2d.fillOval(this.renderer.getRightEyeXPixelPos(event), this.renderer.getRightEyeYPixelPos(event),
			           (int) (pupilSizeRight * 10), (int) (pupilSizeRight * 10));
		} else {
			try {
				pupilSizeRight = CommonFunctions.limitFloat(
						((EyeTrackingEvent) this.previousTrackingEvent).getPupilSizeRight(), EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);

				g2d.fillOval(this.renderer.getRightEyeXPixelPos((EyeTrackingEvent) this.previousTrackingEvent), this.renderer.getRightEyeYPixelPos((EyeTrackingEvent) this.previousTrackingEvent),
				           this.renderer.getRightPupilPixelSize(pupilSizeRight), this.renderer.getRightPupilPixelSize(pupilSizeRight));
			} catch (final RuntimeException e) { /* */ }
		}



		// Draw gaze center track and gaze point
		final Point gazeCenter = event.getGazeCenter();
		if ((gazeCenter.x > 0) && (gazeCenter.y > 0)) {
		    // Add to gaze track
		    this.gazeTrack[this.gazeTrackPointer] = gazeCenter;
	        this.gazeTrackPointer = (this.gazeTrackPointer + 1) % this.gazeTrackLength;

            final int startPositionIndex = (this.gazeTrackLength - 1 + this.gazeTrackPointer) % this.gazeTrackLength;
            final int endPositionIndex = (startPositionIndex + 1) % this.gazeTrackLength;

            final float scalingFactorX = (float) areaWidth / this.screenXResolution;
            final float scalingFactorY = (float) areaHeight / this.screenYResolution;

            g2d.setColor(Color.PINK);

            // Draw gaze center track history
			g2d.drawLine(Math.round(this.gazeTrack[startPositionIndex].x * scalingFactorX) + leftBorder,
			           Math.round(this.gazeTrack[startPositionIndex].y * scalingFactorY) + topBorder,
			           Math.round(this.gazeTrack[endPositionIndex].x * scalingFactorX) + leftBorder,
			           Math.round(this.gazeTrack[endPositionIndex].y * scalingFactorY) + topBorder);

			// Draw current gaze center point
			g2d.fillOval(Math.round(gazeCenter.x * scalingFactorX) + leftBorder - this.fixationPointSize / 2,
			           Math.round(gazeCenter.y * scalingFactorY) + topBorder - this.fixationPointSize / 2,
			           this.fixationPointSize, this.fixationPointSize);
		}

		g2d.dispose();
	}

	/**
	 * @param validRectColor
	 *            the validRectColor to set
	 */
	public void setValidRectColor(final Color validRectColor) {
		this.validRectColor = validRectColor;
	}

	/**
	 * @return the validRectColor
	 */
	public Color getValidRectColor() {
		return this.validRectColor;
	}

	/**
	 * @return the eyePostionCanvasBackgroundColor
	 */
	public Color getEyePostionCanvasBackgroundColor() {
		return this.eyePostionCanvasBackgroundColor;
	}

	/**
	 * @param eyePostionCanvasBackgroundColor
	 *            the eyePostionCanvasBackgroundColor to set
	 */
	public void setEyePostionCanvasBackgroundColor(final Color eyePostionCanvasBackgroundColor) {
		this.eyePostionCanvasBackgroundColor = eyePostionCanvasBackgroundColor;
	}

}
