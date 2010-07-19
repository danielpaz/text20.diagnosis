package de.dfki.km.text20.diagnosis.gui.components.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.net.URI;

import javax.swing.JLabel;
import javax.swing.UIManager;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.options.getplugin.OptionCapabilities;
import de.dfki.km.text20.diagnosis.gui.ServerBrowserWindow;
import de.dfki.km.text20.diagnosis.gui.panel.ServerBrowserPanel;
import de.dfki.km.text20.services.trackingdevices.common.TrackingDevice;
import de.dfki.km.text20.services.trackingdevices.common.TrackingDeviceProvider;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDevice;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDeviceInfo;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingListener;

/**
 * @author rb
 *
 */
public class DeviceLabel extends JLabel {

    private static final String FONT_NAME = "Tahoma";

    private static final int FONT_SIZE = 10;

    private static final Color FONT_COLOR = Color.WHITE;

    /**  */
    //    private static final String imagePath = "/de/dfki/km/augmentedtext/diagnosis/gui/resources/";

    /**  */
    public static final int DEFAULT_HEIGHT = 20;

    /**  */
    public static final int DEFAULT_WIDTH = 125;

    /**  */
    private int centerDistance = -1;

    /**  */
    private int clockPosition = -1;

    private URI uri;

    /**  */
    private static final long serialVersionUID = -2313182624829982438L;

    /**
     * @param uri
     * @param distance
     */
    public DeviceLabel(final URI uri, final int distance) {
        super(uri.getHost() + ":" + uri.getPort());

        this.uri = uri;

        //default size
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setCenterDistance(distance);
        setToolTipText(uri.toString());
        setForeground(FONT_COLOR);
        //        setBorder(new LineBorder(Color.WHITE));
        setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
        setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
        //        setIcon(new ImageIcon(getClass().getResource(imagePath+BLUE_EYE_GIF)));
        validate();
    }

    /**
     * @return the centerDistance
     */
    public int getCenterDistance() {
        return this.centerDistance;
    }

    /**
     * @param centerDistance the centerDistance to set
     */
    public void setCenterDistance(final int centerDistance) {
        this.centerDistance = centerDistance;
    }

    /**
     * @return the clockPosition
     */
    public int getClockPosition() {
        return this.clockPosition;
    }

    /**
     * @param clockPosition the clockPosition to set
     */
    public void setClockPosition(final int clockPosition) {
        this.clockPosition = clockPosition;
    }

    /**
     * @return a Point object with the location of the anchor point
     * related to the labels screen ('clock') position
     */
    public Point getAnchorPoint() {
        final int width = getWidth();
        final int height = getHeight();
        final int x = getX();
        final int y = getY();
        
        switch (this.clockPosition) {
            case 1: return new Point(x + Math.round(width / 2), y + height);
            case 2: return new Point(x + Math.round(width / 2), y + height);
            case 3: return new Point(x + Math.round(width / 2), y + height);
            case 4: return new Point(x, y + Math.round(height / 2));
            case 5: return new Point(x + Math.round(width / 2), y);
            case 6: return new Point(x + Math.round(width / 2), y);
            case 7: return new Point(x + Math.round(width / 2), y);
            case 8: return new Point(x + width, y + Math.round(height / 2));
            default: return new Point(x + Math.round(width / 2), y + height);           
        }
    }

    /**
     * @param sbPanel
     */
    public void setFullDeviceText(ServerBrowserPanel sbPanel) {

        // TODO: How do we handle multiple connections?
        final PluginManager pm = ((ServerBrowserWindow) sbPanel.getRootPane().getParent()).getApplicationData().getPluginManager();//. applicationData.getPluginManager();
        final TrackingDeviceProvider<EyeTrackingDeviceInfo, EyeTrackingEvent, EyeTrackingListener, EyeTrackingDevice> deviceProvider = pm.getPlugin(TrackingDeviceProvider.class, new OptionCapabilities("eyetrackingdevice:trackingserver"));
        final TrackingDevice<EyeTrackingDeviceInfo, EyeTrackingEvent, EyeTrackingListener> openDevice = deviceProvider.openDevice(this.uri.toString());
        if (openDevice == null) {
            System.err.println("Error obtaining a tracking device");
            return;
        }
        setText("<html>" + openDevice.getDeviceInfo().getInfo("DEVICE_NAME") + "<br/>@" + this.uri.getHost() + ":" + this.uri.getPort() + "</html>");
        openDevice.closeDevice();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("text: " + getText() + "\n");
        sb.append("uri: " + getToolTipText() + "\n");
        sb.append("clockposition: " + getClockPosition() + "\n");
        sb.append("(x/y): (" + getX() + "/" + getY() + ")\n");
        sb.append("distance: " + this.centerDistance + "\n");
        return sb.toString();
    }
}