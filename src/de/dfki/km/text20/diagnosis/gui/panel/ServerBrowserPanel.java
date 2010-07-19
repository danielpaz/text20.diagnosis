package de.dfki.km.text20.diagnosis.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.remotediscovery.DiscoveredPlugin;
import net.xeoh.plugins.remotediscovery.RemoteDiscovery;
import de.dfki.km.text20.diagnosis.gui.ServerWindow;
import de.dfki.km.text20.diagnosis.gui.components.util.DeviceLabel;
import de.dfki.km.text20.diagnosis.gui.panel.templates.ServerBrowserTemplate;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;
import de.dfki.km.text20.trackingserver.eyes.remote.TrackingServerRegistry;

/**
 * @author rb
 *
 */
public class ServerBrowserPanel extends ServerBrowserTemplate {

    private static final int MIN_DEVICE_DISTANCE = 300;
    private static final int MAX_DEVICE_DISTANCE = 1000;
    private static final int JUNCTION_POINT_DIAMETER = 4;
    private static final int POSITION_CNT = 8;
    private static final int MINIMAL_JUNCTION_LENGHT = 80;
    /** */
    private static final long serialVersionUID = -5166103908732670108L;
    /** */
    ApplicationData applicationData;
    /** */
    private HashMap<String, DiscoveredPlugin> serverMap;
    /** */
    private PluginManager pluginManager;
    /** */
    public javax.swing.Timer timer;
    /** */
    private RemoteDiscovery discovery;
    /** */
    private Collection<DiscoveredPlugin> plugins;
    
    HashMap<String, DeviceLabel> deviceLabelMap = new HashMap<String, DeviceLabel>();
    /** */
    final Logger logger = Logger.getLogger(this.getClass().getName());    
    /** */
    public boolean firstUpdate = true;
    /** */
    final Image image = Toolkit.getDefaultToolkit(  ).getImage(ServerWindow.class.getResource("resources/pc.gif"));
    /** */
    private Color lineColor = Color.WHITE;

    /**
     * @param ad
     */
    public ServerBrowserPanel(final ApplicationData ad) {
        super();
        this.applicationData = ad;
        
//        ((JFrame) ServerBrowserPanel.this.getRootPane().getParent()).setState(Frame.ICONIFIED);        

        this.pluginManager = this.applicationData.getPluginManager();
        try {
            this.pluginManager.addPluginsFrom(new URI("classpath://*"));
        } catch (final URISyntaxException e) {
            e.printStackTrace();
        }
        
        this.discovery = this.pluginManager.getPlugin(RemoteDiscovery.class);
        
        this.serverMap = new HashMap<String, DiscoveredPlugin>();
        isDeviceListChanged();
        
        if(this.deviceLabelMap.size()==1){
            openDiagnosisFrame(ad, this.deviceLabelMap.keySet().toArray()[0].toString());
        }
        
        this.getUpdateButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent e) {
                ServerBrowserPanel.this.updateDeviceLabels();
                ServerBrowserPanel.this.getUpdateButton().setEnabled(! ServerBrowserPanel.this.getUpdateButton().isEnabled());
            }});
                
        

        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) { 
                arrangeLabelsOnDesktop(ServerBrowserPanel.this.deviceLabelMap);
            }
        });

        
        this.timer = new javax.swing.Timer(3000, new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent e) {
                new WorkerThread().run();
            }});
        this.timer.start();
    }

    /**
     * @param ad
     * @param uri 
     * 
     * opens a diagnosis window for the device with given uri and the 
     * context data of the calling application 
     */
    void openDiagnosisFrame(final ApplicationData ad, final String uri) {

        //avoid multiple windows for same device
        if (ad.getWindowList().containsKey(uri)){
            ad.getWindowList().get(uri).setVisible(true);
           return;
        }
        
        final ServerInfo si = new ServerInfo (uri);
        ad.getServerInfos().add(si);

        //create new diagnosis window and make it visible
        final ServerWindow diagnosisWindow = new ServerWindow(ad,si);

        diagnosisWindow.setVisible(true);

        // add this window to the application window list
        ad.getWindowList().put(uri, diagnosisWindow);

        // set close behavior of diagnosis frame
        diagnosisWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        diagnosisWindow.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(final WindowEvent e) {
                ad.getWindowList().remove(((ServerWindow) (e.getSource())).getServerInfo().getURI());
                ad.getServerInfos().remove(((ServerWindow) e.getSource()).getServerInfo());
                diagnosisWindow.dispose();
            }
            
            // deIconify Server Browser on close of diagnosis window 
            @Override
            public void windowClosed(WindowEvent e) {
                ((JFrame) ServerBrowserPanel.this.getRootPane().getParent()).setState(Frame.NORMAL);
                ServerBrowserPanel.this.firstUpdate = true;
            }

			@Override
			public void windowOpened(WindowEvent e) {
                ((JFrame) ServerBrowserPanel.this.getRootPane().getParent()).setState(Frame.ICONIFIED);
			}
       });
        diagnosisWindow.invalidate();
    }

    /**
     * @return true, if device set has changed, otherwise false
     */
    boolean isDeviceListChanged() {
        final HashMap<String, DiscoveredPlugin> incomingMap = new HashMap<String, DiscoveredPlugin>();
        final int oldsize = this.serverMap.size();
        try {
            this.plugins = this.discovery.discover(TrackingServerRegistry.class);
            for (final DiscoveredPlugin plugin : this.plugins) {
                final String key = plugin.getPublishURI().toString(); 
                incomingMap.put(key, plugin);
                if (!this.deviceLabelMap.containsKey(key) /** && this.deviceLabelMap.size() <= 3*/){ 
                    this.serverMap.put(key, plugin);
                    final int di = plugin.getDistance();
//                  final DeviceLabel label = new DeviceLabel(,di.intValue());
                    final DeviceLabel label = new DeviceLabel(plugin.getPublishURI(),di);
//                    this.serverInfo.getDevice().getDeviceInfo().getInfo("DEVICE_NAME")
                    label.setClockPosition(getEmptyClockPosition(this.deviceLabelMap));
                    label.addMouseListener(new DiagnoseWindowCaller());
                    label.setFullDeviceText(this);
                    this.deviceLabelMap.put(key, label );
                }
            }
        } catch (final Throwable e) {
            e.printStackTrace();
//            System.err.println("Exception polling devices (method: ServerBrowserPanel.isDeviceListChanged) " + e.getMessage());
        }
        
        // remove missing DeviceLabels from Map
        this.deviceLabelMap.keySet().retainAll(incomingMap.keySet());
        
        //remove missing plugINs from Map
        return this.serverMap.keySet().retainAll(incomingMap.keySet()) || (oldsize != incomingMap.size());
    }
    
    
    /** 
     *  update and arrange the devices on the desktop panel
    */   
    public void updateDeviceLabels (){
        // final Vector <DeviceLabel> deviceLabels  = new Vector<DeviceLabel>();
        
        // remove device labels from Desktop
         clearDesktop(); 

        for (final String key: this.deviceLabelMap.keySet()){
          getDesktopPane().add(this.deviceLabelMap.get(key));            
//          this.deviceLabelMap.get(key).initConnection();
        }
        getDesktopPane().invalidate();
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
    
        int cX = getWidth()/2;
        int cY = getHeight()/2;
    
        // draw junction lines
        try {
            // TODO: handle exception
            Color oldCol = g.getColor();
            g.setColor(this.lineColor );
            
            for (String key : this.deviceLabelMap.keySet()) {
                DeviceLabel label = this.deviceLabelMap.get(key);
                g.drawLine(label.getAnchorPoint().x, label.getAnchorPoint().y ,cX, cY);  
                g.fillOval(label.getAnchorPoint().x-2, label.getAnchorPoint().y-2, JUNCTION_POINT_DIAMETER, JUNCTION_POINT_DIAMETER);
            }
            g.setColor(oldCol);
        } catch (Exception e) {
            // paint nothing, if any thing fails getting junctions
        }
        
        // draw center image
        int iw = this.image.getWidth(this); 
        int ih = this.image.getHeight(this);
        g.drawImage(this.image, (getWidth() - iw) / 2 +1, (getHeight() - ih) / 2+1,iw,ih, this);
    
    }

    /**
     * @param deviceLabels
     * 
     * arrange (yet at most 8) labels clockwise beginning at 10.30h (pos 1)
     * with the distance related to its network response time
     * the distance ratio ist restricted to a fixed maximum (3)
     */
     void arrangeLabelsOnDesktop(final HashMap<String, DeviceLabel> map) {
        final int currentPixelWidth = getWidth();        
        final int currentPixelHeigth = getHeight();
        
        if ((currentPixelHeigth == 0) || (currentPixelWidth == 0)) {
            return;
        }
        final int midPixelY = currentPixelHeigth / 2;
        final int midPixelX = currentPixelWidth / 2;
        
        // restrict pointer length to the half of the windows largest side minus the label width
        final double maxPixelDistanceFromCenter = Math.min(currentPixelWidth, currentPixelHeigth)/2 - DeviceLabel.DEFAULT_HEIGHT ;

//        final int ratio = 3;
        
        // restrict ratio to 1:ratio
//        final double maxValidDistance = Math.min(minDeviceDistance*ratio, maxDeviceDistance);       
        
        for (String key : map.keySet()) {
            
            final DeviceLabel element = map.get(key);
            
            //calculate 'clock' angle fur i-th position         
            final int i = element.getClockPosition();
            if (i > POSITION_CNT)
                return;
            final double radians = 2*Math.PI* ((Math.round(POSITION_CNT * 1.5)-i)%POSITION_CNT)/POSITION_CNT;
            
            final int distance = new Integer(this.serverMap.get(element.getToolTipText()).getDistance()).intValue();
            // normalize device distance and calc resulting pixel distance
            final long rlen = CommonFunctions.limitLong(distance,getMinDeviceDistance(), getMaxDeviceDistance());
            final double normalizedLen = (double) rlen / getMaxDeviceDistance(); 
            // set minimal len 
            final long len = Math.max(Math.round(normalizedLen * maxPixelDistanceFromCenter),MINIMAL_JUNCTION_LENGHT);
                       
            // calculate position for label                        
            final int x = (int) Math.round(midPixelX + Math.cos(radians)* len - element.getWidth()/2); 
            final int y = (int) Math.round(midPixelY - Math.sin(radians)* len - element.getHeight()/2); 
            
            element.setBounds(x,y,element.getWidth(),element.getHeight());
            element.setVisible(true);
            element.invalidate();
        }
        repaint();
    }
     
     
     
    /**
     * @return the distance value of the farest detected device
     */
    int getMaxDeviceDistance() {
//        int max = 0;
//        for (final String key: this.serverMap.keySet()){
//            final int i = this.serverMap.get(key).getDistance();
//            max = Math.max(max, i);
//        }
        return MAX_DEVICE_DISTANCE;
    }

    /**
     * @return the distance value of the closest detected device
     */
    private int getMinDeviceDistance() {
//        int min = getMaxDeviceDistance();
//        for (final String key: this.serverMap.keySet()){
//            final int i = this.serverMap.get(key).getDistance();
//            min = Math.min(min, i);
//        }
        return MIN_DEVICE_DISTANCE;
    }

    /**
     * @param deviceLabelMap2
     * @return
     */
    private int getEmptyClockPosition(HashMap<String, DeviceLabel> deviceLabelMap2) {
        for (int i = 1; i<= 8; i++){
            boolean occupied = false;
            for (String key : deviceLabelMap2.keySet()) {
                if (i == deviceLabelMap2.get(key).getClockPosition()){
                    occupied = true;
                }
            }
            if (!occupied)
                return i;
        }
        return -1;
    }

    /** */
    private void clearDesktop() {
        final Component[] dp = getDesktopPane().getComponents();
        for (int i = 0; i < dp.length; i++) {
            if ((dp[i] instanceof DeviceLabel)){
                getDesktopPane().remove(dp[i]);
            }
        }
    }


    /**
     * @author buhl
     */
    class WorkerThread extends Thread {

        /* (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            
            if (isDeviceListChanged()) {
                updateDeviceLabels();
                arrangeLabelsOnDesktop(ServerBrowserPanel.this.deviceLabelMap);
            }
         }
    }

    class DiagnoseWindowCaller extends MouseAdapter{
        @Override
        public void mouseClicked(final MouseEvent e) {
            if (e.getClickCount()==2) {
                openDiagnosisFrame(ServerBrowserPanel.this.applicationData , ((DeviceLabel) e.getSource()).getToolTipText());
            }
        }
        
    }

    /**
     * @return the applicationData
     */
    public ApplicationData getApplicationData() {
        return this.applicationData;
    }
}
