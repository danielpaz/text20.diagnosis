package de.dfki.km.text20.diagnosis.util;

import javax.swing.table.AbstractTableModel;

/**
 * @author rb
 *
 */
public class EyetrackerDataModel extends AbstractTableModel {

    /** */
    private static final long serialVersionUID = 250104829667151459L;

    /** a ringbuffer containing the data to visualize */
    private EyeTrackingEventRingbuffer ringbuffer;

    /** the column names and width */
    @SuppressWarnings("boxing")
    final private Object colData[][] = { { "Nr.", 35 }, { "xPos left", 55 }, { "xPos right", 55 }, { "yPos left", 55 }, { "yPos right", 55 }, { "Dist left", 55 }, { "Dist right", 55 }, { "Size left", 35 }, { "Size right", 35 } };

    /**
     * @param ringbuffer
     */
    public EyetrackerDataModel(final EyeTrackingEventRingbuffer ringbuffer) {
        this.ringbuffer = ringbuffer;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return this.colData.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return this.ringbuffer.size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {

        if (this.ringbuffer.get(rowIndex) == null) {
            return "N/A";
        }
        switch (columnIndex) {
        case 0:
            return new Integer(rowIndex + 1);
        case 1:
            return String.format("%.2f", new Float(this.ringbuffer.get(rowIndex).getLeftEyePosition()[0]));
        case 2:
            return String.format("%.2f", new Float(this.ringbuffer.get(rowIndex).getRightEyePosition()[0]));
        case 3:
            return String.format("%.2f", new Float(this.ringbuffer.get(rowIndex).getLeftEyePosition()[1]));
        case 4:
            return String.format("%.2f", new Float(this.ringbuffer.get(rowIndex).getRightEyePosition()[1]));
        case 5:
            return String.format("%.2f", new Float(this.ringbuffer.get(rowIndex).getLeftEyePosition()[2]));
        case 6:
            return String.format("%.2f", new Float(this.ringbuffer.get(rowIndex).getRightEyePosition()[2]));
        case 7:
            return String.format("%.1f", new Float(this.ringbuffer.get(rowIndex).getPupilSizeLeft()));
        case 8:
            return String.format("%.1f", new Float(this.ringbuffer.get(rowIndex).getPupilSizeRight()));
        default:
            return "unknown";
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(final int column) {
        return (String) this.colData[column][0];
    }

    /**
     * @param column
     * @return .
     */
    public int getColumnWidth(final int column) {
        return ((Integer) this.colData[column][1]).intValue();
    }

}
