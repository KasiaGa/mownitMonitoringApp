package gui;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class Gui extends JFrame implements ActionListener {

    private JPanel settingsPanel = new JPanel();
    private JPanel namePanel = new JPanel();
    private JPanel monitorPanel = new JPanel();
    private JPanel datePanel = new JPanel();
    private JPanel addressPanel = new JPanel();
    private ArrayList<JCheckBox> nameBox = new ArrayList<>();
    private ArrayList<JCheckBox> monitorBox = new ArrayList<>();
    private ArrayList<JCheckBox> addressBox = new ArrayList<>();
    private JFormattedTextField sinceDate;
    private JFormattedTextField toDate;

    public Gui(Object[][] data,HashSet<String> names, HashSet<String> monitors, HashSet<String> addresses) {
        super();
        setPreferredSize(new Dimension(1000, 600));
        setLayout(new GridLayout());
        JPanel panel = new JPanel();
        CustomTableModel model = new CustomTableModel(data);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gBC = new GridBagConstraints();
        GridBagConstraints settingsGBC = new GridBagConstraints();
        gBC.fill = GridBagConstraints.HORIZONTAL;
        gBC.anchor = GridBagConstraints.PAGE_START;
        gBC.weightx = 0.5;
        gBC.gridx = 0;
        gBC.gridy = 0;

        panel.add(table, gBC);

        JComboBox jcmbSample = new JComboBox(new String[]{"Search filter","Agent Address","Date","Monitoring Case","Name"});
        jcmbSample.addActionListener(this);

        settingsPanel.setLayout(new GridBagLayout());
        settingsGBC.anchor = GridBagConstraints.PAGE_START;
        settingsGBC.gridx = 0;
        settingsGBC.gridy = 0;
        settingsGBC.ipady = 1;
        settingsPanel.add(jcmbSample, settingsGBC);

        addressPanel.setLayout(new GridBagLayout());
        addresses.forEach(s->addressBox.add(new JCheckBox(s,false)));

        GridBagConstraints addressGBC = new GridBagConstraints();
        addressGBC.anchor = GridBagConstraints.PAGE_START;
        addressGBC.gridx = 0;
        addressGBC.gridwidth = 2;
        addressGBC.ipady = 1;
        int y = 1;
        for(JCheckBox box : addressBox) {
            addressGBC.gridy = y;
            addressPanel.add(box, addressGBC);
            y++;
        }

        settingsGBC.gridy = 1;
        settingsGBC.gridwidth = 2;
        settingsGBC.ipady = 1;
        addressPanel.setVisible(false);
        settingsPanel.add(addressPanel, settingsGBC);

        namePanel.setLayout(new GridBagLayout());
        names.forEach(s-> nameBox.add(new JCheckBox(s,false)));

        GridBagConstraints sourceGBC = new GridBagConstraints();
        sourceGBC.anchor = GridBagConstraints.PAGE_START;
        sourceGBC.gridx = 0;
        sourceGBC.gridwidth = 2;
        sourceGBC.ipady = 1;
        y = 1;
        for(JCheckBox box : nameBox) {
            sourceGBC.gridy = y;
            namePanel.add(box, sourceGBC);
            y++;
        }

        namePanel.setVisible(false);
        settingsPanel.add(namePanel, settingsGBC);

        monitorPanel.setLayout(new GridBagLayout());
        monitors.forEach(s-> monitorBox.add(new JCheckBox(s,false)));
        monitorBox.forEach(s-> monitorPanel.add(s));

        y = 1;
        for(JCheckBox box : monitorBox) {
            sourceGBC.gridy = y;
            monitorPanel.add(box, sourceGBC);
            y++;
        }

        monitorPanel.setVisible(false);
        settingsPanel.add(monitorPanel, settingsGBC);

        datePanel.setLayout(new GridBagLayout());
        int dateformat = DateFormat.DEFAULT;

        try {
            MaskFormatter formatter = new MaskFormatter("####-##-##");
            sinceDate = new JFormattedTextField(formatter);
            sinceDate.setColumns(10);
            datePanel.add(sinceDate);
            toDate = new JFormattedTextField(formatter);
            toDate.setColumns(10);
            datePanel.add(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        settingsPanel.add(datePanel, sourceGBC);

        datePanel.setVisible(false);

        JButton button = new JButton("Search");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList <String> toSearchName = new ArrayList<String>();
                ArrayList <String> toSearchMonitor = new ArrayList<String>();
                ArrayList <String> toSearchAddresses = new ArrayList<String>();
                String sinceDate1 = sinceDate.getText();
                String toDate1 = toDate.getText();
                for(JCheckBox s: nameBox) {
                    if (s.isSelected()) {
                        toSearchName.add(s.getLabel());
                    }
                }
                for(JCheckBox s: monitorBox) {
                    if (s.isSelected()) {
                        toSearchMonitor.add(s.getLabel());
                    }
                }
                for(JCheckBox s:addressBox) {
                    if (s.isSelected()) {
                        toSearchAddresses.add(s.getLabel());
                    }
                }
                toSearchName.forEach(s-> System.out.println(s));
                toSearchMonitor.forEach(s-> System.out.println(s));
                //System.out.println(sinceDate1);
                //System.out.println(toDate1);

                Date sinceDateD = null;
                Date toDateD = null;
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if(!sinceDate1.equals("    -  -  ")) sinceDateD = df.parse(sinceDate1);
                    if(!toDate1.equals("    -  -  ")) toDateD = df.parse(toDate1);
                    System.out.println(sinceDateD);
                    System.out.println(toDateD);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<RowFilter<TableModel, Integer>> nameFilters = new ArrayList<>();
                ArrayList<RowFilter<TableModel, Integer>> monitorFilters = new ArrayList<>();
                ArrayList<RowFilter<TableModel, Integer>> addressFilters = new ArrayList<>();

                for(String s : toSearchName) {
                    nameFilters.add(RowFilter.regexFilter(s, 5));
                }

                for(String s : toSearchMonitor) {
                    monitorFilters.add(RowFilter.regexFilter(s, 3));
                }

                for(String s : toSearchAddresses) {
                    addressFilters.add(RowFilter.regexFilter(s, 4));
                }

                int counter =0;
                if(!(sinceDateD == null || toDateD == null)){
                    counter += 1;
                }
                if(!toSearchName.isEmpty()) {
                    counter+=2;
                }
                if(!toSearchMonitor.isEmpty()) {
                    counter += 4;
                }
                if(!toSearchAddresses.isEmpty()) {
                    counter += 8;
                }

                switch(counter){
                    case 1:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD, 2),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD, 2))));
                        break;
                    case 2:
                        sorter.setRowFilter(RowFilter.orFilter(nameFilters));
                        break;
                    case 3:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(nameFilters))));
                        break;
                    case 4:
                        sorter.setRowFilter(RowFilter.orFilter(monitorFilters));
                        break;
                    case 5:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(monitorFilters))));
                        break;
                    case 6:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(monitorFilters), RowFilter.orFilter(nameFilters))));
                        break;
                    case 7:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(monitorFilters), RowFilter.orFilter(nameFilters))));
                        break;
                    case 8:
                        sorter.setRowFilter(RowFilter.orFilter(addressFilters));
                        break;
                    case 9:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(addressFilters))));
                        break;
                    case 10:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(nameFilters), RowFilter.orFilter(addressFilters))));
                        break;
                    case 11:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(addressFilters), RowFilter.orFilter(nameFilters))));
                        break;
                    case 12:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(monitorFilters), RowFilter.orFilter(addressFilters))));
                        break;
                    case 13:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(monitorFilters), RowFilter.orFilter(addressFilters))));
                        break;
                    case 14:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(monitorFilters), RowFilter.orFilter(addressFilters), RowFilter.orFilter(nameFilters))));
                        break;
                    case 15:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(addressFilters), RowFilter.orFilter(nameFilters), RowFilter.orFilter(monitorFilters))));
                        break;
                    default:
                        sorter.setRowFilter(null);
                        break;
                }

                toSearchName.clear();
                toSearchMonitor.clear();
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
            case "Agent Address":
                addressPanel.setVisible(true);
                namePanel.setVisible(false);
                monitorPanel.setVisible(false);
                datePanel.setVisible(false);
                break;
            case "Date":
                addressPanel.setVisible(false);
                namePanel.setVisible(false);
                monitorPanel.setVisible(false);
                datePanel.setVisible(true);
                break;
            case "Monitoring Case":
                addressPanel.setVisible(false);
                namePanel.setVisible(false);
                monitorPanel.setVisible(true);
                datePanel.setVisible(false);
                break;
            case "Name":
                addressPanel.setVisible(false);
                namePanel.setVisible(true);
                monitorPanel.setVisible(false);
                datePanel.setVisible(false);
                break;
            default:
                addressPanel.setVisible(false);
                namePanel.setVisible(false);
                monitorPanel.setVisible(false);
                datePanel.setVisible(false);
                break;
        }
    }
}