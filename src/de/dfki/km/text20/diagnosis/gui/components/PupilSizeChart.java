package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * TODO: Make a general chart component for other charts as well (head position, ...)
 * 
 * @author ABu
 *
 */
public class PupilSizeChart extends AbstractHistoryChartDisplay {

    /** */
    private static final long serialVersionUID = -2771364762444202451L;
    /** */
    private final static int DEFAULT_PUPILSIZECHARTPANEL_WIDTH = -1;
    /** */
    private final static int DEFAULT_PUPILSIZECHARTPANEL_HEIGHT = -1;

    /** */

    /**
     * default Constructor (parameterless )
     */
    public PupilSizeChart() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public PupilSizeChart(final ApplicationData applicationData,
                          final ServerInfo serverInfo) {
        super(applicationData, serverInfo);

        this.setPreferredSize(new Dimension(DEFAULT_PUPILSIZECHARTPANEL_WIDTH, DEFAULT_PUPILSIZECHARTPANEL_HEIGHT));
        setLeftUpperLimit(EyeTrackingEventEvaluator.MAX_PUPILSIZE);
        setLeftLowerLimit(EyeTrackingEventEvaluator.MIN_PUPILSIZE);
        setRightUpperLimit(EyeTrackingEventEvaluator.MAX_PUPILSIZE);
        setRightLowerLimit(EyeTrackingEventEvaluator.MIN_PUPILSIZE);
    }

    private float getNormalizedValue(final float pupilsize) {
        final float pupilSizeRange = EyeTrackingEventEvaluator.MAX_PUPILSIZE - EyeTrackingEventEvaluator.MIN_PUPILSIZE;
        final float pupilSize = CommonFunctions.limitFloat(pupilsize, EyeTrackingEventEvaluator.MIN_PUPILSIZE, EyeTrackingEventEvaluator.MAX_PUPILSIZE);
        return (pupilSize - EyeTrackingEventEvaluator.MIN_PUPILSIZE) / pupilSizeRange;
    }

    @Override
    protected String getValueFromMousePos(final MouseEvent e) {
        return super.getValueFromMousePos(e);
    }

    @Override
    protected float getLeftEyeNormalizedValue(final EyeTrackingEvent event) {
        return getNormalizedValue(event.getPupilSizeLeft());
    }

    @Override
    protected float getRightEyeNormalizedValue(final EyeTrackingEvent event) {
        return getNormalizedValue(event.getPupilSizeRight());
    }

    @Override
    float getLeftCurrVal(int x) {
        EyeTrackingEvent e = this.getServerInfo().getEyeTrackingRingBuffer().get(x);
        return e.getPupilSizeLeft();
    }

    @Override
    float getRightCurrVal(int x) {
        EyeTrackingEvent e = this.getServerInfo().getEyeTrackingRingBuffer().get(x);
        return e.getPupilSizeRight();
    }
}
