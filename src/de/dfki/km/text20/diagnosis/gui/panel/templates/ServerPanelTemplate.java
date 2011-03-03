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
    private JLabel brainTrackingDeviceNameLabel;
    protected JLabel brainTrackingDeviceNameValueLabel;
    private JLabel brainTrackingDeviceLocationLabel;
    protected JLabel brainTrackingDeviceLocationValueLabel;
    public JLabel brainTrackerRecordingStatusLabel;
    public JStatusIndicator brainRecordIndicator;
    public JXHyperlink brainTrackingRecordSwitch;
    private JLabel brainTrackingEventRateLabel;
    protected JStatusIndicator brainTrackingEventRateIndicator;
    protected JLabel brainTrackingEventRateValue;
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
    private JLabel qualityLabel;
    protected JStatusIndicator overallQualityIndicator;
    private JLabel overallQualityLabel;
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
    protected JPanel ChannelPanel;
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

    public JLabel getBrainTrackingEventRateValue() {
        return this.brainTrackingEventRateValue;
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

    @SuppressWarnings("unqualified-field-access")
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        this.visualEyeTrackingDataPanel = new JPanel();
        this.eyePositionDisplay = new EyePositionDisplay();
        this.eyeDistanceDisplay = new EyeDistanceDisplay();
        this.pupilSizeDisplay = new PupilSizeDisplay();
        this.tabbedPane = new JTabbedPane();
        this.overallPanel = new JPanel();
        this.eyeTrackingDeviceNameLabel = new JLabel();
        this.eyeTrackingDeviceNameValueLabel = new JLabel();
        this.eyeTrackingDeviceTypeLabel = new JLabel();
        this.eyeTrackingDeviceTypeValueLabel = new JLabel();
        this.eyeTrackingDeviceLocationLabel = new JLabel();
        this.eyeTrackingDeviceLocationValueLabel = new JLabel();
        this.recordingTitle = new JLabel();
        this.recordIndicator = new JStatusIndicator();
        this.recordSwitch = new JXHyperlink();
        this.eyeTrackingEventRateLabel = new JLabel();
        this.eventRateIndicator = new JStatusIndicator();
        this.eventRate = new JLabel();
        this.brainTrackingDeviceNameLabel = new JLabel();
        this.brainTrackingDeviceNameValueLabel = new JLabel();
        this.brainTrackingDeviceLocationLabel = new JLabel();
        this.brainTrackingDeviceLocationValueLabel = new JLabel();
        this.brainTrackerRecordingStatusLabel = new JLabel();
        this.brainRecordIndicator = new JStatusIndicator();
        this.brainTrackingRecordSwitch = new JXHyperlink();
        this.brainTrackingEventRateLabel = new JLabel();
        this.brainTrackingEventRateIndicator = new JStatusIndicator();
        this.brainTrackingEventRateValue = new JLabel();
        this.deviceTrackingSinceLabel = new JLabel();
        this.trackingSince = new JLabel();
        this.brainHistoryLabel = new JLabel();
        this.brainHistoryLink = new JXHyperlink();
        this.gazePositionLabel = new JLabel();
        this.gazePositionHistoryLink = new JXHyperlink();
        this.headPositionLabel = new JLabel();
        this.headPositionIndicator = new JStatusIndicator();
        this.headPositionHistoryLink = new JXHyperlink();
        this.headDistanceLabel = new JLabel();
        this.headDistanceIndicator = new JStatusIndicator();
        this.headDistanceHistoryLink = new JXHyperlink();
        this.pupilSizeLabel = new JLabel();
        this.pupilSizeHistoryLink = new JXHyperlink();
        this.hardwareRecalibrationLabel = new JLabel();
        this.performHardwareRecalibrationLink = new JXHyperlink();
        this.localRecalibrationVerificationLabel = new JLabel();
        this.calibrationIndicator = new JStatusIndicator();
        this.performRecalibrationLink = new JXHyperlink();
        this.qualityLabel = new JLabel();
        this.overallQualityIndicator = new JStatusIndicator();
        this.overallQualityLabel = new JLabel();
        this.panel3 = new JPanel();
        this.scrollPane4 = new JScrollPane();
        this.logText = new JTextPane();
        this.settingsPanel = new JPanel();
        this.bufferSizeEyeTrackerHistoryLabel = new JLabel();
        this.bufferSizeLabel = new JLabel();
        this.bufferSizeSlider = new JSlider();
        this.bufferSizeBrainTrackerHistoryLabel = new JLabel();
        this.bufferSizeBrainTrackerHistoryValueLabel = new JLabel();
        this.bufferSizeBrainTrackerHistorySlider = new JSlider();
        this.transparentLocalRecalibrationLabel2 = new JLabel();
        this.ministatus = new JCheckBox();
        this.transparentLocalRecalibrationLabel = new JLabel();
        this.transparentRecalibration = new JCheckBox();
        this.ChannelPanel = new JPanel();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setLayout(new FormLayout(
            "default:grow",
            "min, $lgap, 0dlu, $lgap, fill:[200dlu,pref]:grow"));

        //======== visualEyeTrackingDataPanel ========
        {
            this.visualEyeTrackingDataPanel.setBorder(new TitledBorder(null, "Visual Tracking Data", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
            this.visualEyeTrackingDataPanel.setPreferredSize(new Dimension(506, 500));
            this.visualEyeTrackingDataPanel.setOpaque(false);
            this.visualEyeTrackingDataPanel.setRequestFocusEnabled(false);
            this.visualEyeTrackingDataPanel.setVerifyInputWhenFocusTarget(false);
            this.visualEyeTrackingDataPanel.setAlignmentY(1.5F);
            this.visualEyeTrackingDataPanel.setLayout(new FormLayout(
                "default:grow, 2*($lcgap, default)",
                "fill:150dlu"));
            ((FormLayout)this.visualEyeTrackingDataPanel.getLayout()).setColumnGroups(new int[][] {{3, 5}});

            //---- eyePositionDisplay ----
            this.eyePositionDisplay.setPreferredSize(new Dimension(300, 200));
            this.eyePositionDisplay.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.eyePositionDisplay.setMinimumSize(new Dimension(1, 200));
            this.eyePositionDisplay.setEyePostionCanvasBackgroundColor(Color.darkGray);
            this.visualEyeTrackingDataPanel.add(this.eyePositionDisplay, cc.xy(1, 1));

            //---- eyeDistanceDisplay ----
            this.eyeDistanceDisplay.setPreferredSize(new Dimension(50, 300));
            this.eyeDistanceDisplay.setMinimumSize(new Dimension(1, 300));
            this.eyeDistanceDisplay.setPaddingVal(4);
            this.eyeDistanceDisplay.setBackground(null);
            this.eyeDistanceDisplay.setForeground(Color.darkGray);
            this.eyeDistanceDisplay.setEyeDistanceCanvasColor(Color.darkGray);
            this.visualEyeTrackingDataPanel.add(this.eyeDistanceDisplay, new CellConstraints(3, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets( 5, 0, 5, 0)));

            //---- pupilSizeDisplay ----
            this.pupilSizeDisplay.setPreferredSize(new Dimension(60, 300));
            this.pupilSizeDisplay.setMinimumSize(new Dimension(1, 300));
            this.pupilSizeDisplay.setPupilSizeCanvasBackgroundColor(Color.darkGray);
            this.visualEyeTrackingDataPanel.add(this.pupilSizeDisplay, new CellConstraints(5, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets( 5, 0, 5, 0)));
        }
        add(this.visualEyeTrackingDataPanel, cc.xywh(1, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));

        //======== tabbedPane ========
        {

            //======== overallPanel ========
            {
                this.overallPanel.setLayout(new FormLayout(
                    "150dlu, $lcgap, default, 6dlu, default:grow",
                    "default, $lgap, fill:default, 22*($lgap, default)"));
                ((FormLayout)this.overallPanel.getLayout()).setRowGroups(new int[][] {{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 45, 47}});

                //---- eyeTrackingDeviceNameLabel ----
                this.eyeTrackingDeviceNameLabel.setText("Eye Tracking Device Name");
                this.overallPanel.add(this.eyeTrackingDeviceNameLabel, cc.xy(1, 1));

                //---- eyeTrackingDeviceNameValueLabel ----
                this.eyeTrackingDeviceNameValueLabel.setText("-");
                this.overallPanel.add(this.eyeTrackingDeviceNameValueLabel, cc.xy(5, 1));

                //---- eyeTrackingDeviceTypeLabel ----
                this.eyeTrackingDeviceTypeLabel.setText("Eye Tracking Device Type");
                this.overallPanel.add(this.eyeTrackingDeviceTypeLabel, cc.xy(1, 3));

                //---- eyeTrackingDeviceTypeValueLabel ----
                this.eyeTrackingDeviceTypeValueLabel.setText("-");
                this.overallPanel.add(this.eyeTrackingDeviceTypeValueLabel, cc.xy(5, 3));

                //---- eyeTrackingDeviceLocationLabel ----
                this.eyeTrackingDeviceLocationLabel.setText("Eye Tracking Device Location");
                this.overallPanel.add(this.eyeTrackingDeviceLocationLabel, cc.xy(1, 5));

                //---- eyeTrackingDeviceLocationValueLabel ----
                this.eyeTrackingDeviceLocationValueLabel.setText("-");
                this.overallPanel.add(this.eyeTrackingDeviceLocationValueLabel, cc.xy(5, 5));

                //---- recordingTitle ----
                this.recordingTitle.setText("Eye Tracker Recording Status");
                this.overallPanel.add(this.recordingTitle, cc.xy(1, 7));
                this.overallPanel.add(this.recordIndicator, cc.xy(3, 7));

                //---- recordSwitch ----
                this.recordSwitch.setText("Off");
                this.recordSwitch.setActionCommand("toggleRecording");
                this.overallPanel.add(this.recordSwitch, cc.xy(5, 7));

                //---- eyeTrackingEventRateLabel ----
                this.eyeTrackingEventRateLabel.setText("Eye Tracker Event Rate [events per second]");
                this.overallPanel.add(this.eyeTrackingEventRateLabel, cc.xy(1, 9));
                this.overallPanel.add(this.eventRateIndicator, cc.xy(3, 9));

                //---- eventRate ----
                this.eventRate.setText("0");
                this.overallPanel.add(this.eventRate, cc.xy(5, 9));

                //---- brainTrackingDeviceNameLabel ----
                this.brainTrackingDeviceNameLabel.setText("Brain Tracking Device Name");
                this.overallPanel.add(this.brainTrackingDeviceNameLabel, cc.xy(1, 13));

                //---- brainTrackingDeviceNameValueLabel ----
                this.brainTrackingDeviceNameValueLabel.setText("-");
                this.overallPanel.add(this.brainTrackingDeviceNameValueLabel, cc.xy(5, 13));

                //---- brainTrackingDeviceLocationLabel ----
                this.brainTrackingDeviceLocationLabel.setText("Brain Tracking Device Location");
                this.overallPanel.add(this.brainTrackingDeviceLocationLabel, cc.xy(1, 15));

                //---- brainTrackingDeviceLocationValueLabel ----
                this.brainTrackingDeviceLocationValueLabel.setText("-");
                this.overallPanel.add(this.brainTrackingDeviceLocationValueLabel, cc.xy(5, 15));

                //---- brainTrackerRecordingStatusLabel ----
                this.brainTrackerRecordingStatusLabel.setText("Brain Tracking Recording Status");
                this.overallPanel.add(this.brainTrackerRecordingStatusLabel, cc.xy(1, 17));
                this.overallPanel.add(this.brainRecordIndicator, cc.xy(3, 17));

                //---- brainTrackingRecordSwitch ----
                this.brainTrackingRecordSwitch.setText("Off");
                this.brainTrackingRecordSwitch.setActionCommand("toggleBrainRecording");
                this.overallPanel.add(this.brainTrackingRecordSwitch, cc.xy(5, 17));

                //---- brainTrackingEventRateLabel ----
                this.brainTrackingEventRateLabel.setText("Brain Tracker Event Rate [events per second]");
                this.overallPanel.add(this.brainTrackingEventRateLabel, cc.xy(1, 19));
                this.overallPanel.add(this.brainTrackingEventRateIndicator, cc.xy(3, 19));

                //---- brainTrackingEventRateValue ----
                this.brainTrackingEventRateValue.setText("0");
                this.overallPanel.add(this.brainTrackingEventRateValue, cc.xy(5, 19));

                //---- deviceTrackingSinceLabel ----
                this.deviceTrackingSinceLabel.setText("Device Tracking Since");
                this.overallPanel.add(this.deviceTrackingSinceLabel, cc.xy(1, 23));

                //---- trackingSince ----
                this.trackingSince.setText("Not tracking");
                this.overallPanel.add(this.trackingSince, cc.xy(5, 23));

                //---- brainHistoryLabel ----
                this.brainHistoryLabel.setText("Brain History");
                this.overallPanel.add(this.brainHistoryLabel, cc.xy(1, 27));

                //---- brainHistoryLink ----
                this.brainHistoryLink.setText("Show brain history ...");
                this.brainHistoryLink.setActionCommand("openBrainHistory");
                this.overallPanel.add(this.brainHistoryLink, cc.xy(5, 27));

                //---- gazePositionLabel ----
                this.gazePositionLabel.setText("Gaze Position [normalized]");
                this.overallPanel.add(this.gazePositionLabel, cc.xy(1, 31));

                //---- gazePositionHistoryLink ----
                this.gazePositionHistoryLink.setText("Gaze position history ...");
                this.gazePositionHistoryLink.setActionCommand("openGazePositionHistory");
                this.overallPanel.add(this.gazePositionHistoryLink, cc.xy(5, 31));

                //---- headPositionLabel ----
                this.headPositionLabel.setText("Head Position [normalized]");
                this.overallPanel.add(this.headPositionLabel, cc.xy(1, 33));
                this.overallPanel.add(this.headPositionIndicator, cc.xy(3, 33));

                //---- headPositionHistoryLink ----
                this.headPositionHistoryLink.setText("Head position history ...");
                this.headPositionHistoryLink.setActionCommand("openHeadPositionHistory");
                this.overallPanel.add(this.headPositionHistoryLink, cc.xy(5, 33));

                //---- headDistanceLabel ----
                this.headDistanceLabel.setText("Head Distance [mm]");
                this.overallPanel.add(this.headDistanceLabel, cc.xy(1, 35));
                this.overallPanel.add(this.headDistanceIndicator, cc.xy(3, 35));

                //---- headDistanceHistoryLink ----
                this.headDistanceHistoryLink.setText("Head distance history ...");
                this.headDistanceHistoryLink.setActionCommand("openHeadDistanceHistory");
                this.overallPanel.add(this.headDistanceHistoryLink, cc.xy(5, 35));

                //---- pupilSizeLabel ----
                this.pupilSizeLabel.setText("Pupil Size [mm]");
                this.overallPanel.add(this.pupilSizeLabel, cc.xy(1, 37));

                //---- pupilSizeHistoryLink ----
                this.pupilSizeHistoryLink.setText("Pupilsize history ...");
                this.pupilSizeHistoryLink.setActionCommand("openPupilSizeHistory");
                this.overallPanel.add(this.pupilSizeHistoryLink, cc.xy(5, 37));

                //---- hardwareRecalibrationLabel ----
                this.hardwareRecalibrationLabel.setText("Hardware Calibration");
                this.overallPanel.add(this.hardwareRecalibrationLabel, cc.xy(1, 41));

                //---- performHardwareRecalibrationLink ----
                this.performHardwareRecalibrationLink.setText("Perfom hardware recalibration ...");
                this.performHardwareRecalibrationLink.setActionCommand("performHardwareRecalibration");
                this.overallPanel.add(this.performHardwareRecalibrationLink, cc.xy(5, 41));

                //---- localRecalibrationVerificationLabel ----
                this.localRecalibrationVerificationLabel.setText("Local Recalibration & Verficiation");
                this.overallPanel.add(this.localRecalibrationVerificationLabel, cc.xy(1, 43));
                this.overallPanel.add(this.calibrationIndicator, cc.xy(3, 43));

                //---- performRecalibrationLink ----
                this.performRecalibrationLink.setText("Perfom local recalibration ...");
                this.performRecalibrationLink.setActionCommand("performRecalibration");
                this.overallPanel.add(this.performRecalibrationLink, cc.xy(5, 43));

                //---- qualityLabel ----
                this.qualityLabel.setText("Overall Tracking Quality");
                this.overallPanel.add(this.qualityLabel, cc.xy(1, 47));
                this.overallPanel.add(this.overallQualityIndicator, cc.xy(3, 47));

                //---- overallQualityLabel ----
                this.overallQualityLabel.setText("Unknown");
                this.overallPanel.add(this.overallQualityLabel, cc.xy(5, 47));
            }
            this.tabbedPane.addTab("Overview", this.overallPanel);


            //======== panel3 ========
            {
                this.panel3.setLayout(new FormLayout(
                    "default:grow",
                    "fill:default:grow"));

                //======== scrollPane4 ========
                {
                    this.scrollPane4.setViewportView(this.logText);
                }
                this.panel3.add(this.scrollPane4, cc.xy(1, 1));
            }
            this.tabbedPane.addTab("Message Log", this.panel3);


            //======== settingsPanel ========
            {
                this.settingsPanel.setLayout(new FormLayout(
                    "150dlu, $lcgap, 50dlu, 6dlu, default:grow",
                    "6*(default, $lgap), default"));
                ((FormLayout)this.settingsPanel.getLayout()).setRowGroups(new int[][] {{1, 3, 5, 7, 9, 11}});

                //---- bufferSizeEyeTrackerHistoryLabel ----
                this.bufferSizeEyeTrackerHistoryLabel.setText("Eye Tracker Buffer Time");
                this.settingsPanel.add(this.bufferSizeEyeTrackerHistoryLabel, cc.xy(1, 1));

                //---- bufferSizeLabel ----
                this.bufferSizeLabel.setText("30s");
                this.settingsPanel.add(this.bufferSizeLabel, cc.xywh(3, 1, 1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));

                //---- bufferSizeSlider ----
                this.bufferSizeSlider.setMinimum(5);
                this.bufferSizeSlider.setMaximum(120);
                this.bufferSizeSlider.setMajorTickSpacing(5);
                this.bufferSizeSlider.setSnapToTicks(true);
                this.bufferSizeSlider.setValue(30);
                this.settingsPanel.add(this.bufferSizeSlider, cc.xy(5, 1));

                //---- bufferSizeBrainTrackerHistoryLabel ----
                this.bufferSizeBrainTrackerHistoryLabel.setText("Brain Tracker Buffer Time");
                this.settingsPanel.add(this.bufferSizeBrainTrackerHistoryLabel, cc.xy(1, 3));

                //---- bufferSizeBrainTrackerHistoryValueLabel ----
                this.bufferSizeBrainTrackerHistoryValueLabel.setText("5s");
                this.settingsPanel.add(this.bufferSizeBrainTrackerHistoryValueLabel, cc.xywh(3, 3, 1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));

                //---- bufferSizeBrainTrackerHistorySlider ----
                this.bufferSizeBrainTrackerHistorySlider.setMaximum(30);
                this.bufferSizeBrainTrackerHistorySlider.setMinimum(5);
                this.bufferSizeBrainTrackerHistorySlider.setSnapToTicks(true);
                this.bufferSizeBrainTrackerHistorySlider.setMajorTickSpacing(1);
                this.bufferSizeBrainTrackerHistorySlider.setValue(5);
                this.settingsPanel.add(this.bufferSizeBrainTrackerHistorySlider, cc.xy(5, 3));

                //---- transparentLocalRecalibrationLabel2 ----
                this.transparentLocalRecalibrationLabel2.setText("Display Ministatus");
                this.settingsPanel.add(this.transparentLocalRecalibrationLabel2, cc.xy(1, 7));

                //---- ministatus ----
                this.ministatus.setText("Ministatus");
                this.settingsPanel.add(this.ministatus, cc.xy(5, 7));

                //---- transparentLocalRecalibrationLabel ----
                this.transparentLocalRecalibrationLabel.setText("Transparent Local Recalibration");
                this.settingsPanel.add(this.transparentLocalRecalibrationLabel, cc.xy(1, 11));

                //---- transparentRecalibration ----
                this.transparentRecalibration.setText("Transparent");
                this.settingsPanel.add(this.transparentRecalibration, cc.xy(5, 11));
            }
            this.tabbedPane.addTab("Settings", this.settingsPanel);


            //======== ChannelPanel ========
            {
                this.ChannelPanel.setLayout(new FormLayout(
                    "default:grow",
                    "default"));
            }
            this.tabbedPane.addTab("Channels", this.ChannelPanel);

        }
        add(this.tabbedPane, cc.xy(1, 5));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
