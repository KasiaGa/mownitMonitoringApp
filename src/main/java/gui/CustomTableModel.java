package gui;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel extends AbstractTableModel {

    private Object[][] data;
    private String[] columnNames = {"id", "timestamp", "source id", "source name", "type", "comment"};

    public CustomTableModel(Object[][] data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
