/*
 * Created by JFormDesigner on Wed Sep 02 16:51:17 CEST 2009
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
//    private class recInd extends JStatusIndicator {
//        /**
//         * 
//         */
//        private static final long serialVersionUID = -4793194366766899056L;
//
//        private recInd() {
//            initComponents();
//        }
//
//        private void initComponents() {
//            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
//            // Generated using JFormDesigner non-commercial license
//            // JFormDesigner - End of component initialization  //GEN-END:initComponents
//        }
//
//        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
//        // Generated using JFormDesigner non-commercial license
//        // JFormDesigner - End of variables declaration  //GEN-END:variables
//    }

    /**  */
    private static final long serialVersionUID = 6771868751640834350L;

    public JLabel bufferSizeLabel;

    public JSlider bufferSizeSlider;

    protected JStatusIndicator calibrationIndicator;

    protected JLabel deviceLocation;

    protected JLabel deviceName;

    private JLabel deviceNameLabel;

    protected JLabel deviceType;

    private JLabel deviceTypeLabel;

    protected JLabel eventRate;

    protected JStatusIndicator eventRateIndicator;

    protected EyeDistanceDisplay eyeDistanceDisplay;

    protected EyePositionDisplay eyePositionDisplay;

    protected JXHyperlink gazePositionHistoryLink;

    private JLabel gazePositionLabel;

    protected JXHyperlink headDistanceHistoryLink;
    protected JStatusIndicator headDistanceIndicator;
    private JLabel headDistanceLabel;
    protected JXHyperlink headPositionHistoryLink;
    protected JStatusIndicator headPositionIndicator;
    private JLabel headPositionLabel;
    private JLabel label1;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JLabel label2;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    protected JTextPane logText;
    protected JStatusIndicator overallQualityIndicator;
    private JLabel overallQualityLabel;
    protected JPanel panel1;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    protected JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    protected JXHyperlink performHardwareRecalibrationLink;
    protected JXHyperlink performRecalibrationLink;
    protected PupilSizeDisplay pupilSizeDisplay1;
    protected JXHyperlink pupilSizeHistoryLink;
    private JLabel pupilSizeLabel;
    public JStatusIndicator recordIndicator;
    public JLabel recordingTitle;
    public JXHyperlink recordSwitch;
    private JScrollPane scrollPane4;
    private JTabbedPane tabbedPane1;
    protected JLabel trackingSince;
    protected JCheckBox transparentRecalibration;

    // JFormDesigner - End of variables declaration  //GEN-END:variables
    /** */
    public ServerPanelTemplate() {
        initComponents();
    }

    public JSlider getBufferSizeSlider() {
        return this.bufferSizeSlider;
    }

    public JStatusIndicator getCalibrationIndicator() {
        return this.calibrationIndicator;
    }

    public JLabel getEventRate() {
        return this.eventRate;
    }

    public JStatusIndicator getEventRateIndicator() {
        return this.eventRateIndicator;
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

    public JPanel getPanel2() {
        return this.panel2;
    }

    public JXHyperlink getPerformHardwareRecalibrationLink() {
        return this.performHardwareRecalibrationLink;
    }

    public JXHyperlink getPerformRecalibrationLink() {
        return this.performRecalibrationLink;
    }

    public JXHyperlink getPupilSizeHistoryLink() {
        return this.pupilSizeHistoryLink;
    }

    public JStatusIndicator getRecordIndicator() {
        return this.recordIndicator;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        this.panel2 = new JPanel();
        this.eyePositionDisplay = new EyePositionDisplay();
        this.eyeDistanceDisplay = new EyeDistanceDisplay();
        this.pupilSizeDisplay1 = new PupilSizeDisplay();
        this.tabbedPane1 = new JTabbedPane();
        this.panel1 = new JPanel();
        this.deviceNameLabel = new JLabel();
        this.deviceName = new JLabel();
        this.deviceTypeLabel = new JLabel();
        this.deviceType = new JLabel();
        this.label1 = new JLabel();
        this.deviceLocation = new JLabel();
        this.label10 = new JLabel();
        this.trackingSince = new JLabel();
        this.label7 = new JLabel();
        this.eventRateIndicator = new JStatusIndicator();
        this.eventRate = new JLabel();
        this.recordingTitle = new JLabel();
        this.recordIndicator = new JStatusIndicator();
        this.recordSwitch = new JXHyperlink();
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
        this.label5 = new JLabel();
        this.calibrationIndicator = new JStatusIndicator();
        this.performRecalibrationLink = new JXHyperlink();
        this.label6 = new JLabel();
        this.performHardwareRecalibrationLink = new JXHyperlink();
        this.label12 = new JLabel();
        this.overallQualityIndicator = new JStatusIndicator();
        this.overallQualityLabel = new JLabel();
        this.panel3 = new JPanel();
        this.scrollPane4 = new JScrollPane();
        this.logText = new JTextPane();
        this.panel4 = new JPanel();
        this.label2 = new JLabel();
        this.bufferSizeLabel = new JLabel();
        this.bufferSizeSlider = new JSlider();
        this.label11 = new JLabel();
        this.transparentRecalibration = new JCheckBox();
        final CellConstraints cc = new CellConstraints();

        //======== this ========
        setPreferredSize(new Dimension(600, 720));
        setMinimumSize(new Dimension(600, 620));
        setLayout(new FormLayout("default:grow", "min, $lgap, 0dlu, $lgap, fill:[235dlu,default]:grow"));

        //======== panel2 ========
        {
            this.panel2.setBorder(new TitledBorder(null, "Visual Tracking Data", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
            this.panel2.setPreferredSize(new Dimension(506, 500));
            this.panel2.setOpaque(false);
            this.panel2.setRequestFocusEnabled(false);
            this.panel2.setVerifyInputWhenFocusTarget(false);
            this.panel2.setAlignmentY(1.5F);
            this.panel2.setLayout(new FormLayout("default:grow, 2*($lcgap, default)", "fill:150dlu"));
            ((FormLayout) this.panel2.getLayout()).setColumnGroups(new int[][] { { 3, 5 } });

            //---- eyePositionDisplay ----
            this.eyePositionDisplay.setPreferredSize(new Dimension(300, 200));
            this.eyePositionDisplay.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.eyePositionDisplay.setMinimumSize(new Dimension(1, 200));
            this.eyePositionDisplay.setEyePostionCanvasBackgroundColor(Color.darkGray);
            this.panel2.add(this.eyePositionDisplay, cc.xy(1, 1));

            //---- eyeDistanceDisplay ----
            this.eyeDistanceDisplay.setPreferredSize(new Dimension(50, 300));
            this.eyeDistanceDisplay.setMinimumSize(new Dimension(1, 300));
            this.eyeDistanceDisplay.setPaddingVal(4);
            this.eyeDistanceDisplay.setBackground(null);
            this.eyeDistanceDisplay.setForeground(Color.darkGray);
            this.eyeDistanceDisplay.setEyeDistanceCanvasColor(Color.darkGray);
            this.panel2.add(this.eyeDistanceDisplay, new CellConstraints(3, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets(5, 0, 5, 0)));

            //---- pupilSizeDisplay1 ----
            this.pupilSizeDisplay1.setPreferredSize(new Dimension(60, 300));
            this.pupilSizeDisplay1.setMinimumSize(new Dimension(1, 300));
            this.pupilSizeDisplay1.setPupilSizeCanvasBackgroundColor(Color.darkGray);
            this.panel2.add(this.pupilSizeDisplay1, new CellConstraints(5, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets(5, 0, 5, 0)));
        }
        add(this.panel2, cc.xywh(1, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {
                this.panel1.setLayout(new FormLayout("150dlu, $lcgap, default, 6dlu, default:grow", "default, $lgap, fill:default, 17*($lgap, default)"));
                ((FormLayout) this.panel1.getLayout()).setRowGroups(new int[][] { { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 33, 35 } });

                //---- deviceNameLabel ----
                this.deviceNameLabel.setText("Tracking Device Name");
                this.panel1.add(this.deviceNameLabel, cc.xy(1, 1));

                //---- deviceName ----
                this.deviceName.setText("-");
                this.panel1.add(this.deviceName, cc.xy(5, 1));

                //---- deviceTypeLabel ----
                this.deviceTypeLabel.setText("Tracking Device Type");
                this.panel1.add(this.deviceTypeLabel, cc.xy(1, 3));

                //---- deviceType ----
                this.deviceType.setText("-");
                this.panel1.add(this.deviceType, cc.xy(5, 3));

                //---- label1 ----
                this.label1.setText("Tracking Device Location");
                this.panel1.add(this.label1, cc.xy(1, 5));

                //---- deviceLocation ----
                this.deviceLocation.setText("-");
                this.panel1.add(this.deviceLocation, cc.xy(5, 5));

                //---- label10 ----
                this.label10.setText("Device Tracking Since");
                this.panel1.add(this.label10, cc.xy(1, 9));

                //---- trackingSince ----
                this.trackingSince.setText("0");
                this.panel1.add(this.trackingSince, cc.xy(5, 9));

                //---- label7 ----
                this.label7.setText("Event Rate [events / second]");
                this.panel1.add(this.label7, cc.xy(1, 11));
                this.panel1.add(this.eventRateIndicator, cc.xy(3, 11));

                //---- eventRate ----
                this.eventRate.setText("0");
                this.panel1.add(this.eventRate, cc.xy(5, 11));

                //---- recordingTitle ----
                this.recordingTitle.setText("Recording Status");
                this.panel1.add(this.recordingTitle, cc.xy(1, 15));
                this.panel1.add(this.recordIndicator, cc.xy(3, 15));

                //---- recordSwitch ----
                this.recordSwitch.setText("Off");
                this.recordSwitch.setActionCommand("toggleRecording");
                this.panel1.add(this.recordSwitch, cc.xy(5, 15));

                //---- gazePositionLabel ----
                this.gazePositionLabel.setText("Gaze Position [normalized]");
                this.panel1.add(this.gazePositionLabel, cc.xy(1, 19));

                //---- gazePositionHistoryLink ----
                this.gazePositionHistoryLink.setText("Gaze position history ...");
                this.gazePositionHistoryLink.setActionCommand("openGazePositionHistory");
                this.panel1.add(this.gazePositionHistoryLink, cc.xy(5, 19));

                //---- headPositionLabel ----
                this.headPositionLabel.setText("Head Position [normalized]");
                this.panel1.add(this.headPositionLabel, cc.xy(1, 21));
                this.panel1.add(this.headPositionIndicator, cc.xy(3, 21));

                //---- headPositionHistoryLink ----
                this.headPositionHistoryLink.setText("Head position history ...");
                this.headPositionHistoryLink.setActionCommand("openHeadPositionHistory");
                this.panel1.add(this.headPositionHistoryLink, cc.xy(5, 21));

                //---- headDistanceLabel ----
                this.headDistanceLabel.setText("Head Distance [mm]");
                this.panel1.add(this.headDistanceLabel, cc.xy(1, 23));
                this.panel1.add(this.headDistanceIndicator, cc.xy(3, 23));

                //---- headDistanceHistoryLink ----
                this.headDistanceHistoryLink.setText("Head distance history ...");
                this.headDistanceHistoryLink.setActionCommand("openHeadDistanceHistory");
                this.panel1.add(this.headDistanceHistoryLink, cc.xy(5, 23));

                //---- pupilSizeLabel ----
                this.pupilSizeLabel.setText("Pupil Size [mm]");
                this.panel1.add(this.pupilSizeLabel, cc.xy(1, 25));

                //---- pupilSizeHistoryLink ----
                this.pupilSizeHistoryLink.setText("Pupilsize history ...");
                this.pupilSizeHistoryLink.setActionCommand("openPupilSizeHistory");
                this.panel1.add(this.pupilSizeHistoryLink, cc.xy(5, 25));

                //---- label5 ----
                this.label5.setText("Local Recalibration & Verficiation");
                this.panel1.add(this.label5, cc.xy(1, 29));
                this.panel1.add(this.calibrationIndicator, cc.xy(3, 29));

                //---- performRecalibrationLink ----
                this.performRecalibrationLink.setText("Perfom local recalibration ...");
                this.performRecalibrationLink.setActionCommand("performRecalibration");
                this.panel1.add(this.performRecalibrationLink, cc.xy(5, 29));

                //---- label6 ----
                this.label6.setText("Hardware Recalibration");
                this.panel1.add(this.label6, cc.xy(1, 31));

                //---- performHardwareRecalibrationLink ----
                this.performHardwareRecalibrationLink.setText("Perfom hardware recalibration ...");
                this.performHardwareRecalibrationLink.setActionCommand("performHardwareRecalibration");
                this.panel1.add(this.performHardwareRecalibrationLink, cc.xy(5, 31));

                //---- label12 ----
                this.label12.setText("Overall Quality");
                this.panel1.add(this.label12, cc.xy(1, 35));
                this.panel1.add(this.overallQualityIndicator, cc.xy(3, 35));

                //---- overallQualityLabel ----
                this.overallQualityLabel.setText("Unknown");
                this.panel1.add(this.overallQualityLabel, cc.xy(5, 35));
            }
            this.tabbedPane1.addTab("Overview", this.panel1);

            //======== panel3 ========
            {
                this.panel3.setLayout(new FormLayout("default:grow", "fill:default:grow"));

                //======== scrollPane4 ========
                {
                    this.scrollPane4.setViewportView(this.logText);
                }
                this.panel3.add(this.scrollPane4, cc.xy(1, 1));
            }
            this.tabbedPane1.addTab("Message Log", this.panel3);

            //======== panel4 ========
            {
                this.panel4.setLayout(new FormLayout("150dlu, $lcgap, 50dlu, 6dlu, default:grow", "2*(default, $lgap), default"));

                //---- label2 ----
                this.label2.setText("Buffer Size History [Number of Events]");
                this.panel4.add(this.label2, cc.xy(1, 1));

                //---- bufferSizeLabel ----
                this.bufferSizeLabel.setText("300");
                this.panel4.add(this.bufferSizeLabel, cc.xy(3, 1));

                //---- bufferSizeSlider ----
                this.bufferSizeSlider.setMinimum(300);
                this.bufferSizeSlider.setMaximum(6000);
                this.bufferSizeSlider.setMajorTickSpacing(10);
                this.bufferSizeSlider.setSnapToTicks(true);
                this.panel4.add(this.bufferSizeSlider, cc.xy(5, 1));

                //---- label11 ----
                this.label11.setText("Transparent Local Recalibration");
                this.panel4.add(this.label11, cc.xy(1, 3));

                //---- transparentRecalibration ----
                this.transparentRecalibration.setText("Transparent");
                this.panel4.add(this.transparentRecalibration, cc.xy(5, 3));
            }
            this.tabbedPane1.addTab("Settings", this.panel4);

        }
        add(this.tabbedPane1, cc.xy(1, 5));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
