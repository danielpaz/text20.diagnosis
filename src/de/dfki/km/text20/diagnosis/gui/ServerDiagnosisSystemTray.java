package de.dfki.km.text20.diagnosis.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.ImageIcon;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DiagState;

/**
 * @author ABu
 *
 */
public class ServerDiagnosisSystemTray {

    /** */
    private final Image greenLight = getStatusImage("resources/ampel_gruen.gif");

    /** */
    @SuppressWarnings("unused")
    private final Image yellowLight = getStatusImage("resources/ampel_gelb.gif");

    /** */
    @SuppressWarnings("unused")
    private final Image redLight = getStatusImage("resources/ampel_rot.gif");

    /** */
    final TrayIcon trayIcon = new TrayIcon(this.greenLight, "Text 2.0 Diagnosis", this.popup);

    /** */
    private final PopupMenu popup = new PopupMenu();

    /** */
    private DiagState trayState = DiagState.VAGUE;

    /** */
    private SystemTray tray = null;

    /**
     * @param applicationData
     */
    public ServerDiagnosisSystemTray(final ApplicationData applicationData) {
        // If no tray is supported, do nothing
        if (!SystemTray.isSupported()) { return; }

        // Get the tray.
        try {
            this.tray = SystemTray.getSystemTray();
        } catch (final Exception e) {
            System.out.println("Error getting Tray-Icons ... application exits!");
            return;
        }

        // Configure the tray icon
        this.trayIcon.setImageAutoSize(true);
        this.trayIcon.setPopupMenu(this.popup);

        // And add our icon 
        try {
            this.tray.add(this.trayIcon);
        } catch (final AWTException e) {
            System.err.println("TrayIcon could not be added.");
        }
    }

    /**
     * @param state
     * sets the system trays icon;
     * state couls be one of the constants defined in the EyeTrackingEventEvaluator class:
     * DIAG_STATE_BAD,  DIAG_STATE_VAGUE, DIAG_STATE_OK;
     * the icons are located in the package de.dfki.km.augmentedtext.gui,resources;
     */
    public void setTrayIcon(final DiagState state) {
        this.trayState = state;
    }

    /**
     * Loads a tray icon
     * 
     * @param resourcePath
     * @return
     */
    private Image getStatusImage(final String resourcePath) {
        final URL picURL = ServerWindow.class.getResource(resourcePath);
        final ImageIcon icon = new ImageIcon(picURL);
        return icon.getImage();
    }

    /**
     * @return .
     */
    public DiagState getCurrentTrayState() {
        return this.trayState;
    }

    /**
     * 
     */
    public void removeTrayIcon() {
        this.tray.remove(this.trayIcon);
    }
}
