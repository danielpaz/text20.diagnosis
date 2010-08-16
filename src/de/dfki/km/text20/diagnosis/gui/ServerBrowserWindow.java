/*
 * MainWindow.java
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
package de.dfki.km.text20.diagnosis.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.dfki.km.text20.diagnosis.gui.panel.ServerBrowserPanel;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;

/**
 * Contains all other widgets related to one server.
 * 
 * @author Andreas Buhl
 * @author Ralf Biedert
 * @author Nathaniel Egwu.
 */
public class ServerBrowserWindow extends JFrame {

    /** */
    private static final long serialVersionUID = 7067545069191717971L;

    /** */
    ApplicationData applicationData;

    /** */
    ServerBrowserPanel serverBrowserPanel;

    /**
     * @param applicationData
     * @param serverInfo 
     */
    public ServerBrowserWindow(final ApplicationData applicationData,
                               final ServerInfo serverInfo) {
        super();
        this.applicationData = applicationData;

        //        this.serverInfo = serverInfo;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JButton btn = ServerBrowserWindow.this.serverBrowserPanel.getUpdateButton();
                int x = ServerBrowserWindow.this.getWidth() - btn.getWidth() - 10 - ServerBrowserWindow.this.getInsets().left;
                int y = ServerBrowserWindow.this.getHeight() - btn.getHeight() - 10 - ServerBrowserWindow.this.getInsets().top;
                btn.setBounds(x, y, btn.getWidth(), btn.getHeight());
                ServerBrowserWindow.this.serverBrowserPanel.updateDeviceLabels();
            }
        });

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent e) {
                if (ServerBrowserWindow.this.applicationData.getWindowList().size() == 0) {
                    shutdown();
                } else {
                    removeAllWindows(ServerBrowserWindow.this.applicationData.getWindowList());
                }
            }

            @Override
            public void windowDeiconified(final WindowEvent e) {
                ServerBrowserWindow.this.serverBrowserPanel.timer.start();
            }

            @Override
            public void windowIconified(final WindowEvent e) {
                ServerBrowserWindow.this.serverBrowserPanel.timer.stop();
            }

            private void removeAllWindows(Map<String, JFrame> windowList) {
                Set<String> keys = windowList.keySet();
                //                System.out.println(keys);
                for (String key : keys) {
                    windowList.get(key).dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                shutdown();
            }

        });
        initGUI();
    }

    /**  */
    private void initGUI() {

        setTitle("Server Browser Application");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main panel of this frame
        this.serverBrowserPanel = new ServerBrowserPanel(this.applicationData);

        // Pack 
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(this.serverBrowserPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    /** */
    void shutdown() {
        this.applicationData.getPluginManager().shutdown();
        this.dispose();
        System.exit(0);
    }

    /**
     * @return the applicationData
     */
    public ApplicationData getApplicationData() {
        return this.applicationData;
    }

}
