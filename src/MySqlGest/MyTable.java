/*
     MyTable is a class which you can create tables for queries or another kind tables
 */
package MySqlGest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyTable extends JTable {
    // Table frame:
    private final TableFrame frame;

    /**
     * The constructor creates a table for the user
     *
     * @param data        A matrix of data
     * @param columnNames Contains the names of columns
     * @param table       Is the "type" of column
     */
    public MyTable(Object[][] data, String[] columnNames, String table) {
        super(data, columnNames);
        setPreferredScrollableViewportSize(getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(this);
        frame = new TableFrame();
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(200 * data[0].length, Math.min(30 * (data.length + 2), 900));
        frame.setLocationRelativeTo(null);
        if (table != null) {
            if (table.equals("atributes"))
                frame.setLocation(frame.getX() + 250, frame.getY());
            if (table.equals("tables"))
                frame.setLocation(frame.getX() - 250, frame.getY());

            if (table.equals("query"))
                frame.setLocation(frame.getX(), frame.getY() + 270);

            if (table.equals("update"))
                frame.setLocation(frame.getX(), frame.getY() - 270);
            /*
               The table dectects if you dou a double-click in a row.
               If the type of the table is a query or update it just will write in the respective Text Area of the statement.
               But if it's a table of Database tables it will create a new table with the atributes of the table clicked
             */
            this.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    if (me.getClickCount() == 2) {
                        String click = getValueAt(getSelectedRow(), 0).toString();
                        switch (table) {
                            case "query":
                                PrincipalPanel.setTextQuery(click);
                                break;
                            case "update":
                                PrincipalPanel.setTextUpdate(click);
                                break;
                            case "tables":
                                Object[][] atributesData = GUI.gest.getAtributes(click);
                                String[] columnName = {click + " atributes"};
                                PrincipalPanel.tableList.addLast(new MyTable(atributesData, columnName, "atributes"));
                                break;
                        }
                    }
                }
            });
        }
    }

    /**
     * If you call this method, the table will be destroyed
     */
    public void remove() {
        frame.removeAll();
        frame.setVisible(false);
    }

    @Override
    public boolean isCellEditable(int row, int column) { // Disable changes
        return false;
    }

}
