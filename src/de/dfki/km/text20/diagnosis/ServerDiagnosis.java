/*
 * ServerDiagnosis.java
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
package de.dfki.km.text20.diagnosis;

import javax.swing.JFrame;
import javax.swing.UIManager;

import de.dfki.km.text20.diagnosis.gui.ServerWindow;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.CommonFunctions;

/**
 * @author rb, ABu
 *
 */
public class ServerDiagnosis {

    /**
     * Server diagnosis app
     * 
     * @param args
     */
    public static void main(final String[] args) {
        // Set Native LnF
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception e) {
            e.printStackTrace();
        }

        // Dirty hack to do everything in the swing edt
        CommonFunctions.invokeAndWaitAndShutup(new Runnable() {

            @Override
            public void run() {
                final ApplicationData applicationData = new ApplicationData();
                final ServerInfo serverInfo = new ServerInfo("discover://nearest");

                // Bring up tray
                //applicationData.setTray(new ServerDiagnosisSystemTray(applicationData));

                // Register main window
                serverInfo.setMainWindow(new ServerWindow(applicationData, serverInfo));

                // Add the server info 
                applicationData.getServerInfos().add(serverInfo);

                // Bring up GUI
                serverInfo.getMainWindow().setVisible(true);
                serverInfo.getMainWindow().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }

}
