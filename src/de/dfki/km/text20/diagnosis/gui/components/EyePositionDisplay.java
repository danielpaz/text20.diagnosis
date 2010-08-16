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
import java.awt.Point;

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
	private static final int DEFAULT_EYEPOSITION_CANVAS_WIDTH = 500;

	/** */
	private static final int DEFAULT_EYEPOSITION_CANVAS_HEIGHT = 400;

	/** */
	private final int fixationPointSize = 12;

	/** */
	private final int gazeTrackLenght = 10;

	/** */
	private int gazeTrackPointer = 0;

	/** the pixel resolution of the eyetracker monitor */
	private final int screenXResolution = 1600;

	/** */
	private final int screenYResolution = 1600;

	/** */
	private final Point[] gazeTrack = new Point[this.gazeTrackLenght];




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
		this.setPreferredSize(new Dimension(DEFAULT_EYEPOSITION_CANVAS_WIDTH, DEFAULT_EYEPOSITION_CANVAS_HEIGHT));

		for(int i = 0; i < this.gazeTrack.length; ++i){
            this.gazeTrack[i] = new Point(0,0);
		}
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

		// draw Background
		g.setColor(this.eyePostionCanvasBackgroundColor);
		g.fillRect(getInsets().left, getInsets().top, getAreaWidth(), getAreaHeight());

		// draw valid area rectangle
		g.setColor(this.validRectColor);
		final int x = this.renderer.getXPixelPos(EyeTrackingEventEvaluator.X_EYE_POSITION_MIN);
		final int y = this.renderer.getYPixelPos(EyeTrackingEventEvaluator.Y_EYE_POSITION_MIN);
		final int w = this.renderer.getXPixelPos(EyeTrackingEventEvaluator.X_EYE_POSITION_MAX - EyeTrackingEventEvaluator.X_EYE_POSITION_MIN);
		final int h = this.renderer.getYPixelPos(EyeTrackingEventEvaluator.Y_EYE_POSITION_MAX - EyeTrackingEventEvaluator.Y_EYE_POSITION_MIN);
		g.drawRect(x, y, w, h);

		if (this.applicationData == null || event == null) {
			return;
		}


		// calculate quality
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



		// dray eyes
		g.setColor(currentStateColor);

		// Draw left eye
		float pupilSizeLeft = CommonFunctions.limitFloat(event.getPupilSizeLeft(), EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);
		if (CommonFunctions.isBetweenIncludes(0, 1.0f, event.getLeftEyePosition()[0]) &&
		    CommonFunctions.isBetweenIncludes(0, 1.0f, event.getLeftEyePosition()[1]) &&
		    CommonFunctions.isBetweenIncludes(10, 40, (int) (pupilSizeLeft * 10))) {
			g.fillOval(this.renderer.getLeftEyeXPixelPos(event), this.renderer.getLeftEyeYPixelPos(event),
			           (int) (pupilSizeLeft * 10), (int) (pupilSizeLeft * 10));
		} else {
			try {
				pupilSizeLeft = CommonFunctions.limitFloat(
						((EyeTrackingEvent) this.previousTrackingEvent).getPupilSizeLeft(),
						EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);

				g.fillOval(this.renderer.getLeftEyeXPixelPos((EyeTrackingEvent) this.previousTrackingEvent), this.renderer.getLeftEyeYPixelPos((EyeTrackingEvent) this.previousTrackingEvent),
				           this.renderer.getLeftPupilPixelSize(pupilSizeLeft), this.renderer.getLeftPupilPixelSize(pupilSizeLeft));
			} catch (final RuntimeException e) { }
		}

		// Draw right eye
		float pupilSizeRight = CommonFunctions.limitFloat(event.getPupilSizeRight(), EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);
		if (CommonFunctions.isBetweenIncludes(0, 1.0f, event.getRightEyePosition()[0]) &&
		    CommonFunctions.isBetweenIncludes(0, 1.0f, event.getRightEyePosition()[1]) &&
		    CommonFunctions.isBetweenIncludes(10, 40, (int) (pupilSizeRight * 10))) {

			g.fillOval(this.renderer.getRightEyeXPixelPos(event), this.renderer.getRightEyeYPixelPos(event),
			           (int) (pupilSizeRight * 10), (int) (pupilSizeRight * 10));
		} else {
			try {
				pupilSizeRight = CommonFunctions.limitFloat(
						((EyeTrackingEvent) this.previousTrackingEvent).getPupilSizeRight(), EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);
				g.fillOval(this.renderer.getRightEyeXPixelPos((EyeTrackingEvent) this.previousTrackingEvent), this.renderer.getRightEyeYPixelPos((EyeTrackingEvent) this.previousTrackingEvent),
				           this.renderer.getRightPupilPixelSize(pupilSizeRight), this.renderer.getRightPupilPixelSize(pupilSizeRight));
			} catch (final RuntimeException e) { }
		}



		// draw gaceCenter track
		if ((event.getGazeCenter().x > 0) && (event.getGazeCenter().y > 0)) {
		    // add to gaze track
		    this.gazeTrack[this.gazeTrackPointer] = event.getGazeCenter();
	        this.gazeTrackPointer = (this.gazeTrackPointer + 1) % this.gazeTrackLenght;


			g.setColor(Color.PINK);

			// draw gazeCenter history
			int n0 = 0;
			int n1 = 0;
			for (int i = 0; i < this.gazeTrackLenght; i++) {
				n0 = (i + this.gazeTrackPointer) % this.gazeTrackLenght;
				n1 = (n0 + 1) % this.gazeTrackLenght;
			}

			// TODO: hier hat am anfang gazeTrack[n1] null wenn man mit dem TrackingServer ausfŸhrt

			g.drawLine(this.gazeTrack[n0].x * getAreaWidth() / this.screenXResolution + getInsets().left,
			           this.gazeTrack[n0].y * getAreaHeight() / this.screenYResolution + getInsets().top,
			           this.gazeTrack[n1].x * getAreaWidth() / this.screenXResolution + getInsets().left,
			           this.gazeTrack[n1].y * getAreaHeight() / this.screenYResolution + getInsets().top);

			// draw current gazeCenter point
			g.fillOval((event.getGazeCenter().x * getAreaWidth() / this.screenXResolution + getInsets().left),
			           (event.getGazeCenter().y * getAreaHeight() / this.screenYResolution + getInsets().top),
			           this.fixationPointSize, this.fixationPointSize);
		}
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
