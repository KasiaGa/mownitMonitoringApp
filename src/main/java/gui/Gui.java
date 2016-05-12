package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;

public class Gui extends JFrame implements ActionListener {

    private JPanel settingsPanel = new JPanel();
    private JPanel sourcePanel = new JPanel();
    private JPanel typePanel = new JPanel();
    private JPanel datePanel = new JPanel();
    private ArrayList<JCheckBox> sourceBox = new ArrayList<>();
    private ArrayList<JCheckBox> typeBox = new ArrayList<>();

    public Gui(Object[][] data,HashSet<String> sources, HashSet<String> types) {
        super();
        setLayout(new GridLayout());
        JPanel panel = new JPanel();
        String[] columnNames = {"id", "timestamp", "source id", "source name", "type", "comment"};
        JTable table = new JTable(data, columnNames);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        GridBagConstraints settingsGBC = new GridBagConstraints();
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.anchor = GridBagConstraints.PAGE_START;
        gBC.weightx = 0.5;
        gBC.gridx = 0;
        gBC.gridy = 0;

        panel.add(table, gBC);

        JComboBox jcmbSample = new JComboBox(new String[]{"Search filter","Source","Type","Date"});
        jcmbSample.addActionListener(this);

        settingsPanel.setLayout(new GridBagLayout());
        settingsGBC.anchor = GridBagConstraints.PAGE_START;
        settingsGBC.gridx = 0;
        settingsGBC.gridy = 0;
        settingsGBC.ipady = 1;
        settingsPanel.add(jcmbSample, settingsGBC);

        sourcePanel.setLayout(new GridBagLayout());
        sources.forEach(s->sourceBox.add(new JCheckBox(s,false)));

        GridBagConstraints sourceGBC = new GridBagConstraints();
        sourceGBC.anchor = GridBagConstraints.PAGE_START;
        sourceGBC.gridx = 0;
        sourceGBC.gridwidth = 2;
        sourceGBC.ipady = 1;
        int y = 1;
        for(JCheckBox box : sourceBox) {
            sourceGBC.gridy = y;
            sourcePanel.add(box, sourceGBC);
            y++;
        }

        settingsGBC.gridy = 1;
        settingsGBC.gridwidth = 2;
        settingsGBC.ipady = 1;
        sourcePanel.setVisible(false);
        settingsPanel.add(sourcePanel, settingsGBC);

        typePanel.setLayout(new GridBagLayout());
        types.forEach(s->typeBox.add(new JCheckBox(s,false)));
        typeBox.forEach(s->typePanel.add(s));

        y = 1;
        for(JCheckBox box : typeBox) {
            sourceGBC.gridy = y;
            typePanel.add(box, sourceGBC);
            y++;
        }

        typePanel.setVisible(false);
        settingsPanel.add(typePanel, settingsGBC);

        datePanel.setLayout(new GridBagLayout());
        int dateformat = DateFormat.DEFAULT;
        try {
            MaskFormatter formatter = new MaskFormatter("####-##-##");
            JFormattedTextField sinceDate = new JFormattedTextField(formatter);
            sinceDate.setColumns(10);
            datePanel.add(sinceDate);
            JFormattedTextField toDate = new JFormattedTextField(formatter);
            toDate.setColumns(10);
            datePanel.add(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        settingsPanel.add(datePanel, sourceGBC);

        JButton button = new JButton("Search");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList <String> toSearchSource = new ArrayList<String>();
                ArrayList <String> toSearchType = new ArrayList<String>();
                for(JCheckBox s:sourceBox) {
                    if (s.isSelected()) {
                        toSearchSource.add(s.getLabel());
                    }
                }
                for(JCheckBox s:typeBox) {
                    if (s.isSelected()) {
                        toSearchType.add(s.getLabel());
                    }
                }
                toSearchSource.forEach(s-> System.out.println(s));
                toSearchType.forEach(s-> System.out.println(s));

                TableRowSorter<TableModel> sorter = new TableRowSorter<>(new DefaultTableModel());
                table.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter("1"));
                /*for(String s : toSearchSource) {
                    sorter.setRowFilter(RowFilter.regexFilter(s, 3));
                }*/
            }
        });
        settingsGBC.gridx = 1;
        settingsGBC.gridy = 0;
        settingsGBC.insets = new Insets(0,5,0,0);
        settingsPanel.add(button, settingsGBC);

        gBC.ipady = 2;
        gBC.weighty = 1.0;
        gBC.anchor = GridBagConstraints.PAGE_START;
        gBC.insets = new Insets(10,10,10,10);
        gBC.gridx = 1;
        gBC.gridwidth = 2;
        gBC.gridy = 0;
        panel.add(settingsPanel,gBC);

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