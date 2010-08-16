package de.dfki.km.text20.diagnosis.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Enumeration;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.dfki.km.text20.diagnosis.model.ApplicationData;
import de.dfki.km.text20.diagnosis.model.ServerInfo;
import de.dfki.km.text20.diagnosis.util.EyetrackerDataModel;

/**
 * TODO: What's the purpose of this component?
 * 
 * @author rb
 *
 */
public class EyeTrackingEventDataGrid extends AbstractTrackingEventComponent {

    /**  */
    private static final long serialVersionUID = 7934421049116282411L;

    /**  */
    private static final int DEFAULT_EVENTDATAPANEL_WIDTH = 600;

    /**  */
    private static final int DEFAULT_EVENTDATAPANEL_HEIGHT = -1;

    /**  */
    private static final Color DEFAULT_EVENTDATAPANEL_BACKGROUNDCOLOR = null;

    /**  */
    private final JTable dataTable = new JTable();

    /**  */
    private JScrollPane scrollPane1;

    /**  */
    private JTable table1;

    /**  */
    public EyeTrackingEventDataGrid() {
        this(null, null);
    }

    /**
     * @param applicationData
     * @param serverInfo
     */
    public EyeTrackingEventDataGrid(final ApplicationData applicationData, final ServerInfo serverInfo) {
        super(applicationData, serverInfo);
        this.setPreferredSize(new Dimension(DEFAULT_EVENTDATAPANEL_WIDTH, DEFAULT_EVENTDATAPANEL_HEIGHT));
        setBackground(DEFAULT_EVENTDATAPANEL_BACKGROUNDCOLOR);
        initComponents();
    }

    /**
     * @return .
     */
    public JTable getDataTable() {
        return this.dataTable;
    }

    /**
     * 
     */
    @Deprecated
    private void initComponents() {
        this.scrollPane1 = new JScrollPane();
        this.table1 = new JTable();
        final CellConstraints cc = new CellConstraints();

        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0), "Tracking Event Daten; stop and start using menu \"actions\"", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.BLUE), getBorder()));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(final java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) {
                    throw new RuntimeException();
                }
            }
        });

        setLayout(new FormLayout("default, $lcgap, default", "2*(default, $lgap), " + "default"));

        //======== scrollPane1 ========
        {
            this.scrollPane1.setViewportView(this.table1);
        }
        this.table1.setModel(new EyetrackerDataModel(this.serverInfo.getEyeTrackingRingBuffer()));
        add(this.scrollPane1, cc.xy(1, 3));

        int i = 0;
        TableColumn c;
        for (final Enumeration<TableColumn> num = this.table1.getColumnModel().getColumns(); num.hasMoreElements();) {
            c = num.nextElement();
            c.setPreferredWidth(((EyetrackerDataModel) this.table1.getModel()).getColumnWidth(i));
            c.setMinWidth(((EyetrackerDataModel) this.table1.getModel()).getColumnWidth(i));
            c.setMaxWidth(((EyetrackerDataModel) this.table1.getModel()).getColumnWidth(i));
            i++;
        }
    }

    /* (non-Javadoc)
     * @see de.dfki.km.augmentedtext.diagnosis.gui.components.EyeTrackingEventComponent#render(java.awt.Graphics)
     */
    @Override
    public void render(final Graphics g) {
        // TODO Auto-generated method stub
    }
}
