package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingEvent;

/**
 * @author Arman Vartan
 *
 */
public abstract class AbstractBrainTrackingHistoryChartDisplay extends AbstractTrackingEventComponent {

    /** */
    private static final long serialVersionUID = 4805889544008857101L;

    /** Pixel width of scale area */
    private final int scaleAreaWidth = 30;

    /** Pixel width of space between scale and data area */
    private final int scaleGap = 3;

    /** Pixel width of space between left border an data area */
    private final int dataAreaBorder = this.scaleAreaWidth + this.scaleGap;

    /** Pixel height between each channel graph */
    private final int channelPixelSpacing = 6;

    private final int channelNameSpacing = 20;

    /** Stores for each channel the draw status, i.e. to be drawn or not */
    private final HashMap<String, Boolean> channelStatus;

    /** Color of the messages */
    private final Color messageColor = Color.CYAN;

    /** Color of the background */
    private final Color backgroundColor = Color.BLACK;

    /** Color of the scales */
    private final Color scaleColor = Color.ORANGE;

    /** Color of the data */
    private final Color dataColor = Color.RED;

    /** */
    public AbstractBrainTrackingHistoryChartDisplay() {
        this(null, null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     * @param channelStatus
     */
    public AbstractBrainTrackingHistoryChartDisplay(final ApplicationData applicationData, final ServerInfo serverInfo, HashMap<String,Boolean> channelStatus) {
        super(applicationData, serverInfo);
        this.channelStatus = channelStatus;
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractEyeTrackingEventComponent#render(java.awt.Graphics)
     */
    @Override
    public void render(final Graphics g) {
        // Calculating border positions in pixels
        final int leftBorder = getInsets().left;
        final int areaWidth = leftBorder + getAreaWidth();
        final int topBorder = getInsets().top;
        final int areaHeight = topBorder + getAreaHeight();

        // Return if there is no application data
        if (this.applicationData == null) {
            g.setColor(this.messageColor);
            g.drawString("Offline ...", Math.round(leftBorder + areaWidth / 2), Math.round(topBorder + areaHeight / 2));
            return;
        }



        // Drawing background with a white gap as scale
        g.setColor(this.backgroundColor);
        g.fillRect(leftBorder, topBorder, this.scaleAreaWidth, areaHeight);
        g.fillRect(leftBorder + this.dataAreaBorder, topBorder,
                   areaWidth  - this.dataAreaBorder, areaHeight);


        // Getting the number of channels that are enabled
        int channelCount = 0;
        for (final Boolean b : this.channelStatus.values()) {
            if (b.booleanValue()) {
                channelCount++;
            }
        }

        // If no channels should be drawn then we can stop right here
        if (channelCount == 0) {
            return;
        }



        // Range in pixels in which the intervall -1.0 ... +1.0 will be displayed
        final int offset = this.channelPixelSpacing + this.channelNameSpacing;

        final int channelValueRange = areaHeight / channelCount - offset;
        int baseLineYPosition = (channelValueRange + this.channelPixelSpacing) / 2 + this.channelNameSpacing;

        // Drawing the base lines with chaptions
        g.setColor(this.scaleColor);
        final int channelValueScalingFactor = channelValueRange / 2;
        final int channelNameOffset =  channelValueScalingFactor + this.channelNameSpacing / 2;

        for (final String channelName : this.channelStatus.keySet()) {
            if (this.channelStatus.get(channelName).booleanValue()) {

                this.renderer.drawDottedLine((Graphics2D) g, leftBorder, baseLineYPosition, areaWidth, baseLineYPosition);

                // Shifting chaptions one pixel so that isn't touching the left border or the base line
                g.drawString("0.0", leftBorder + 1, baseLineYPosition - 1);

                g.drawString(channelName, this.dataAreaBorder + 1, baseLineYPosition - channelNameOffset);

                baseLineYPosition += channelValueRange + offset;
            }
        }

        // Drawing the data
        int previousXPosition = 0;
        int previousBufferPosition = 0;

        // Getting previous tracking event from ring buffer
        BrainTrackingEvent previousBrainTrackingEvent = this.serverInfo.getBrainTrackingRingBuffer().get(previousBufferPosition);

        // Iterating over every x Position
        for (int currentXPosition = 0; currentXPosition < areaWidth; currentXPosition++) {

            final int currentBufferPosition = Math.max(0, Math.round(currentXPosition * this.serverInfo.getBrainTrackingRingBuffer().size() / areaWidth - 1));

            // Getting current brain tracking event from ring buffer and returning if it is null
            final BrainTrackingEvent currentBrainTrackingEvent = this.serverInfo.getBrainTrackingRingBuffer().get(currentBufferPosition);
            if (currentBrainTrackingEvent == null) {
                previousBufferPosition = currentBufferPosition;
                previousBrainTrackingEvent = currentBrainTrackingEvent;
                previousXPosition = currentXPosition;
                continue;
            }

            if ((previousBufferPosition != currentBufferPosition) && (previousBrainTrackingEvent != null)) {

                g.setColor(this.dataColor);

                // Reusing baseLineYPosition variable for the same purpose used above
                baseLineYPosition = (channelValueRange + this.channelPixelSpacing) / 2  + this.channelNameSpacing;

                // Interating over every channel in the previousBrainTrackingEvent and assuming that the current one has the same
                for (final String channelName : previousBrainTrackingEvent.getChannels()) {
                    // TODO: Might want to catch the case if the number of channels change while tracking

                    // Skip this iteration if nothing should be drawn
                    final boolean shouldBeDrawn = this.channelStatus.get(channelName).booleanValue();
                    if (!shouldBeDrawn) {
                        continue;
                    }

                    final double previousTrackingValue = previousBrainTrackingEvent.getValue(channelName);
                    final double currentTrackingValue = currentBrainTrackingEvent.getValue(channelName);

                    // Moving cursor to the previous position and drawing a line to the current position
                    // Y Position is from the baseLineYPosition up or down by the among of the tracking value scaled up to fit the designated area
                    g.drawLine(this.dataAreaBorder + previousXPosition, (int) Math.round(baseLineYPosition + previousTrackingValue * channelValueScalingFactor),
                               this.dataAreaBorder + currentXPosition , (int) Math.round(baseLineYPosition + currentTrackingValue * channelValueScalingFactor));

                    baseLineYPosition += channelValueRange + offset;
                }

                previousXPosition = currentXPosition;
            }

            previousBufferPosition = currentBufferPosition;
            previousBrainTrackingEvent = currentBrainTrackingEvent;
        }

        // TODO: If neccessary output warning if no buffered data
    }

}
