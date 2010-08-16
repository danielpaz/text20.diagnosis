/*
 * JStatusIndicator.java
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
package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import de.dfki.km.text20.diagnosis.gui.ServerWindow;
import de.dfki.km.text20.diagnosis.util.EyeTrackingEventEvaluator.DiagState;

/**
 * @author rb
 *
 */
public class JStatusIndicator extends JComponent {

    /** */
    private static final long serialVersionUID = -1629022534528175329L;

    /** */
    private DiagState state = DiagState.BAD;

    /** */
    Map<DiagState, Image> images = new HashMap<DiagState, Image>();

    /**
     * 
     */
    public JStatusIndicator() {
        this(DiagState.BAD);
    }

    /**
     * @param initialState
     */
    public JStatusIndicator(final DiagState initialState) {
        this.state = initialState;
        rebuildMap();
    }

    private void rebuildMap() {
        final ImageIcon green = new ImageIcon(ServerWindow.class.getResource("resources/green.png"));
        final ImageIcon yellow = new ImageIcon(ServerWindow.class.getResource("resources/orange.png"));
        final ImageIcon red = new ImageIcon(ServerWindow.class.getResource("resources/red.png"));
        final ImageIcon off = new ImageIcon(ServerWindow.class.getResource("resources/red.png"));
        final ImageIcon on = new ImageIcon(ServerWindow.class.getResource("resources/green.png"));

        this.images.put(DiagState.OK, green.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        this.images.put(DiagState.VAGUE, yellow.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        this.images.put(DiagState.BAD, red.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        this.images.put(DiagState.OFF, off.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        this.images.put(DiagState.ON, on.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
    }

    /**
     * @param state
     */
    public void setStatus(final DiagState state) {
        if (this.state == state) return;

        this.state = state;
        this.repaint();
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(final Graphics g) {
        final Image image = this.images.get(this.state);
        int iw = Math.max(getPreferredSize().width, image.getWidth(this));
        int ih = Math.max(getPreferredSize().height, image.getHeight(this));
        if (iw < 0 || ih < 0) System.out.println(iw + ", " + ih + "; " + getName());
        g.drawImage(image, (getWidth() - iw) / 2, (getHeight() - ih) / 2, this);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(16, 16);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(16, 16);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(100, 100);
    }

    /**
     * @return .
     */
    public Enum<DiagState> getStatus() {
        return this.state;
    }

    /**
     * @param b
     * @return .
     */
    public static DiagState getDiagState(final boolean b) {
        if (b) { return DiagState.OK; }
        return DiagState.BAD;
    }
}
