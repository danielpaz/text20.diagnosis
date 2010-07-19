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
import de.dfki.km.text20.diagnosis.gui.components.EyeDistanceChart;
import de.dfki.km.text20.diagnosis.gui.components.EyePositionChart;
import de.dfki.km.text20.diagnosis.gui.components.JStatusIndicator;
import de.dfki.km.text20.diagnosis.gui.components.PupilSizeChart;
import de.dfki.km.text20.diagnosis.gui.panel.templates.ServerPanelTemplate;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventRingbuffer;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DataPartition;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DiagState;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingListener;
import de.dfki.km.text20.trackingserver.eyes.remote.TrackingCommand;

/**
 * @author rb, ABu
 *
 */
public class ServerPanel extends ServerPanelTemplate implements EyeTrackingListener {

    /** */
    private static final long serialVersionUID = -7647208759232985123L;

    /** */
    ApplicationData applicationData;

    /** */
    ServerInfo serverInfo;

    /** */
    EyeTrackingEventRingbuffer ringBuffer;

    /** */
    long intervalStartMillis = new Date().getTime();

    /** */
    long intervalEndMillis;

    /** */
    int eventCounter = 0;

    /** */
    int eventRate = 0;

    /** */
    PupilSizeChart pupilSizeHistory;

    /** */
    EyeDistanceChart headDistanceHistory;

    /** */
    EyePositionChart headPositionHistory;

    /** */
    RecalibrationWindow recalibrationWindow;

    /** common listener to handle all hyperlink commands */
    private final ActionListener commandProcessor = new CommandListener();

    String[] statusLabels = { "Bad", "Vage", "Ok", "Off", "On" };

    /**
     * @param ad 
     * @param si 
     */
    public ServerPanel(final ApplicationData ad, final ServerInfo si) {
        super();

        this.applicationData = ad;
        this.serverInfo = si;
        this.ringBuffer = si.getRingBuffer();
        setupWithTrackingDevice();
        setGUIParameters();
    }

    /** */
    private void setGUIParameters() {
        for (String s : this.serverInfo.getDevice().getDeviceInfo().getKeys()) {
            System.out.println(s);
        }
        this.deviceName.setText(this.serverInfo.getDevice().getDeviceInfo().getInfo("DEVICE_NAME"));
        this.deviceType.setText(this.serverInfo.getDevice().getDeviceType().name());//.getOpenDevice().getDeviceType().name());
        this.deviceLocation.setText(this.serverInfo.getURI());

        this.trackingSince.setText(new java.text.SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date()));

        this.eyePositionDisplay.setApplicationData(this.applicationData);
        this.eyePositionDisplay.setServerInfo(this.serverInfo);
        this.eyeDistanceDisplay.setApplicationData(this.applicationData);
        this.eyeDistanceDisplay.setServerInfo(this.serverInfo);
        this.pupilSizeDisplay1.setApplicationData(this.applicationData);
        this.pupilSizeDisplay1.setServerInfo(this.serverInfo);
        this.pupilSizeHistory = new PupilSizeChart(this.applicationData, this.serverInfo);
        this.headDistanceHistory = new EyeDistanceChart(this.applicationData, this.serverInfo);
        this.headPositionHistory = new EyePositionChart(this.applicationData, this.serverInfo);
        this.recalibrationWindow = new RecalibrationWindow(this.applicationData, this.serverInfo);

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

        this.serverInfo.getRingBuffer().setRecording(true);
        this.recordIndicator.setStatus(DiagState.ON);
        this.recordSwitch.setText(this.statusLabels[this.recordIndicator.getStatus().ordinal()]);

        this.calibrationIndicator.setStatus(DiagState.VAGUE);

        this.recordSwitch.addActionListener(this.commandProcessor);
        this.gazePositionHistoryLink.addActionListener(this.commandProcessor);
        this.headDistanceHistoryLink.addActionListener(this.commandProcessor);
        this.headPositionHistoryLink.addActionListener(this.commandProcessor);
        this.pupilSizeHistoryLink.addActionListener(this.commandProcessor);
        this.performRecalibrationLink.addActionListener(this.commandProcessor);
        this.performHardwareRecalibrationLink.addActionListener(this.commandProcessor);

        this.bufferSizeSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent e) {
                final int i = ((JSlider) e.getSource()).getValue();
                ServerPanel.this.bufferSizeLabel.setText(i + "");
                ServerPanel.this.ringBuffer.setRingbufferSize(i);
                ServerPanel.this.recordingTitle.setText("Recording Status (" + ServerPanel.this.ringBuffer.size() + " Events)");
            }
        });

        this.bufferSizeSlider.setValue(this.ringBuffer.size());

        //        this.serverInfo.setMainWindow(this);
        new Timer().scheduleAtFixedRate(new EventCountTask(), 1000, 1000);
        updateUI();
    }

    /**
     * Uses the given device to set up connections
     * @param device
     */
    private void setupWithTrackingDevice() {
        this.ringBuffer.setRingbufferSize(1200);
        this.ringBuffer.restartRecording();
    }

    /**
     * @param event
     */
    protected void evaluateEvent(final EyeTrackingEvent event) {
        //TODO Reactive Tray ?
        //        final DiagState newTrayState = EyeTrackingEventEvaluator.evaluateEvent(event);

        this.getEventRate().setText("" + this.eventRate);

        final Map<DataPartition, DiagState> qualityStatus = EyeTrackingEventEvaluator.getOverallQualityStatus(event);
        final DiagState hpState = qualityStatus.get(DataPartition.HEAD_POSITION);
        this.getHeadPositionIndicator().setStatus(hpState);
        final DiagState hdState = qualityStatus.get(DataPartition.HEAD_DISTANCE);
        this.getHeadDistanceIndicator().setStatus(hdState);
        final DiagState eventRateState = this.eventRate > 40 ? DiagState.OK : DiagState.BAD;
        this.getEventRateIndicator().setStatus(eventRateState);

        final DiagState overallState = EyeTrackingEventEvaluator.getWorstState(hdState, hpState, eventRateState);
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
    public void newTrackingEvent(final EyeTrackingEvent event) {

        this.eventCounter++;
        this.ringBuffer.addEvent(event);

        evaluateEvent(event);

        // fixed widgets
        this.eyePositionDisplay.newEyeTrackingEvent(event);
        this.eyeDistanceDisplay.newEyeTrackingEvent(event);
        this.pupilSizeDisplay1.newEyeTrackingEvent(event);

        // on demand windows
        this.pupilSizeHistory.newEyeTrackingEvent(event);
        this.headDistanceHistory.newEyeTrackingEvent(event);
        this.headPositionHistory.newEyeTrackingEvent(event);
        this.recalibrationWindow.newEyeTrackingEvent(event);

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
                final JXHyperlink hl = (JXHyperlink) e.getSource();

                //                System.out.println("----'"+hl.getActionCommand()+"'-----");

                if (hl.getActionCommand() == "openPupilSizeHistory") {
                    showHistoryFrame(ServerPanel.this.pupilSizeHistory, "Pupil Size History");
                }
                if (hl.getActionCommand() == "openGazePositionHistory") {
                    // TODO GazePositionHistory implementation 
                }
                if (hl.getActionCommand() == "openHeadPositionHistory") {
                    showHistoryFrame(ServerPanel.this.headPositionHistory, "Head Position History");
                }
                if (hl.getActionCommand() == "openHeadDistanceHistory") {
                    showHistoryFrame(ServerPanel.this.headDistanceHistory, "Head Distance History");
                }
                if (hl.getActionCommand() == "toggleRecording") {
                    final EyeTrackingEventRingbuffer rb = ServerPanel.this.serverInfo.getRingBuffer();
                    final JStatusIndicator si = ServerPanel.this.recordIndicator;

                    rb.setRecording(!rb.isRecording());
                    if (rb.isRecording()) {
                        si.setStatus(DiagState.ON);
                    } else {
                        si.setStatus(DiagState.OFF);
                    }
                    ServerPanel.this.recordSwitch.setText(ServerPanel.this.statusLabels[si.getStatus().ordinal()]);
                }
                if (hl.getActionCommand() == "performRecalibration") {
                    hideEverythingElse();

                    ServerPanel.this.getCalibrationIndicator().setStatus(DiagState.OK);
                    ServerPanel.this.recalibrationWindow.setTransparency(ServerPanel.this.transparentRecalibration.isSelected());
                    ServerPanel.this.recalibrationWindow.setVisible(true);
                    ServerPanel.this.recalibrationWindow.setServerInfo(ServerPanel.this.serverInfo);
                }
                if (hl.getActionCommand() == "performHardwareRecalibration") {
                    hideEverythingElse();
                    System.out.println("Perform HW Recalib");
                    ServerPanel.this.serverInfo.getDevice().sendLowLevelCommand(TrackingCommand.HARDWARE_CALIBRATION);
                }
                // */
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
            ServerPanel.this.eventRate = Math.round((float) ServerPanel.this.eventCounter / difference * 1000);
            ServerPanel.this.eventCounter = 0;
        }
    }
}
