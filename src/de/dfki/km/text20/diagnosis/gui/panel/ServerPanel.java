/*
 * ServerPanel.java
 * 
 * Copyright (c) 2010, Ralf Biedert, DFKI. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301  USA
 *
 */
package de.dfki.km.text20.diagnosis.gui.panel;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXHyperlink;

import de.dfki.km.text20.diagnosis.gui.RecalibrationWindow;
import de.dfki.km.text20.diagnosis.gui.components.BrainDataChart;
import de.dfki.km.text20.diagnosis.gui.components.EyeDistanceChart;
import de.dfki.km.text20.diagnosis.gui.components.EyePositionChart;
import de.dfki.km.text20.diagnosis.gui.components.JStatusIndicator;
import de.dfki.km.text20.diagnosis.gui.components.PupilSizeChart;
import de.dfki.km.text20.diagnosis.gui.panel.templates.ServerPanelTemplate;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.BrainTrackingEventRingbuffer;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DataPartition;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DiagState;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventRingbuffer;
import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingEvent;
import de.dfki.km.text20.services.trackingdevices.common.TrackingEvent;
import de.dfki.km.text20.services.trackingdevices.common.TrackingListener;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;
import de.dfki.km.text20.trackingserver.eyes.remote.TrackingCommand;

/**
 * @author rb, ABu
 *
 */
public class ServerPanel extends ServerPanelTemplate implements TrackingListener<TrackingEvent>{

    /** */
    private static final long serialVersionUID = -7647208759232985123L;

    /** */
    ApplicationData applicationData;

    /** */
    ServerInfo serverInfo;

    /** */
    EyeTrackingEventRingbuffer eyeTrackingRingBuffer;
    
    /** */
    BrainTrackingEventRingbuffer brainTrackingRingBuffer;

    /** */
    long intervalStartMillis = new Date().getTime();

    /** */
    long intervalEndMillis;

    /** */
    int eyeTrackingEventCounter = 0;
    
    /** */
    int brainTrackingEventCounter = 0;

    /** */
    int eventRate = 0;
    
    /** */
    int brainTrackingEventRate = 0;

    /** */
    PupilSizeChart pupilSizeHistory;

    /** */
    EyeDistanceChart headDistanceHistory;

    /** */
    EyePositionChart headPositionHistory;
    
    /** */
    BrainDataChart brainHistory;

    /** */
    RecalibrationWindow recalibrationWindow;

    /** common listener to handle all hyperlink commands */
    private final ActionListener commandProcessor = new CommandListener();

    final String[] statusLabels = { "Bad", "Vage", "Ok", "Off", "On" };

    /**
     * @param appData 
     * @param serverInfo 
     */
    public ServerPanel(final ApplicationData appData, final ServerInfo serverInfo) {
        super();

        this.applicationData = appData;
        this.serverInfo = serverInfo;
        
        this.eyeTrackingRingBuffer = serverInfo.getEyeTrackingRingBuffer();
        this.brainTrackingRingBuffer = serverInfo.getBrainTrackingRingBuffer();
        
        setupWithTrackingDevice();
        setGUIParameters();
    }

    /** */
    private void setGUIParameters() {
        for (final String s : this.serverInfo.getEyeTrackingDevice().getDeviceInfo().getKeys()) {
            System.out.println(s);
        }
        
        for (final String s : this.serverInfo.getBrainTrackingDevice().getDeviceInfo().getKeys()) {
            System.out.println(s);
        }
        
        // Setting eye tracker name, type and location
        this.eyeTrackingDeviceNameValueLabel.setText(this.serverInfo.getEyeTrackingDevice().getDeviceInfo().getInfo("DEVICE_NAME"));
        this.eyeTrackingDeviceTypeValueLabel.setText(this.serverInfo.getEyeTrackingDevice().getDeviceType().name());//.getOpenDevice().getDeviceType().name());
        this.eyeTrackingDeviceLocationValueLabel.setText(this.serverInfo.getURI());
        
        // Starting recording for eye tracker
        this.serverInfo.getEyeTrackingRingBuffer().setRecording(true);
        this.recordIndicator.setStatus(DiagState.ON);
        this.recordSwitch.setText(this.statusLabels[this.recordIndicator.getStatus().ordinal()]);
        
        
        
        // Setting brain tracker name and location
        this.brainTrackingDeviceNameValueLabel.setText(this.serverInfo.getBrainTrackingDevice().getDeviceInfo().getInfo("DEVICE_NAME"));
        this.brainTrackingDeviceLocationValueLabel.setText(this.serverInfo.getURI());
        
        // Starting recording for brain tracker
        this.serverInfo.getBrainTrackingRingBuffer().setRecording(true);
        this.brainRecordIndicator.setStatus(DiagState.ON);
        this.brainTrackingRecordSwitch.setText(this.statusLabels[this.brainRecordIndicator.getStatus().ordinal()]);
  
        
        
        this.trackingSince.setText(new java.text.SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date()));

        this.eyePositionDisplay.setApplicationData(this.applicationData);
        this.eyePositionDisplay.setServerInfo(this.serverInfo);
        this.eyeDistanceDisplay.setApplicationData(this.applicationData);
        this.eyeDistanceDisplay.setServerInfo(this.serverInfo);
        this.pupilSizeDisplay.setApplicationData(this.applicationData);
        this.pupilSizeDisplay.setServerInfo(this.serverInfo);
        this.pupilSizeHistory = new PupilSizeChart(this.applicationData, this.serverInfo);
        this.headDistanceHistory = new EyeDistanceChart(this.applicationData, this.serverInfo);
        this.headPositionHistory = new EyePositionChart(this.applicationData, this.serverInfo);
        this.recalibrationWindow = new RecalibrationWindow(this.applicationData, this.serverInfo);

        this.brainHistory = new BrainDataChart(this.applicationData, this.serverInfo);
        
        this.recalibrationWindow.addWindowListener(new WindowAdapter() {

            @Override
            public void windowActivated(final WindowEvent e) {
                ((JFrame) ServerPanel.this.getRootPane().getParent()).setVisible(false);
            }

            @Override
            public void windowDeactivated(final WindowEvent e) {
                ((JFrame) ServerPanel.this.getRootPane().getParent()).setVisible(true);
            }

        });

        this.calibrationIndicator.setStatus(DiagState.VAGUE);

        this.recordSwitch.addActionListener(this.commandProcessor);
        this.brainTrackingRecordSwitch.addActionListener(this.commandProcessor);
        this.brainHistoryLink.addActionListener(this.commandProcessor);
        this.gazePositionHistoryLink.addActionListener(this.commandProcessor);
        this.headDistanceHistoryLink.addActionListener(this.commandProcessor);
        this.headPositionHistoryLink.addActionListener(this.commandProcessor);
        this.pupilSizeHistoryLink.addActionListener(this.commandProcessor);
        this.performRecalibrationLink.addActionListener(this.commandProcessor);
        this.performHardwareRecalibrationLink.addActionListener(this.commandProcessor);

        // Eye tracker buffer size setup
        this.bufferSizeSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent e) {
                final int i = ((JSlider) e.getSource()).getValue();
                ServerPanel.this.bufferSizeLabel.setText(i + "");
                ServerPanel.this.eyeTrackingRingBuffer.setRingbufferSize(i);
                ServerPanel.this.recordingTitle.setText("Eye Tracking Recording Status (" + ServerPanel.this.eyeTrackingRingBuffer.size() + " Events)");
            }
        });
        this.bufferSizeSlider.setValue(this.eyeTrackingRingBuffer.size());

        // Brain tracker buffer size setup
        this.bufferSizeBrainTrackerHistorySlider.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                final int i = ((JSlider) e.getSource()).getValue();
                ServerPanel.this.bufferSizeBrainTrackerHistoryValueLabel.setText(i + "");
                ServerPanel.this.brainTrackingRingBuffer.setRingbufferSize(i);
                ServerPanel.this.brainTrackerRecordingStatusLabel.setText("Brain Tracking Recording Status (" + ServerPanel.this.brainTrackingRingBuffer.size() + " Events)");
                
            }
        });
        this.bufferSizeBrainTrackerHistorySlider.setValue(this.brainTrackingRingBuffer.size());
        
        
//        this.serverInfo.setMainWindow(this);
        new Timer().scheduleAtFixedRate(new EventCountTask(), 1000, 1000);
        updateUI();
    }

    /**
     * Uses the given device to set up connections
     * @param device
     */
    private void setupWithTrackingDevice() {
        this.eyeTrackingRingBuffer.setRingbufferSize(1200);
        this.eyeTrackingRingBuffer.restartRecording();
        
        this.brainTrackingRingBuffer.setRingbufferSize(1200);
        this.brainTrackingRingBuffer.restartRecording();
    }

    /**
     * @param event
     */
    protected void evaluateEvent(final EyeTrackingEvent event) {
        //TODO Reactive Tray ?
        //        final DiagState newTrayState = EyeTrackingEventEvaluator.evaluateEvent(event);

        // Getting and setting the eye tracking event rate + event rate indicator
        this.getEventRate().setText("" + this.eventRate);
        final DiagState eventRateState = this.eventRate > 40 ? DiagState.OK : DiagState.BAD;
        this.getEventRateIndicator().setStatus(eventRateState);

        // Getting and setting the brain tracking event rate + event rate indicator
        this.getBrainTrackingEventRate().setText("" + this.brainTrackingEventCounter);
        
        // TODO: Check if this threshold makes sense for brain tracker
        final DiagState brainTrackingEventRateState = (this.brainTrackingEventRate > 5) ? DiagState.OK : DiagState.BAD;
        this.getBrainTrackingEventRateIndicator().setStatus(brainTrackingEventRateState);

        
        
        final Map<DataPartition, DiagState> qualityStatus = EyeTrackingEventEvaluator.getOverallQualityStatus(event);
        
        final DiagState headPositionState = qualityStatus.get(DataPartition.HEAD_POSITION);
        this.getHeadPositionIndicator().setStatus(qualityStatus.get(DataPartition.HEAD_POSITION));
        
        final DiagState headDistanceState = qualityStatus.get(DataPartition.HEAD_DISTANCE);
        this.getHeadDistanceIndicator().setStatus(headDistanceState);
        
        // TODO: Check if an overall state indicator is neccessary with eye and brain tracking enabled...
        final DiagState overallState = EyeTrackingEventEvaluator.getWorstState(headDistanceState, headPositionState, eventRateState);
        
        this.getOverallQualityIndicator().setStatus(overallState);
        this.getOverallQualityLabel().setText(this.statusLabels[overallState.ordinal()]);

        //        final ServerDiagnosisSystemTray tray = ServerPanel.this.applicationData.getTray();
        //        if (tray.getCurrentTrayState() != overallState) {
        //            tray.setTrayIcon(overallState);
        //        }
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.services.trackingdevices.TrackingListener#newEyeTrackingEvent(de.dfki.km.augmentedtext.services.trackingdevices.EyeTrackingEvent)
     */
    @Override
    public void newTrackingEvent(TrackingEvent event) {
        if (event instanceof EyeTrackingEvent) {
            this.eyeTrackingEventCounter++;
            this.eyeTrackingRingBuffer.addEvent((EyeTrackingEvent) event);
              
            evaluateEvent((EyeTrackingEvent) event);
              
            // fixed widgets
            this.eyePositionDisplay.newTrackingEvent(event);
            this.eyeDistanceDisplay.newTrackingEvent(event);
            this.pupilSizeDisplay.newTrackingEvent(event);
              
            // on demand windows
            this.pupilSizeHistory.newTrackingEvent(event);
            this.headDistanceHistory.newTrackingEvent(event);
            this.headPositionHistory.newTrackingEvent(event);
            this.recalibrationWindow.newEyeTrackingEvent((EyeTrackingEvent)event);
        }
        
        if (event instanceof BrainTrackingEvent) {
            this.brainTrackingEventCounter++;
            this.brainTrackingRingBuffer.addEvent((BrainTrackingEvent) event);
            
//            System.out.println("#### New Brain Tracking Event ####");
//            Collection<String> channels = ((BrainTrackingEvent) event).getChannels();
//            
//            for (String s : channels) {
//                System.out.println(s + " = " + ((BrainTrackingEvent) event).getValue(s));
//            }
        }
        
    }

    /**
     * Brings the window to the screen
     */
    public void showOnScreen() {
        this.setVisible(true);
    }

    /**
     * @author buhl
     * ActionListener class to process all panel events
     */
    class CommandListener implements ActionListener {

        @SuppressWarnings("synthetic-access")
        @Override
        public void actionPerformed(final ActionEvent e) {

            // process all hyperlink commands

            if (e.getSource() instanceof JXHyperlink) {
                final JXHyperlink hyperlink = (JXHyperlink) e.getSource();

//                System.out.println("----'"+hyperlink.getActionCommand()+"'-----");

                if (hyperlink.getActionCommand() == "openPupilSizeHistory") {
                    showHistoryFrame(ServerPanel.this.pupilSizeHistory, "Pupil Size History");
                }
                
                if (hyperlink.getActionCommand() == "openGazePositionHistory") {
                    // TODO GazePositionHistory implementation 
                }
                
                if (hyperlink.getActionCommand() == "openHeadPositionHistory") {
                    showHistoryFrame(ServerPanel.this.headPositionHistory, "Head Position History");
                }
                
                if (hyperlink.getActionCommand() == "openHeadDistanceHistory") {
                    showHistoryFrame(ServerPanel.this.headDistanceHistory, "Head Distance History");
                }
                
                if (hyperlink.getActionCommand() == "toggleRecording") {
                    final EyeTrackingEventRingbuffer rb = ServerPanel.this.serverInfo.getEyeTrackingRingBuffer();
                    final JStatusIndicator si = ServerPanel.this.recordIndicator;

                    rb.setRecording(!rb.isRecording());
                    si.setStatus(rb.isRecording() ? DiagState.ON : DiagState.OFF);
                    
                    ServerPanel.this.recordSwitch.setText(ServerPanel.this.statusLabels[si.getStatus().ordinal()]);
                }
                
                if (hyperlink.getActionCommand() == "toggleBrainRecording") {
                    final BrainTrackingEventRingbuffer rb = ServerPanel.this.serverInfo.getBrainTrackingRingBuffer();
                    final JStatusIndicator si = ServerPanel.this.brainRecordIndicator;

                    rb.setRecording(!rb.isRecording());
                    si.setStatus(rb.isRecording() ? DiagState.ON : DiagState.OFF);

                    ServerPanel.this.brainTrackingRecordSwitch.setText(ServerPanel.this.statusLabels[si.getStatus().ordinal()]);
                }
                
                if (hyperlink.getActionCommand() == "openBrainHistory") {
                    showHistoryFrame(ServerPanel.this.brainHistory, "Brain Data History");
                }
                
                if (hyperlink.getActionCommand() == "performRecalibration") {
                    hideEverythingElse();

                    ServerPanel.this.getCalibrationIndicator().setStatus(DiagState.OK);
                    ServerPanel.this.recalibrationWindow.setTransparency(ServerPanel.this.transparentRecalibration.isSelected());
                    ServerPanel.this.recalibrationWindow.setVisible(true);
                    ServerPanel.this.recalibrationWindow.setServerInfo(ServerPanel.this.serverInfo);
                }
                
                if (hyperlink.getActionCommand() == "performHardwareRecalibration") {
                    hideEverythingElse();
                    System.out.println("Perform HW Recalib");
                    ServerPanel.this.serverInfo.getEyeTrackingDevice().sendLowLevelCommand(TrackingCommand.HARDWARE_CALIBRATION);
                }
            }
        }

        /**
         * @param chart
         * @param title
         */
        private void showHistoryFrame(final Component chart, final String title) {
            final JFrame frame = new JFrame();
            frame.setTitle(title);
            frame.add(chart);
            frame.pack();
            frame.setVisible(true);
        }
    }

    /**
     * Hide everything else ...
     */
    public void hideEverythingElse() {
        final Collection<ServerInfo> serverInfos = this.applicationData.getServerInfos();
        for (ServerInfo si : serverInfos) {
            si.getMainWindow().setState(Frame.ICONIFIED);
        }
    }

    /**
     * @author buhl
     * implements the task used by the fixed time event rate counter 
     */
    class EventCountTask extends TimerTask {
        Date date = new Date();

        @Override
        public synchronized void run() {
            ServerPanel.this.intervalEndMillis = this.date.getTime();
            this.date = new Date();
            ServerPanel.this.intervalStartMillis = this.date.getTime();
            final long difference = ServerPanel.this.intervalStartMillis - ServerPanel.this.intervalEndMillis;
            
            // Calculating eye tracking event rate
            ServerPanel.this.eventRate = Math.round((float) ServerPanel.this.eyeTrackingEventCounter / difference * 1000);
            ServerPanel.this.eyeTrackingEventCounter = 0;
            
            // Calculating brain tracking event rate
            ServerPanel.this.brainTrackingEventRate = Math.round((float) ServerPanel.this.brainTrackingEventCounter / difference * 1000);
            ServerPanel.this.brainTrackingEventCounter = 0;
        }
    }

}
