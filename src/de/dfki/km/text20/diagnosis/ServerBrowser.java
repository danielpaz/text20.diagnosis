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

import static net.jcores.CoreKeeper.$;

import javax.swing.UIManager;

import net.jcores.interfaces.functions.F0;
import de.dfki.km.text20.diagnosis.gui.ServerBrowserWindow;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;

/**
 * @author rb, ABu
 *
 */
public class ServerBrowser {
    /**
     * Server Browser app
     * Create a default server browser app.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        // Set Native LnF
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception e) {
            //
        }

        // Perform the app-start in the edt 
        $.edtnow(new F0(){
            @SuppressWarnings("unused")
            @Override
            public void f() {
                final ApplicationData applicationData = new ApplicationData();
                final ServerInfo serverInfo = new ServerInfo("init");
                new ServerBrowserWindow(applicationData, serverInfo);
            }
        });
    }
}
