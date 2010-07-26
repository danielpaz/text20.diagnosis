package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Dimension;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * @author rb
 *
 */
public class EyeDistanceChart extends AbstractHistoryChartDisplay {

    /** */
    private static final long serialVersionUID = -871265621650266414L;
    /** */
    private final static int DEFAULT_EYEDISTANCECHARTPANEL_WIDTH = -1;
    /** */
    private final static int DEFAULT_EYEDISTANCECHARTPANEL_HEIGHT = -1;

    /**
     * @param applicationData
     * @param serverInfo
     */
    public EyeDistanceChart(final ApplicationData applicationData,
                            final ServerInfo serverInfo) {
        super(applicationData, serverInfo);
        this.setPreferredSize(new Dimension(DEFAULT_EYEDISTANCECHARTPANEL_WIDTH, DEFAULT_EYEDISTANCECHARTPANEL_HEIGHT));
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractHistoryChartDisplay#getLeftEyeNormalizedValue(de.dfki.km.augmentedtext.services.trackingdevices.EyeTrackingEvent)
     */
    @Override
    protected float getLeftEyeNormalizedValue(final EyeTrackingEvent e) {
        return CommonFunctions.limitFloat(e.getLeftEyePosition()[2]);
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractHistoryChartDisplay#getRightEyeNormalizedValue(de.dfki.km.augmentedtext.services.trackingdevices.EyeTrackingEvent)
     */
    @Override
    protected float getRightEyeNormalizedValue(final EyeTrackingEvent e) {
        return CommonFunctions.limitFloat(e.getRightEyePosition()[2]);
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractHistoryChartDisplay#getLeftCurrVal(int)
     */
    @Override
    float getLeftCurrVal(final int x) {
        final EyeTrackingEvent e = this.getServerInfo().getEyeTrackingRingBuffer().get(x);
        return e.getLeftEyePosition()[2];
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.AbstractHistoryChartDisplay#getRightCurrVal(int)
     */
    @Override
    float getRightCurrVal(final int x) {
        final EyeTrackingEvent e = this.getServerInfo().getEyeTrackingRingBuffer().get(x);
        return e.getRightEyePosition()[2];
    }
}
