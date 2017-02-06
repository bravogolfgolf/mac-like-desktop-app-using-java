package ui;

import domain.PersonMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class PersonTablePanel extends JPanel {

    private final PersonTableModel personTableModel = new PersonTableModel();
    private final JTable tablePanel = new JTable(personTableModel);
    private final JPopupMenu popupMenu = new JPopupMenu();
    private final JMenuItem deleteRowMenuItem = new JMenuItem("Delete row");
    private final PersonTableListener personTableListener;

    PersonTablePanel(PersonTableListener personTableListener) {
        this.personTableListener = personTableListener;
        addListeners();
        addComponents();
    }

    private void addListeners() {
        addMouseListener();
        addActionListener();
    }

    private void addMouseListener() {
        tablePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowSelected = tablePanel.rowAtPoint(e.getPoint());
                tablePanel.setRowSelectionInterval(rowSelected, rowSelected);
                if (e.getButton() == MouseEvent.BUTTON3)
                    popupMenu.show(tablePanel, e.getX(), e.getY());
            }
        });
    }

    private void addActionListener() {
        deleteRowMenuItem.addActionListener(e -> {
            int rowSelected = tablePanel.getSelectedRow();
            int id = personTableModel.getIdOfPersonOn(rowSelected);
            personTableListener.personDeleted(id);
        });
    }

    private void addComponents() {
        setLayout(new BorderLayout());
        popupMenu.add(deleteRowMenuItem);
        add(popupMenu);
        add(new JScrollPane(tablePanel), BorderLayout.CENTER);
    }

    void addDataForPersonTableModel(PersonMessage[] people) {
        personTableModel.addDataForPersonTableModel(people);
    }

    void refresh() {
        personTableModel.fireTableDataChanged();
    }
}
