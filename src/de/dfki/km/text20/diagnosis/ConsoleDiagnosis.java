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

import java.awt.Point;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.options.getplugin.OptionCapabilities;
import net.xeoh.plugins.remotediscovery.DiscoveredPlugin;
import net.xeoh.plugins.remotediscovery.RemoteDiscovery;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDevice;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingDeviceProvider;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEventValidity;
import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingListener;
import de.dfki.km.text20.trackingserver.eyes.remote.TrackingServerRegistry;

/**
 * @author rb
 *
 */
public class ConsoleDiagnosis {

    /**
     * @param args
     * @throws URISyntaxException
     * @throws InterruptedException 
     */
    public static void main(final String[] args) throws URISyntaxException,
                                                InterruptedException {

        // Create plugin manager
        final PluginManager pluginManager = PluginManagerFactory.createPluginManager();
        pluginManager.addPluginsFrom(new URI("classpath://*"));

        // Obtain the proper tracking device here
        final EyeTrackingDeviceProvider deviceProvider = pluginManager.getPlugin(EyeTrackingDeviceProvider.class, new OptionCapabilities("eyetrackingdevice:trackingserver"));
        final EyeTrackingDevice openDevice = deviceProvider.openDevice("discover://youngest");
        
        RemoteDiscovery discovery = pluginManager.getPlugin(RemoteDiscovery.class);
        Collection<DiscoveredPlugin> plugins = discovery.discover(TrackingServerRegistry.class);
        for (DiscoveredPlugin plugin : plugins) {
            System.out.println(plugin.getPublishURI());
            
            
        }
        
        
        

        // Listen to the flow of gaze ... 
        openDevice.addTrackingListener(new EyeTrackingListener() {
            int i = 0;

            @Override
            @SuppressWarnings("unused")
            public void newTrackingEvent(final EyeTrackingEvent event) {
                final Point gazeCenter = event.getGazeCenter();
                final float[] hp = event.getHeadPosition();

                //System.out.println(event.getEventTime() + " " + hp[0] + " " + hp[1] + " " + hp[2] + " " + gazeCenter.x + " " + event.getPupilSizeLeft());
                if (this.i++ % 60 == 0) {
                    System.out.println(System.currentTimeMillis());
                }

                event.areValid(EyeTrackingEventValidity.CENTER_POSITION_VALID);
            }
        });

    }
}
