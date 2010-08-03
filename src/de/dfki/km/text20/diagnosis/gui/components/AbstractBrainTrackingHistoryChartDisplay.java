package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;

/**
 * @author Arman Vartan
 *
 */
public abstract class AbstractBrainTrackingHistoryChartDisplay extends AbstractTrackingEventComponent {

    /** */
    public static boolean hasInstance = false;

    /** */
    private static final long serialVersionUID = 4805889544008857101L;

    /** */
    private String currentValue;

    /** */
    private Color dataAreaBackgroundColor = Color.BLACK;

    /** */
    private final int defaultChartPanelHeight = -1;

    /** */
    private final int defaultChartPanelWidth = -1;

    /** */
    private float leftLowerLimit = 0.0f;

    /** */
    private float leftUpperLimit = 1.0f;

    /** */
    private Color messageColor = Color.CYAN;

    /** */
    protected MouseEvent mouseEvent;

    /** used for additional information requested by mouse click*/
    private boolean mousePressed;

    /** */
    private float rightLowerLimit = 0.0f;

    /** */
    private float rightUpperLimit = 1.0f;

    /** */
    private Color scaleAreaBackgroundColor = Color.BLACK;

    /** */
    private Color scaleColor = Color.ORANGE;

    /** pixel width of space between scale and data area */
    private int scaleGap = 3;

    /** pixel width of scale area */
    private int scaleWidth = 20;

    /** normalized size of space upon,between and below data tracks */
    private float spaceHeight;

    /** normalized size of data tracks */
    private float trackHeight = 0.35f;

    /** */
    public AbstractBrainTrackingHistoryChartDisplay() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public AbstractBrainTrackingHistoryChartDisplay(final ApplicationData applicationData, final ServerInfo serverInfo) {
        super(applicationData, serverInfo);
//        setPreferredSize(new Dimension(this.defaultChartPanelWidth, this.defaultChartPanelHeight));
//        this.spaceHeight = (1.0f - 2 * CommonFunctions.limitFloat(this.trackHeight, 0.1f, 0.45f)) / 3;
        
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(final MouseEvent e) {
//                AbstractBrainTrackingHistoryChartDisplay.this.setMousePressed(e);
//            }
//
//            @Override
//            public void mouseReleased(final MouseEvent e) {
//                AbstractBrainTrackingHistoryChartDisplay.this.setMousePressed(null);
//            }
//        });
    }
//
//    /**
//     * @return the the background color of the data area
//     */
//    public Color getDataAreaBackgroundColor() {
//        return this.dataAreaBackgroundColor;
//    }
//
//    /**
//     * @param normalizedValue an eyetracker value, normalized to a [0.0,1.0] interval
//     * @param baseLineOffset the y-pixel position of the tracks baseline  
//     * @return
//     */
//    int getEyeYPixelPos(final float normalizedValue, final int baseLineOffset) {
//        final int val = Math.round(getTrackHeight() * getAreaHeight() * normalizedValue);
//        return baseLineOffset - val;
//    }
//
//    /**
//     * @return
//     */
//    protected int getLeftBaseLineYPos() {
//        final int bl = Math.round(getAreaHeight() * (this.trackHeight * 2 + this.spaceHeight * 2)) + getInsets().top;
//        return bl;
//    }
//
//    /**
//     * @param x the x-pixel position inside the chart diplay, 
//     * justified to the left eye area
//     * @return the value current displayed on this x position
//     */
//    abstract float getLeftCurrVal(int x);
//
//    /**
//     * must return a value between 0.0f and 1.0f
//     */
//    abstract protected float getLeftEyeNormalizedValue(final EyeTrackingEvent e);
//
//    /**
//     * @return the leftLowerLimit
//     */
//    public float getLeftLowerLimit() {
//        return this.leftLowerLimit;
//    }
//
//    private int getLeftTopLineYPos() {
//        final int bl = Math.round(getAreaHeight() * (this.spaceHeight * 2 + this.trackHeight)) + getInsets().top;
//        return bl;
//    }
//
//    /**
//     * @return the leftUpperLimit
//     */
//    public float getLeftUpperLimit() {
//        return this.leftUpperLimit;
//    }
//
//    /**
//     * @return the messageColor
//     */
//    protected Color getMessageColor() {
//        return this.messageColor;
//    }
//
//    /**
//     * @return
//     */
//    protected int getRightBaseLineYPos() {
//        final int bl = Math.round(getAreaHeight() * (this.trackHeight + this.spaceHeight)) + getInsets().top;
//        return bl;
//    }
//
//    /**
//     * @param x the x-pixel position inside the chart diplay 
//     * justified to the right eye area
//     * @return the value current displayed on this x position
//     */
//    abstract float getRightCurrVal(int x);
//
//    /**
//     * must return a value between 0.0f and 1.0f
//     */
//    abstract protected float getRightEyeNormalizedValue(final EyeTrackingEvent e);
//
//    /**
//     * @return the rightLowerLimit
//     */
//    public float getRightLowerLimit() {
//        return this.rightLowerLimit;
//    }
//
//    /**
//     * @return
//     */
//    private int getRightTopLineYPos() {
//        final int bl = Math.round(getAreaHeight() * this.spaceHeight) + getInsets().top;
//        return bl;
//    }
//
//    /**
//     * @return the rightUpperLimit
//     */
//    public float getRightUpperLimit() {
//        return this.rightUpperLimit;
//    }
//
//    /**
//     * @return the background color of the scale area
//     */
//    public Color getScaleAreaBackgroundColor() {
//        return this.scaleAreaBackgroundColor;
//    }
//
//    /**
//     * @return the scaleColor
//     */
//    public Color getScaleColor() {
//        return this.scaleColor;
//    }
//
//    /**
//     * @return the scaleGap
//     */
//    public int getScaleGap() {
//        return this.scaleGap;
//    }
//
//    /**
//     * @return the scaleWidth
//     */
//    public int getScaleWidth() {
//        return this.scaleWidth;
//    }
//
//    /**
//     * @return the spaceHeight
//     */
//    public float getSpaceHeight() {
//        return this.spaceHeight;
//    }
//
//    /**
//     * @return the trackHeight
//     */
//    public float getTrackHeight() {
//        return this.trackHeight;
//    }

//    /**
//     * @param e
//     * @return a formatted representation of the data value currently displayed 
//     * on the x-position of a given mouse event (click)   
//     */
//    @SuppressWarnings({ "unused" })
//    protected String getValueFromMousePos(final MouseEvent e) {
//        final int y = e.getY();
//        final int x = e.getX() - getInsets().left - this.scaleGap - this.scaleWidth;
//        final int bufpos = Math.max(0, Math.round(x * this.serverInfo.getEyeTrackingRingBuffer().size() / getAreaWidth() - 1));
//
//        if (CommonFunctions.isBetweenIncludes(getLeftTopLineYPos(), getLeftBaseLineYPos(), y)) {
//
//            final float currVal = getLeftCurrVal(bufpos);
//            final float delta = getLeftBaseLineYPos() - getLeftTopLineYPos();
//            final float relativePos = delta - y + getLeftTopLineYPos();
//            final float range = getLeftUpperLimit() - getLeftLowerLimit();
//            final float portion = relativePos / delta;
//            final float result = getLeftLowerLimit() + portion * range;
//            return String.format("%.2f", new Float(currVal));
//        }
//        if (CommonFunctions.isBetweenIncludes(getRightTopLineYPos(), getRightBaseLineYPos(), y)) {
//            final float currVal = getRightCurrVal(bufpos);
//            final float delta = getRightBaseLineYPos() - getRightTopLineYPos();
//            final float relativePos = delta - y + getRightTopLineYPos();
//            final float range = getRightUpperLimit() - getRightLowerLimit();
//            final float result = getRightLowerLimit() + relativePos / delta * range;
//            return String.format("%.2f", new Float(currVal));
//        }
//
//        return "?: " + x;
//    }

//    /**
//     * @param x
//     * @return
//     */
//    private int getXPixelPos(final int x) {
//        return Math.max(0, x + getInsets().left) + this.scaleWidth + this.scaleGap;
//    }
//
//    /**
//     * @return the mousePressed
//     */
//    private boolean isMousePressed() {
//        return this.mousePressed;
//    }

    
    
    
    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractEyeTrackingEventComponent#render(java.awt.Graphics)
     */
    @Override
    public void render(final Graphics g) {
//        // Draw scale
//        g.setColor(this.scaleAreaBackgroundColor);
//        g.fillRect(getInsets().left, getInsets().top, this.scaleWidth, getAreaHeight());
//
//        // Draw data area
//        g.setColor(this.dataAreaBackgroundColor);
//        g.fillRect(getInsets().left + this.scaleWidth + this.scaleGap, getInsets().top, getAreaWidth() - this.scaleWidth - this.scaleGap, getAreaHeight());
//
//        // draw base lines
//        g.setColor(this.scaleColor);
//
//        this.renderer.drawDottedLine((Graphics2D) g, getInsets().left, getLeftTopLineYPos(), getInsets().left + getAreaWidth(), getLeftTopLineYPos());
//        g.drawString(" " + this.leftUpperLimit, getInsets().left, getLeftTopLineYPos() - 2);
//        this.renderer.drawDottedLine((Graphics2D) g, getInsets().left, getLeftBaseLineYPos(), getInsets().left + getAreaWidth(), getLeftBaseLineYPos());
//        g.drawString(" " + this.leftLowerLimit, getInsets().left, getLeftBaseLineYPos() - 2);
//
//        this.renderer.drawDottedLine((Graphics2D) g, getInsets().left, getRightTopLineYPos(), getInsets().left + getAreaWidth(), getRightTopLineYPos());
//        g.drawString(" " + this.rightUpperLimit, getInsets().left, getRightTopLineYPos() - 2);
//        this.renderer.drawDottedLine((Graphics2D) g, getInsets().left, getRightBaseLineYPos(), getInsets().left + getAreaWidth(), getRightBaseLineYPos());
//        g.drawString(" " + this.rightLowerLimit, getInsets().left, getRightBaseLineYPos() - 2);
//
//        if (this.applicationData == null) {
//            g.setColor(getMessageColor());
//            g.drawString("offline ...", Math.round(getAreaWidth() / 2 + getInsets().left), Math.round(getAreaHeight() / 2 + getInsets().top));
//            return;
//        }
//
//        //   draw data
//        //        int i = 0;
//        int cnt = 0;
//        int prevXPos = 0;
//        int prevBufPos = 0; //this.getAreaWidth();
//        EyeTrackingEvent previousChartEyeTrackingEvent = this.serverInfo.getEyeTrackingRingBuffer().get(prevBufPos);
//
//        // one value for each x-pixel position 
//        for (int i = 0; i < getAreaWidth(); i++) {
//
//            final int bufpos = Math.max(0, Math.round(i * this.serverInfo.getEyeTrackingRingBuffer().size() / getAreaWidth() - 1));
//            if (prevBufPos != bufpos && previousChartEyeTrackingEvent != null) {
//                final EyeTrackingEvent e = this.serverInfo.getEyeTrackingRingBuffer().get(bufpos);
//                if (e == null) {
//                    prevBufPos = bufpos;
//                    previousChartEyeTrackingEvent = e;
//                    cnt++;
//                    prevXPos = i;
//                    continue;
//                }
//                
//                
//                g.setColor(Color.YELLOW);
//                g.drawLine(getXPixelPos(prevXPos), getEyeYPixelPos(getLeftEyeNormalizedValue(previousChartEyeTrackingEvent), getLeftBaseLineYPos()),
//                           getXPixelPos(i), getEyeYPixelPos(getLeftEyeNormalizedValue(e), getLeftBaseLineYPos()));
//                g.setColor(Color.RED);
//                g.drawLine(getXPixelPos(prevXPos), getEyeYPixelPos(getRightEyeNormalizedValue(previousChartEyeTrackingEvent), getRightBaseLineYPos()),
//                           getXPixelPos(i), getEyeYPixelPos(getRightEyeNormalizedValue(e), getRightBaseLineYPos()));
//
//                prevBufPos = bufpos;
//                previousChartEyeTrackingEvent = e;
//                cnt++;
//                prevXPos = i;
//            } else {
//                final EyeTrackingEvent e = this.serverInfo.getEyeTrackingRingBuffer().get(bufpos);
//                previousChartEyeTrackingEvent = e;
//                prevBufPos = bufpos;
//            }
//        }
//
//        // draw warning if no buffered data
//        if (cnt < 2) {
//            g.setColor(getMessageColor());
//            g.drawString("no buffered data ...", Math.round(getAreaWidth() / 2 + getInsets().left), Math.round(getAreaHeight() / 2 + getInsets().top));
//        }
//
//        // 
//        if (isMousePressed()) {
//            g.setColor(getMessageColor());
//            //            g.drawString(getValueFromMouseYPos(this.mouseEvent), this.mouseEvent.getX() - 1, this.mouseEvent.getY() - 1);
//            g.drawString(this.currentValue, this.mouseEvent.getX() - 1, this.mouseEvent.getY() - 1);
//        }
    }

//    /**
//     * @param default_pupilsizechart_dataarea_backgroundcolor the dEFAULT_PUPILSIZECHART_DATAAREA_BACKGROUNDCOLOR to set
//     */
//    public void setDataAreaBackgroundColor(final Color default_pupilsizechart_dataarea_backgroundcolor) {
//        this.dataAreaBackgroundColor = default_pupilsizechart_dataarea_backgroundcolor;
//    }
//
//    /**
//     * @param leftLowerLimit the leftLowerLimit to set
//     */
//    public void setLeftLowerLimit(final float leftLowerLimit) {
//        this.leftLowerLimit = leftLowerLimit;
//    }
//
//    /**
//     * @param leftUpperLimit the leftUpperLimit to set
//     */
//    public void setLeftUpperLimit(final float leftUpperLimit) {
//        this.leftUpperLimit = leftUpperLimit;
//    }
//
//    /**
//     * @param messageColor the messageColor to set
//     */
//    protected void setMessageColor(final Color messageColor) {
//        this.messageColor = messageColor;
//    }
//
//    /**
//     * @param e
//     */
//    void setMousePressed(final MouseEvent e) {
//        this.mouseEvent = e;
//        if (e == null) {
//            this.mousePressed = false;
//        } else {
//            this.mousePressed = true;
//            this.currentValue = getValueFromMousePos(e);
//        }
//    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
     */
    @Override
    public void setPreferredSize(final Dimension preferredSize) {
        final Dimension d = new Dimension();

        d.width = preferredSize.width > 0 ? preferredSize.width : this.defaultChartPanelWidth;
        d.height = preferredSize.height > 0 ? preferredSize.height : this.defaultChartPanelHeight;

        super.setPreferredSize(d);
    }

//    /**
//     * @param rightLowerLimit the rightLowerLimit to set
//     */
//    public void setRightLowerLimit(final float rightLowerLimit) {
//        this.rightLowerLimit = rightLowerLimit;
//    }
//
//    /**
//     * @param rightUpperLimit the rightUpperLimit to set
//     */
//    public void setRightUpperLimit(final float rightUpperLimit) {
//        this.rightUpperLimit = rightUpperLimit;
//    }
//
//    /**
//     * @param scaleAreaBackgroundColor the scaleAreaBackgroundColor to set
//     */
//    public void setScaleAreaBackgroundColor(final Color scaleAreaBackgroundColor) {
//        this.scaleAreaBackgroundColor = scaleAreaBackgroundColor;
//    }
//
//    /**
//     * @param scaleColor the scaleColor to set
//     */
//    public void setScaleColor(final Color scaleColor) {
//        this.scaleColor = scaleColor;
//    }
//
//    /**
//     * @param scale_gap the sCALE_GAP to set
//     */
//    public void setScaleGap(final int scale_gap) {
//        this.scaleGap = scale_gap;
//    }
//
//    /**
//     * @param scale_width the sCALE_WIDTH to set
//     */
//    public void setScaleWidth(final int scale_width) {
//        this.scaleWidth = scale_width;
//    }
//
//    /**
//     * @param spaceHeight the spaceHeight to set
//     */
//    public void setSpaceHeight(final float spaceHeight) {
//        this.spaceHeight = spaceHeight;
//    }
//
//    /**
//     * @param trackHeight the trackHeight to set
//     */
//    public void setTrackHeight(final float trackHeight) {
//        this.trackHeight = trackHeight;
//    }

}
