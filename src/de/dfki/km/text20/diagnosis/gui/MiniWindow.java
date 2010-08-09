/*
 * Miniwindow.java
 * 
 * Copyright (c) 2010, Ralf Biedert All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of the author nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package de.dfki.km.text20.diagnosis.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.dfki.km.text20.diagnosis.gui.components.TrackingStatusMiniPanel;
import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * @author rb
 *
 */
public class MiniWindow extends JFrame {

    /** */
    private static final long serialVersionUID = 1856686618798807723L;

    /** */
    private TrackingStatusMiniPanel mini;

    /**
     * @param applicationData
     * @param serverInfo
     */
    public MiniWindow(final ApplicationData applicationData, final ServerInfo serverInfo) {
        this.mini = new TrackingStatusMiniPanel(applicationData, serverInfo);

        setResizable(true); // TODO: Change to false
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(this.mini, BorderLayout.CENTER);

        
        // FIXME: Why the fuck doesn't this respond to any resize operation?
        setSize(50, 40);
        //setPreferredSize(new Dimension(50, 40));
        //setMinimumSize(new Dimension(50, 40));

    }

    /**
     * @param evt
     */
    public void newEyeTrackingEvent(final EyeTrackingEvent evt) {
        if (isVisible()) {
            this.mini.newTrackingEvent(evt);
        }
    }

}
