package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * @author rb
 *
 */
public class EyePositionChart extends AbstractHistoryChartDisplay {

    /** */
    private static final long serialVersionUID = 1602077008678295091L;
    /** */
    private static final int DEFAULT_EYEPOSITIONCHARTPANEL_HEIGHT = -1;
    /** */
    private static final int DEFAULT_EYEPOSITIONCHARTPANEL_WIDTH = -1;

    /**
     * 
     */
    public EyePositionChart() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public EyePositionChart(final ApplicationData applicationData,
                            final ServerInfo serverInfo) {
        super(applicationData, serverInfo);

        this.setPreferredSize(new Dimension(DEFAULT_EYEPOSITIONCHARTPANEL_WIDTH, DEFAULT_EYEPOSITIONCHARTPANEL_HEIGHT));
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractHistoryChartDisplay#getValueFromMouseYPos(java.awt.event.MouseEvent)
     */
    @Override
    protected String getValueFromMousePos(final MouseEvent e) {
        // TODO add class specific calculation
        return super.getValueFromMousePos(e);
    }

    @Override
    protected float getLeftEyeNormalizedValue(final EyeTrackingEvent e) {
        return CommonFunctions.limitFloat(e.getLeftEyePosition()[1]);
    }

    @Override
    protected float getRightEyeNormalizedValue(final EyeTrackingEvent e) {
        return CommonFunctions.limitFloat(e.getRightEyePosition()[1]);
    }

    @Override
    float getLeftCurrVal(int x) {
        EyeTrackingEvent e = this.getServerInfo().getEyeTrackingRingBuffer().get(x);
        return e.getLeftEyePosition()[1];
    }

    @Override
    float getRightCurrVal(int x) {
        EyeTrackingEvent e = this.getServerInfo().getEyeTrackingRingBuffer().get(x);
        return e.getRightEyePosition()[1];
    }

}
