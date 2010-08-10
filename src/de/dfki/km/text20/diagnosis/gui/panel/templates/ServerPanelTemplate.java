/*
 * Created by JFormDesigner on Mon Jul 26 14:40:41 CEST 2010
 */

package de.dfki.km.text20.diagnosis.gui.panel.templates;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXHyperlink;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.dfki.km.text20.diagnosis.gui.components.EyeDistanceDisplay;
import de.dfki.km.text20.diagnosis.gui.components.EyePositionDisplay;
import de.dfki.km.text20.diagnosis.gui.components.JStatusIndicator;
import de.dfki.km.text20.diagnosis.gui.components.PupilSizeDisplay;

/**
 * @author Ralf Biedert
 */
public class ServerPanelTemplate extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 5166137640013392483L;

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    protected JPanel visualEyeTrackingDataPanel;
    protected EyePositionDisplay eyePositionDisplay;
    protected EyeDistanceDisplay eyeDistanceDisplay;
    protected PupilSizeDisplay pupilSizeDisplay;
    private JTabbedPane tabbedPane;
    protected JPanel overallPanel;
    private JLabel eyeTrackingDeviceNameLabel;
    protected JLabel eyeTrackingDeviceNameValueLabel;
    private JLabel eyeTrackingDeviceTypeLabel;
    protected JLabel eyeTrackingDeviceTypeValueLabel;
    private JLabel eyeTrackingDeviceLocationLabel;
    protected JLabel eyeTrackingDeviceLocationValueLabel;
    public JLabel recordingTitle;
    public JStatusIndicator recordIndicator;
    public JXHyperlink recordSwitch;
    private JLabel eyeTrackingEventRateLabel;
    protected JStatusIndicator eventRateIndicator;
    protected JLabel eventRate;
    private JLabel qualityLabel;
    protected JStatusIndicator overallQualityIndicator;
    private JLabel overallQualityLabel;
    private JLabel brainTrackingDeviceNameLabel;
    protected JLabel brainTrackingDeviceNameValueLabel;
    private JLabel brainTrackingDeviceLocationLabel;
    protected JLabel brainTrackingDeviceLocationValueLabel;
    public JLabel brainTrackerRecordingStatusLabel;
    public JStatusIndicator brainRecordIndicator;
    public JXHyperlink brainTrackingRecordSwitch;
    private JLabel brainTrackingEventRateLabel;
    protected JStatusIndicator brainTrackingEventRateIndicator;
    protected JLabel brainTrackingEventRate;
    private JLabel deviceTrackingSinceLabel;
    protected JLabel trackingSince;
    private JLabel brainHistoryLabel;
    protected JXHyperlink brainHistoryLink;
    private JLabel gazePositionLabel;
    protected JXHyperlink gazePositionHistoryLink;
    private JLabel headPositionLabel;
    protected JStatusIndicator headPositionIndicator;
    protected JXHyperlink headPositionHistoryLink;
    private JLabel headDistanceLabel;
    protected JStatusIndicator headDistanceIndicator;
    protected JXHyperlink headDistanceHistoryLink;
    private JLabel pupilSizeLabel;
    protected JXHyperlink pupilSizeHistoryLink;
    private JLabel hardwareRecalibrationLabel;
    protected JXHyperlink performHardwareRecalibrationLink;
    private JLabel localRecalibrationVerificationLabel;
    protected JStatusIndicator calibrationIndicator;
    protected JXHyperlink performRecalibrationLink;
    private JPanel panel3;
    private JScrollPane scrollPane4;
    protected JTextPane logText;
    private JPanel settingsPanel;
    private JLabel bufferSizeEyeTrackerHistoryLabel;
    public JLabel bufferSizeLabel;
    public JSlider bufferSizeSlider;
    private JLabel bufferSizeBrainTrackerHistoryLabel;
    public JLabel bufferSizeBrainTrackerHistoryValueLabel;
    public JSlider bufferSizeBrainTrackerHistorySlider;
    private JLabel transparentLocalRecalibrationLabel2;
    public JCheckBox ministatus;
    private JLabel transparentLocalRecalibrationLabel;
    protected JCheckBox transparentRecalibration;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public ServerPanelTemplate() {
        initComponents();
    }

    public JSlider getBufferSizeSlider() {
        return this.bufferSizeSlider;
    }

    public JStatusIndicator getCalibrationIndicator() {
        return this.calibrationIndicator;
    }

    public JLabel getBrainTrackingEventRate() {
        return this.brainTrackingEventRate;
    }

    public JStatusIndicator getBrainTrackingEventRateIndicator() {
        return this.brainTrackingEventRateIndicator;
    }

    public JStatusIndicator getHeadDistanceIndicator() {
        return this.headDistanceIndicator;
    }

    public JStatusIndicator getHeadPositionIndicator() {
        return this.headPositionIndicator;
    }

    public JStatusIndicator getOverallQualityIndicator() {
        return this.overallQualityIndicator;
    }

    public JLabel getOverallQualityLabel() {
        return this.overallQualityLabel;
    }

    public JXHyperlink getPerformHardwareRecalibrationLink() {
        return this.performHardwareRecalibrationLink;
    }

    public JXHyperlink getPupilSizeHistoryLink() {
        return this.pupilSizeHistoryLink;
    }

    public JStatusIndicator getRecordIndicator() {
        return this.recordIndicator;
    }

    public JPanel getVisualEyeTrackingDataPanel() {
        return this.visualEyeTrackingDataPanel;
    }

    public JStatusIndicator getBrainRecordIndicator() {
        return this.brainRecordIndicator;
    }

    public JStatusIndicator getEventRateIndicator() {
        return this.eventRateIndicator;
    }

    public JLabel getEventRate() {
        return this.eventRate;
    }

    public JXHyperlink getPerformRecalibrationLink() {
        return this.performRecalibrationLink;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        visualEyeTrackingDataPanel = new JPanel();
        eyePositionDisplay = new EyePositionDisplay();
        eyeDistanceDisplay = new EyeDistanceDisplay();
        pupilSizeDisplay = new PupilSizeDisplay();
        tabbedPane = new JTabbedPane();
        overallPanel = new JPanel();
        eyeTrackingDeviceNameLabel = new JLabel();
        eyeTrackingDeviceNameValueLabel = new JLabel();
        eyeTrackingDeviceTypeLabel = new JLabel();
        eyeTrackingDeviceTypeValueLabel = new JLabel();
        eyeTrackingDeviceLocationLabel = new JLabel();
        eyeTrackingDeviceLocationValueLabel = new JLabel();
        recordingTitle = new JLabel();
        recordIndicator = new JStatusIndicator();
        recordSwitch = new JXHyperlink();
        eyeTrackingEventRateLabel = new JLabel();
        eventRateIndicator = new JStatusIndicator();
        eventRate = new JLabel();
        qualityLabel = new JLabel();
        overallQualityIndicator = new JStatusIndicator();
        overallQualityLabel = new JLabel();
        brainTrackingDeviceNameLabel = new JLabel();
        brainTrackingDeviceNameValueLabel = new JLabel();
        brainTrackingDeviceLocationLabel = new JLabel();
        brainTrackingDeviceLocationValueLabel = new JLabel();
        brainTrackerRecordingStatusLabel = new JLabel();
        brainRecordIndicator = new JStatusIndicator();
        brainTrackingRecordSwitch = new JXHyperlink();
        brainTrackingEventRateLabel = new JLabel();
        brainTrackingEventRateIndicator = new JStatusIndicator();
        brainTrackingEventRate = new JLabel();
        deviceTrackingSinceLabel = new JLabel();
        trackingSince = new JLabel();
        brainHistoryLabel = new JLabel();
        brainHistoryLink = new JXHyperlink();
        gazePositionLabel = new JLabel();
        gazePositionHistoryLink = new JXHyperlink();
        headPositionLabel = new JLabel();
        headPositionIndicator = new JStatusIndicator();
        headPositionHistoryLink = new JXHyperlink();
        headDistanceLabel = new JLabel();
        headDistanceIndicator = new JStatusIndicator();
        headDistanceHistoryLink = new JXHyperlink();
        pupilSizeLabel = new JLabel();
        pupilSizeHistoryLink = new JXHyperlink();
        hardwareRecalibrationLabel = new JLabel();
        performHardwareRecalibrationLink = new JXHyperlink();
        localRecalibrationVerificationLabel = new JLabel();
        calibrationIndicator = new JStatusIndicator();
        performRecalibrationLink = new JXHyperlink();
        panel3 = new JPanel();
        scrollPane4 = new JScrollPane();
        logText = new JTextPane();
        settingsPanel = new JPanel();
        bufferSizeEyeTrackerHistoryLabel = new JLabel();
        bufferSizeLabel = new JLabel();
        bufferSizeSlider = new JSlider();
        bufferSizeBrainTrackerHistoryLabel = new JLabel();
        bufferSizeBrainTrackerHistoryValueLabel = new JLabel();
        bufferSizeBrainTrackerHistorySlider = new JSlider();
        transparentLocalRecalibrationLabel2 = new JLabel();
        ministatus = new JCheckBox();
        transparentLocalRecalibrationLabel = new JLabel();
        transparentRecalibration = new JCheckBox();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setLayout(new FormLayout(
            "default:grow",
            "min, $lgap, 0dlu, $lgap, fill:[200dlu,pref]:grow"));

        //======== visualEyeTrackingDataPanel ========
        {
            visualEyeTrackingDataPanel.setBorder(new TitledBorder(null, "Visual Tracking Data", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
            visualEyeTrackingDataPanel.setPreferredSize(new Dimension(506, 500));
            visualEyeTrackingDataPanel.setOpaque(false);
            visualEyeTrackingDataPanel.setRequestFocusEnabled(false);
            visualEyeTrackingDataPanel.setVerifyInputWhenFocusTarget(false);
            visualEyeTrackingDataPanel.setAlignmentY(1.5F);
            visualEyeTrackingDataPanel.setLayout(new FormLayout(
                "default:grow, 2*($lcgap, default)",
                "fill:150dlu"));
            ((FormLayout)visualEyeTrackingDataPanel.getLayout()).setColumnGroups(new int[][] {{3, 5}});

            //---- eyePositionDisplay ----
            eyePositionDisplay.setPreferredSize(new Dimension(300, 200));
            eyePositionDisplay.setBorder(new EmptyBorder(5, 5, 5, 5));
            eyePositionDisplay.setMinimumSize(new Dimension(1, 200));
            eyePositionDisplay.setEyePostionCanvasBackgroundColor(Color.darkGray);
            visualEyeTrackingDataPanel.add(eyePositionDisplay, cc.xy(1, 1));

            //---- eyeDistanceDisplay ----
            eyeDistanceDisplay.setPreferredSize(new Dimension(50, 300));
            eyeDistanceDisplay.setMinimumSize(new Dimension(1, 300));
            eyeDistanceDisplay.setPaddingVal(4);
            eyeDistanceDisplay.setBackground(null);
            eyeDistanceDisplay.setForeground(Color.darkGray);
            eyeDistanceDisplay.setEyeDistanceCanvasColor(Color.darkGray);
            visualEyeTrackingDataPanel.add(eyeDistanceDisplay, new CellConstraints(3, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets( 5, 0, 5, 0)));

            //---- pupilSizeDisplay ----
            pupilSizeDisplay.setPreferredSize(new Dimension(60, 300));
            pupilSizeDisplay.setMinimumSize(new Dimension(1, 300));
            pupilSizeDisplay.setPupilSizeCanvasBackgroundColor(Color.darkGray);
            visualEyeTrackingDataPanel.add(pupilSizeDisplay, new CellConstraints(5, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets( 5, 0, 5, 0)));
        }
        add(visualEyeTrackingDataPanel, cc.xywh(1, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));

        //======== tabbedPane ========
        {

            //======== overallPanel ========
            {
                overallPanel.setLayout(new FormLayout(
                    "150dlu, $lcgap, default, 6dlu, default:grow",
                    "default, $lgap, fill:default, 21*($lgap, default)"));
                ((FormLayout)overallPanel.getLayout()).setRowGroups(new int[][] {{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43}});

                //---- eyeTrackingDeviceNameLabel ----
                eyeTrackingDeviceNameLabel.setText("Eye Tracking Device Name");
                overallPanel.add(eyeTrackingDeviceNameLabel, cc.xy(1, 1));

                //---- eyeTrackingDeviceNameValueLabel ----
                eyeTrackingDeviceNameValueLabel.setText("-");
                overallPanel.add(eyeTrackingDeviceNameValueLabel, cc.xy(5, 1));

                //---- eyeTrackingDeviceTypeLabel ----
                eyeTrackingDeviceTypeLabel.setText("Eye Tracking Device Type");
                overallPanel.add(eyeTrackingDeviceTypeLabel, cc.xy(1, 3));

                //---- eyeTrackingDeviceTypeValueLabel ----
                eyeTrackingDeviceTypeValueLabel.setText("-");
                overallPanel.add(eyeTrackingDeviceTypeValueLabel, cc.xy(5, 3));

                //---- eyeTrackingDeviceLocationLabel ----
                eyeTrackingDeviceLocationLabel.setText("Eye Tracking Device Location");
                overallPanel.add(eyeTrackingDeviceLocationLabel, cc.xy(1, 5));

                //---- eyeTrackingDeviceLocationValueLabel ----
                eyeTrackingDeviceLocationValueLabel.setText("-");
                overallPanel.add(eyeTrackingDeviceLocationValueLabel, cc.xy(5, 5));

                //---- recordingTitle ----
                recordingTitle.setText("Eye Tracker Recording Status");
                overallPanel.add(recordingTitle, cc.xy(1, 7));
                overallPanel.add(recordIndicator, cc.xy(3, 7));

                //---- recordSwitch ----
                recordSwitch.setText("Off");
                recordSwitch.setActionCommand("toggleRecording");
                overallPanel.add(recordSwitch, cc.xy(5, 7));

                //---- eyeTrackingEventRateLabel ----
                eyeTrackingEventRateLabel.setText("Eye Tracker Event Rate [events per second]");
                overallPanel.add(eyeTrackingEventRateLabel, cc.xy(1, 9));
                overallPanel.add(eventRateIndicator, cc.xy(3, 9));

                //---- eventRate ----
                eventRate.setText("0");
                overallPanel.add(eventRate, cc.xy(5, 9));

                //---- qualityLabel ----
                qualityLabel.setText("Overall Eye Tracking Quality");
                overallPanel.add(qualityLabel, cc.xy(1, 11));
                overallPanel.add(overallQualityIndicator, cc.xy(3, 11));

                //---- overallQualityLabel ----
                overallQualityLabel.setText("Unknown");
                overallPanel.add(overallQualityLabel, cc.xy(5, 11));

                //---- brainTrackingDeviceNameLabel ----
                brainTrackingDeviceNameLabel.setText("Brain Tracking Device Name");
                overallPanel.add(brainTrackingDeviceNameLabel, cc.xy(1, 15));

                //---- brainTrackingDeviceNameValueLabel ----
                brainTrackingDeviceNameValueLabel.setText("-");
                overallPanel.add(brainTrackingDeviceNameValueLabel, cc.xy(5, 15));

                //---- brainTrackingDeviceLocationLabel ----
                brainTrackingDeviceLocationLabel.setText("Brain Tracking Device Location");
                overallPanel.add(brainTrackingDeviceLocationLabel, cc.xy(1, 17));

                //---- brainTrackingDeviceLocationValueLabel ----
                brainTrackingDeviceLocationValueLabel.setText("-");
                overallPanel.add(brainTrackingDeviceLocationValueLabel, cc.xy(5, 17));

                //---- brainTrackerRecordingStatusLabel ----
                brainTrackerRecordingStatusLabel.setText("Brain Tracker Recording Status");
                overallPanel.add(brainTrackerRecordingStatusLabel, cc.xy(1, 19));
                overallPanel.add(brainRecordIndicator, cc.xy(3, 19));

                //---- brainTrackingRecordSwitch ----
                brainTrackingRecordSwitch.setText("Off");
                brainTrackingRecordSwitch.setActionCommand("toggleBrainRecording");
                overallPanel.add(brainTrackingRecordSwitch, cc.xy(5, 19));

                //---- brainTrackingEventRateLabel ----
                brainTrackingEventRateLabel.setText("Brain Tracker Event Rate [events per second]");
                overallPanel.add(brainTrackingEventRateLabel, cc.xy(1, 21));
                overallPanel.add(brainTrackingEventRateIndicator, cc.xy(3, 21));

                //---- brainTrackingEventRate ----
                brainTrackingEventRate.setText("0");
                overallPanel.add(brainTrackingEventRate, cc.xy(5, 21));

                //---- deviceTrackingSinceLabel ----
                deviceTrackingSinceLabel.setText("Device Tracking Since");
                overallPanel.add(deviceTrackingSinceLabel, cc.xy(1, 25));

                //---- trackingSince ----
                trackingSince.setText("Not tracking");
                overallPanel.add(trackingSince, cc.xy(5, 25));

                //---- brainHistoryLabel ----
                brainHistoryLabel.setText("Brain History");
                overallPanel.add(brainHistoryLabel, cc.xy(1, 29));

                //---- brainHistoryLink ----
                brainHistoryLink.setText("Show brain history ...");
                brainHistoryLink.setActionCommand("openBrainHistory");
                overallPanel.add(brainHistoryLink, cc.xy(5, 29));

                //---- gazePositionLabel ----
                gazePositionLabel.setText("Gaze Position [normalized]");
                overallPanel.add(gazePositionLabel, cc.xy(1, 33));

                //---- gazePositionHistoryLink ----
                gazePositionHistoryLink.setText("Gaze position history ...");
                gazePositionHistoryLink.setActionCommand("openGazePositionHistory");
                overallPanel.add(gazePositionHistoryLink, cc.xy(5, 33));

                //---- headPositionLabel ----
                headPositionLabel.setText("Head Position [normalized]");
                overallPanel.add(headPositionLabel, cc.xy(1, 35));
                overallPanel.add(headPositionIndicator, cc.xy(3, 35));

                //---- headPositionHistoryLink ----
                headPositionHistoryLink.setText("Head position history ...");
                headPositionHistoryLink.setActionCommand("openHeadPositionHistory");
                overallPanel.add(headPositionHistoryLink, cc.xy(5, 35));

                //---- headDistanceLabel ----
                headDistanceLabel.setText("Head Distance [mm]");
                overallPanel.add(headDistanceLabel, cc.xy(1, 37));
                overallPanel.add(headDistanceIndicator, cc.xy(3, 37));

                //---- headDistanceHistoryLink ----
                headDistanceHistoryLink.setText("Head distance history ...");
                headDistanceHistoryLink.setActionCommand("openHeadDistanceHistory");
                overallPanel.add(headDistanceHistoryLink, cc.xy(5, 37));

                //---- pupilSizeLabel ----
                pupilSizeLabel.setText("Pupil Size [mm]");
                overallPanel.add(pupilSizeLabel, cc.xy(1, 39));

                //---- pupilSizeHistoryLink ----
                pupilSizeHistoryLink.setText("Pupilsize history ...");
                pupilSizeHistoryLink.setActionCommand("openPupilSizeHistory");
                overallPanel.add(pupilSizeHistoryLink, cc.xy(5, 39));

                //---- hardwareRecalibrationLabel ----
                hardwareRecalibrationLabel.setText("Hardware Recalibration");
                overallPanel.add(hardwareRecalibrationLabel, cc.xy(1, 43));

                //---- performHardwareRecalibrationLink ----
                performHardwareRecalibrationLink.setText("Perfom hardware recalibration ...");
                performHardwareRecalibrationLink.setActionCommand("performHardwareRecalibration");
                overallPanel.add(performHardwareRecalibrationLink, cc.xy(5, 43));

                //---- localRecalibrationVerificationLabel ----
                localRecalibrationVerificationLabel.setText("Local Recalibration & Verficiation");
                overallPanel.add(localRecalibrationVerificationLabel, cc.xy(1, 45));
                overallPanel.add(calibrationIndicator, cc.xy(3, 45));

                //---- performRecalibrationLink ----
                performRecalibrationLink.setText("Perfom local recalibration ...");
                performRecalibrationLink.setActionCommand("performRecalibration");
                overallPanel.add(performRecalibrationLink, cc.xy(5, 45));
            }
            tabbedPane.addTab("Overview", overallPanel);


            //======== panel3 ========
            {
                panel3.setLayout(new FormLayout(
                    "default:grow",
                    "fill:default:grow"));

                //======== scrollPane4 ========
                {
                    scrollPane4.setViewportView(logText);
                }
                panel3.add(scrollPane4, cc.xy(1, 1));
            }
            tabbedPane.addTab("Message Log", panel3);


            //======== settingsPanel ========
            {
                settingsPanel.setLayout(new FormLayout(
                    "150dlu, $lcgap, 50dlu, 6dlu, default:grow",
                    "6*(default, $lgap), default"));
                ((FormLayout)settingsPanel.getLayout()).setRowGroups(new int[][] {{1, 3, 5, 7, 9, 11}});

                //---- bufferSizeEyeTrackerHistoryLabel ----
                bufferSizeEyeTrackerHistoryLabel.setText("Buffer Size Eye Tracker History [#Events]");
                settingsPanel.add(bufferSizeEyeTrackerHistoryLabel, cc.xy(1, 1));

                //---- bufferSizeLabel ----
                bufferSizeLabel.setText("300");
                settingsPanel.add(bufferSizeLabel, cc.xy(3, 1));

                //---- bufferSizeSlider ----
                bufferSizeSlider.setMinimum(300);
                bufferSizeSlider.setMaximum(6000);
                bufferSizeSlider.setMajorTickSpacing(10);
                bufferSizeSlider.setSnapToTicks(true);
                settingsPanel.add(bufferSizeSlider, cc.xy(5, 1));

                //---- bufferSizeBrainTrackerHistoryLabel ----
                bufferSizeBrainTrackerHistoryLabel.setText("Buffer Size Brain Tracker History [#Events]");
                settingsPanel.add(bufferSizeBrainTrackerHistoryLabel, cc.xy(1, 3));

                //---- bufferSizeBrainTrackerHistoryValueLabel ----
                bufferSizeBrainTrackerHistoryValueLabel.setText("300");
                settingsPanel.add(bufferSizeBrainTrackerHistoryValueLabel, cc.xy(3, 3));

                //---- bufferSizeBrainTrackerHistorySlider ----
                bufferSizeBrainTrackerHistorySlider.setMaximum(2000);
                bufferSizeBrainTrackerHistorySlider.setMinimum(300);
                bufferSizeBrainTrackerHistorySlider.setSnapToTicks(true);
                bufferSizeBrainTrackerHistorySlider.setMajorTickSpacing(10);
                settingsPanel.add(bufferSizeBrainTrackerHistorySlider, cc.xy(5, 3));

                //---- transparentLocalRecalibrationLabel2 ----
                transparentLocalRecalibrationLabel2.setText("Display Ministatus");
                settingsPanel.add(transparentLocalRecalibrationLabel2, cc.xy(1, 7));

                //---- ministatus ----
                ministatus.setText("Ministatus");
                settingsPanel.add(ministatus, cc.xy(5, 7));

                //---- transparentLocalRecalibrationLabel ----
                transparentLocalRecalibrationLabel.setText("Transparent Local Recalibration");
                settingsPanel.add(transparentLocalRecalibrationLabel, cc.xy(1, 11));

                //---- transparentRecalibration ----
                transparentRecalibration.setText("Transparent");
                settingsPanel.add(transparentRecalibration, cc.xy(5, 11));
            }
            tabbedPane.addTab("Settings", settingsPanel);

        }
        add(tabbedPane, cc.xy(1, 5));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
