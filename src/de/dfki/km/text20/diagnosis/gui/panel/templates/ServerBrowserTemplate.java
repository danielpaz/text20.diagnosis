/*
 * Created by JFormDesigner on Wed Sep 02 18:38:27 CEST 2009
 */

package de.dfki.km.text20.diagnosis.gui.panel.templates;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Ralf Biedert
 */
public class ServerBrowserTemplate extends JPanel {
    
    /** */
    private static final long serialVersionUID = -7298452519465777148L;
    public ServerBrowserTemplate() {
        initComponents();
    }

    public JDesktopPane getDesktopPane() {
        return this.desktopPane;
    }

    public JButton getUpdateButton() {
        return this.updateButton;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        this.desktopPane = new JDesktopPane();
        this.updateButton = new JButton();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setPreferredSize(new Dimension(400, 350));
        setMinimumSize(new Dimension(0, 0));
        setVerifyInputWhenFocusTarget(false);
        setLayout(new FormLayout(
            "default:grow",
            "fill:default:grow"));

        //======== desktopPane ========
        {
            this.desktopPane.setPreferredSize(new Dimension(600, 500));
            this.desktopPane.setForeground(Color.cyan);

            //---- updateButton ----
            this.updateButton.setText("Update");
            this.updateButton.setVisible(false);
            this.desktopPane.add(this.updateButton, JLayeredPane.DEFAULT_LAYER);
            this.updateButton.setBounds(280, 300, 93, 25);
        }
        add(this.desktopPane, cc.xy(1, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JDesktopPane desktopPane;
    private JButton updateButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
