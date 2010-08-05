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
public class BrainDataChart extends AbstractBrainTrackingHistoryChartDisplay {

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
        
//      System.out.println("#### New Brain Tracking Event ####");
//      Collection<String> channels = ((BrainTrackingEvent) event).getChannels();
//      
//      for (String s : channels) {
//          System.out.println(s + " = " + ((BrainTrackingEvent) event).getValue(s));
//      }
    }

}
