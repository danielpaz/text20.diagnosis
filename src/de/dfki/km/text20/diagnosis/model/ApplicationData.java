/*
 * ApplicationData.java
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
package de.dfki.km.text20.diagnosis.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JFrame;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.JSPFProperties;
import de.dfki.km.text20.diagnosis.gui.ServerDiagnosisSystemTray;

/**
 * Keeps all application related data of general interest. 
 * 
 * @author rb
 */
public class ApplicationData {
    /** The global plugin manager */
    private final PluginManager pluginManager;

    /** There is only one tray */
    private ServerDiagnosisSystemTray tray;

    /** List of all server infos we manage */
    private final Collection<ServerInfo> serverInfos = new ArrayList<ServerInfo>();

    private Map<String, JFrame> windowList;

    /** */
    public ApplicationData() {

        JSPFProperties props = new JSPFProperties();
        props.setProperty(PluginManager.class, "logging.level", "WARNING");
        props.setProperty(PluginManager.class, "cache.enabled", "true");
        props.setProperty(PluginManager.class, "cache.mode",    "weak");
        
        this.pluginManager = PluginManagerFactory.createPluginManager(props);
        try {
            this.getPluginManager().addPluginsFrom(new URI("classpath://*"));
        } catch (final URISyntaxException e) {
            e.printStackTrace();
        }
        
        this.windowList = new Hashtable<String, JFrame>();
    }

    /**
     * @return the pluginManager
     */
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    /**
     * @return the tray
     */
    public ServerDiagnosisSystemTray getTray() {
        return this.tray;
    }

    /**
     * @param tray the tray to set
     */
    public void setTray(ServerDiagnosisSystemTray tray) {
        this.tray = tray;
    }

    /**
     * @return the serverInfo Collection
     */
    public Collection<ServerInfo> getServerInfos() {
        return this.serverInfos;
    }

    /**
     * @param windowList
     */
    public void setWindowsList(Map<String, JFrame> windowList) {
        this.windowList = windowList; 
    }

    /**
     * @return the windowList
     */
    public Map<String, JFrame> getWindowList() {
        return this.windowList;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(100);
        sb.append("plugInManager=" + this.pluginManager + "\n");
        sb.append("open windows=" + this.windowList.toString() + "\n");
        sb.append("server info=" + this.serverInfos.toString());
        return sb.toString();
    }
    
}
