package MySqlGest;

import javax.swing.*;
import java.awt.*;

public class MyTable extends JTable {
    public MyTable(Object[][] data, String[] columnNames, String table) {
        super(data, columnNames);
        setPreferredScrollableViewportSize(getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(this);
        TableFrame frame = new TableFrame();
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(170 * data[0].length, Math.min(50 * (data.length + 2), 900));
        frame.setLocationRelativeTo(null);
        // If you select a row it will be writting in the textfield:
        if (table != null) {
            getSelectionModel().addListSelectionListener(event -> {
                if (table.equals("query")) {
                    PrincipalPanel.setTextQuery(getValueAt(getSelectedRow(), 0).toString());
                } else if (table.equals("update")) {
                    PrincipalPanel.setTextUpdate(getValueAt(getSelectedRow(), 0).toString());
                }
            });
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) { // Disable changes
        return false;
    }

}
