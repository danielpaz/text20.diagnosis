package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
public class BrainDataChart extends AbstractTrackingEventComponent {

    /** */
    private static final long serialVersionUID = 4805889544008857101L;

    /** Pixel width of scale area */
    private final int scaleAreaWidth = 40;

    /** Pixel width of space between scale and data area */
    private final int scaleGap = 3;

    /** Pixel width of space between left border an data area */
    private final int dataAreaBorder = this.scaleAreaWidth + this.scaleGap;

    /** Pixel height between each channel graph */
    private final int channelPixelSpacing = 6;

    /** Pixel height for every channel name */
    private final int channelNameSpacing = 20;

    /** Total pixel offset */
    private final int spacingOffset = this.channelPixelSpacing + this.channelNameSpacing;

    /** Stores for each channel the draw status, i.e. to be drawn or not */
    private final HashMap<String, Boolean> channelStatus;

    /** Color of the messages */
    private final Color messageColor = Color.CYAN;

    /** Color of the background */
    private final Color backgroundColor = Color.BLACK;

    /** Color of the scales */
    private final Color scaleColor = Color.ORANGE;

    /** Color of the +1.0 and -1.0 scales */
    private final Color helperScaleColor = this.scaleColor.darker().darker();

    /** Color of the data */
    private final Color dataColor = Color.RED;

    /** */
    public BrainDataChart() {
        this(null, null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     * @param channelStatus
     */
    public BrainDataChart(final ApplicationData applicationData, final ServerInfo serverInfo, HashMap<String,Boolean> channelStatus) {
        super(applicationData, serverInfo);

        this.channelStatus = channelStatus;

        setPreferredSize(new Dimension(-1, -1));

        setFont(new Font( Font.MONOSPACED, Font.PLAIN, 14 ));
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractEyeTrackingEventComponent#render(java.awt.Graphics)
     */
    @Override
    public void render(final Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        // Calculating border positions in pixels
        final int leftBorder = getInsets().left;
        final int areaWidth = leftBorder + getAreaWidth();
        final int topBorder = getInsets().top;
        final int areaHeight = topBorder + getAreaHeight();

        // Return if there is no application data
        if (this.applicationData == null) {
            g2d.setColor(this.messageColor);
            g2d.drawString("Offline ...", Math.round(leftBorder + areaWidth / 2), Math.round(topBorder + areaHeight / 2));
            return;
        }



        // Drawing background with a white gap as scale
//        g.setColor(this.backgroundColor);
//        g.fillRect(leftBorder, topBorder, this.scaleAreaWidth, areaHeight);
//        g.fillRect(leftBorder + this.dataAreaBorder, topBorder,
//                   areaWidth  - this.dataAreaBorder, areaHeight);
        g2d.setBackground(this.backgroundColor);
        g2d.clearRect(leftBorder, topBorder, areaWidth, areaHeight);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(this.scaleAreaWidth, topBorder, this.scaleGap, areaHeight);


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
        final int channelValueRange = areaHeight / channelCount - this.spacingOffset;

        // Some constants used in the loop all over again
        final int channelValueScalingFactor = channelValueRange / 2;
        final int channelNameOffset = channelValueScalingFactor + this.channelNameSpacing / 2;

        // Base line y position which gets incremented by channelValueRange + this.spacingOffset every iteration
        int baseLineYPosition = (channelValueRange + this.channelPixelSpacing) / 2 + this.channelNameSpacing;

        // Drawing base lines, captions and data
        for (final String channelName : this.channelStatus.keySet()) {
            final boolean shouldBeDrawn = this.channelStatus.get(channelName).booleanValue();

            if (shouldBeDrawn) {
                // Drawing base line
                g2d.setColor(this.scaleColor);
                this.renderer.drawDottedLine(g2d, this.dataAreaBorder, baseLineYPosition, areaWidth, baseLineYPosition);

                // Shifting captions one pixel so that isn't touching the left border or the base line
                g2d.drawString(" 0.0", this.dataAreaBorder - 39, baseLineYPosition + 6);

                // Drawing channel name caption
                g2d.drawString(channelName, this.dataAreaBorder + 3, baseLineYPosition - channelNameOffset + 6);


                final int minusOneYPosition = baseLineYPosition + channelValueScalingFactor;
                final int plusOneYPosition = baseLineYPosition - channelValueScalingFactor;

                g2d.setColor(this.helperScaleColor);
                this.renderer.drawDottedLine(g2d, this.dataAreaBorder, plusOneYPosition, areaWidth, plusOneYPosition);
                this.renderer.drawDottedLine(g2d, this.dataAreaBorder, minusOneYPosition, areaWidth, minusOneYPosition);

                g2d.drawString("+1.0", this.dataAreaBorder - 39, plusOneYPosition + 9);
                g2d.drawString("-1.0", this.dataAreaBorder - 39, minusOneYPosition + 1);



                // Drawing the data
                int previousBufferPosition = 0;
                int previousXPosition = 0;

                // Getting previous tracking event from ring buffer
                BrainTrackingEvent previousBrainTrackingEvent = this.serverInfo.getBrainTrackingRingBuffer().get(previousBufferPosition);

                for (int currentXPosition = 0; currentXPosition < areaWidth; currentXPosition++) {
                    final int currentBufferPosition = Math.max(Math.round(currentXPosition * this.serverInfo.getBrainTrackingRingBuffer().size() / areaWidth - 1), 0);

                    // Getting current brain tracking event from ring buffer and returning if it is null
                    final BrainTrackingEvent currentBrainTrackingEvent = this.serverInfo.getBrainTrackingRingBuffer().get(currentBufferPosition);
                    if (currentBrainTrackingEvent == null) {
                        previousBufferPosition = currentBufferPosition;
                        previousBrainTrackingEvent = currentBrainTrackingEvent;
                        previousXPosition = currentXPosition;
                        continue;
                    }

                    if ((previousBufferPosition != currentBufferPosition) && (previousBrainTrackingEvent != null)) {
                        final double previousTrackingValue = previousBrainTrackingEvent.getValue(channelName);
                        final double currentTrackingValue = currentBrainTrackingEvent.getValue(channelName);

                        // Moving cursor to the previous position and drawing a line to the current position
                        // Y Position is from the baseLineYPosition up or down by the among of the tracking value scaled up to fit the designated area
                        g2d.setColor(this.dataColor);
                        g2d.drawLine(this.dataAreaBorder + previousXPosition, (int) Math.round(baseLineYPosition + previousTrackingValue * channelValueScalingFactor),
                                   this.dataAreaBorder + currentXPosition , (int) Math.round(baseLineYPosition + currentTrackingValue * channelValueScalingFactor));

                        previousXPosition = currentXPosition;
                    }

                    previousBufferPosition = currentBufferPosition;
                    previousBrainTrackingEvent = currentBrainTrackingEvent;
                }

                baseLineYPosition += channelValueRange + this.spacingOffset;
            }
        }

        g2d.dispose();

        // TODO: If neccessary output warning if no buffered data
    }

}
