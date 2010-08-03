/**
 * 
 */
package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Dimension;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;

/**
 * @author Vartan
 *
 */
public class BrainDataChart extends AbstractEyeTrackingHistoryChartDisplay {

    /** */
    private static final long serialVersionUID = 6906924592431585405L;

    /** */
    public BrainDataChart() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public BrainDataChart(ApplicationData applicationData, ServerInfo serverInfo) {
        super(applicationData, serverInfo);

        this.setPreferredSize(new Dimension(-1, -1));
    }

    /* (non-Javadoc)
     * @see de.dfki.km.text20.diagnosis.gui.components.AbstractEyeTrackingHistoryChartDisplay#getLeftCurrVal(int)
     */
    @Override
    float getLeftCurrVal(int x) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see de.dfki.km.text20.diagnosis.gui.components.AbstractEyeTrackingHistoryChartDisplay#getRightCurrVal(int)
     */
    @Override
    float getRightCurrVal(int x) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see de.dfki.km.text20.diagnosis.gui.components.AbstractEyeTrackingHistoryChartDisplay#getLeftEyeNormalizedValue(de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent)
     */
    @Override
    protected float getLeftEyeNormalizedValue(de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent e) {
        return 0;
    }

    /* (non-Javadoc)
     * @see de.dfki.km.text20.diagnosis.gui.components.AbstractEyeTrackingHistoryChartDisplay#getRightEyeNormalizedValue(de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent)
     */
    @Override
    protected float getRightEyeNormalizedValue(de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent e) {
        return 0;
    }

}
