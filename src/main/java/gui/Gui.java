package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class Gui extends JFrame implements ActionListener {

    private JPanel sourcePanel = new JPanel();
    private JPanel typePanel = new JPanel();

    public Gui(Object[][] data,HashSet<String> sources, HashSet<String> types) {
        super();
        setLayout(new GridLayout());
        JPanel panel = new JPanel();
        String[] columnNames = {"id", "source name", "source id", "type"};
        JTable table = new JTable(data, columnNames);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.anchor = GridBagConstraints.PAGE_START;
        gBC.weightx = 0.5;
        gBC.gridx = 0;
        gBC.gridy = 0;

        panel.add(table, gBC);

        JComboBox jcmbSample = new JComboBox(new String[]{"Search filter","Source","Type","Date"});
        gBC.ipady = 2;
        gBC.weighty = 1.0;
        gBC.anchor = GridBagConstraints.PAGE_START;
        gBC.insets = new Insets(10,10,10,10);
        gBC.gridx = 1;
        gBC.gridwidth = 2;
        gBC.gridy = 0;
        jcmbSample.addActionListener(this);
        panel.add(jcmbSample, gBC);


        sourcePanel.setLayout(new GridBagLayout());
        ArrayList<JCheckBox> sourceBox = new ArrayList<>();
        sources.forEach(s->sourceBox.add(new JCheckBox(s,false)));
        sourceBox.forEach(s->sourcePanel.add(s));
        sourcePanel.setVisible(false);
        panel.add(sourcePanel);

        typePanel.setLayout(new GridBagLayout());
        ArrayList<JCheckBox> typeBox = new ArrayList<>();
        types.forEach(s->typeBox.add(new JCheckBox(s,false)));
        typeBox.forEach(s->typePanel.add(s));
        typePanel.setVisible(false);
        panel.add(typePanel);

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComboBox cb = (JComboBox) actionEvent.getSource();
        String selectedItemName = (String)cb.getSelectedItem();
        switch (selectedItemName){
            case "Type":
                sourcePanel.setVisible(false);
                typePanel.setVisible(true);
                break;
            case "Date":
                sourcePanel.setVisible(false);
                typePanel.setVisible(false);
                break;
            case "Source":
                sourcePanel.setVisible(true);
                typePanel.setVisible(false);
                break;
            default:
                sourcePanel.setVisible(false);
                typePanel.setVisible(false);
                break;
        }

    }
}