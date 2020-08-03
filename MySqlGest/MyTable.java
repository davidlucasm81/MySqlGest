package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyTable extends JTable {
    public MyTable(Object[][] data, String[] columnNames, String table) {
        super(data, columnNames);
        setPreferredScrollableViewportSize(getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(this);
        TableFrame frame = new TableFrame();
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(200 * data[0].length, Math.min(30 * (data.length + 2), 900));
        frame.setLocationRelativeTo(null);
        if (table != null) {
            if (table.equals("atributes"))
                frame.setLocation(frame.getX() + 250, frame.getY());
            if (table.equals("tables")) {
                frame.setLocation(frame.getX() - 250, frame.getY());
            }
            if (table.equals("query")) {
                frame.setLocation(frame.getX(), frame.getY() + 270);
            }
            if (table.equals("update")) {
                frame.setLocation(frame.getX(), frame.getY() - 270);
            }
            this.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    if (me.getClickCount() == 2) {
                        String click = getValueAt(getSelectedRow(), 0).toString();
                        if (table.equals("query")) {
                            PrincipalPanel.setTextQuery(click);
                        } else if (table.equals("update")) {
                            PrincipalPanel.setTextUpdate(click);
                        } else if (table.equals("tables")) {
                            Object[][] atributesData = GUI.gest.getAtributes(click);
                            String[] columnName = {click + " atributes"};
                            new MyTable(atributesData, columnName, "atributes");
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) { // Disable changes
        return false;
    }

}
