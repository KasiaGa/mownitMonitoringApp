package gui;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    public Gui() {
        super();
        setLayout(new GridLayout());
        JPanel panel = new JPanel();
        String[] columnNames = {"id", "source name", "source id", "type"};
        Object[][] data = {{0, "Kernel-General", 4, "information"},{1, "Sth", 10, "warning"}};
        JTable table = new JTable(data, columnNames);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.fill = GridBagConstraints.HORIZONTAL;

        gBC.weightx = 0.5;
        gBC.gridx = 0;
        gBC.gridy = 0;
        panel.add(table, gBC);


        JComboBox jcmbSample = new JComboBox(new String[]{"ComboBox 1","hi","hello"});
        gBC.ipady = 10;
        gBC.weighty = 1.0;
        gBC.anchor = GridBagConstraints.PAGE_END;
        gBC.insets = new Insets(10,10,10,10);
        gBC.gridx = 1;
        gBC.gridwidth = 2;
        gBC.gridy = 2;
        panel.add(jcmbSample, gBC);

        /*setLayout(new GridLayout());
        JPanel panel = new JPanel();
        String[] columnNames = {"id", "source name", "source id", "type"};
        Object[][] data = {{0, "Kernel-General", 4, "information"},{1, "Sth", 10, "warning"}};
        JTable table = new JTable(data, columnNames);
        JComboBox comboBox = new JComboBox();
        panel.setLayout(new GridLayout(1,2));
        panel.add(table);
        panel.add(comboBox);
        setContentPane(panel);*/
        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
