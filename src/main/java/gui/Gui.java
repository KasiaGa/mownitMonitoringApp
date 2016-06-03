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
    private JPanel sourcePanel = new JPanel();
    private JPanel typePanel = new JPanel();
    private JPanel datePanel = new JPanel();
    private ArrayList<JCheckBox> sourceBox = new ArrayList<>();
    private ArrayList<JCheckBox> typeBox = new ArrayList<>();
    private JFormattedTextField sinceDate;
    private JFormattedTextField toDate;

    public Gui(Object[][] data,HashSet<String> sources, HashSet<String> types) {
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
                ArrayList <String> toSearchSource = new ArrayList<String>();
                ArrayList <String> toSearchType = new ArrayList<String>();
                String sinceDate1 = sinceDate.getText();
                String toDate1 = toDate.getText();
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

                ArrayList<RowFilter<TableModel, Integer>> sourceFilters = new ArrayList<>();
                ArrayList<RowFilter<TableModel, Integer>> typeFilters = new ArrayList<>();

                for(String s : toSearchSource) {
                    sourceFilters.add(RowFilter.regexFilter(s, 3));
                }

                for(String s : toSearchType) {
                    typeFilters.add(RowFilter.regexFilter(s, 4));
                }

                /*if(sinceDateD == null && toDateD == null) {

                }*/

                /*if(toSearchSource.isEmpty() && toSearchType.isEmpty() && sinceDateD == null && toDateD == null) {
                    sorter.setRowFilter(null);
                }
                else if(toSearchSource.isEmpty()) {
                    sorter.setRowFilter(RowFilter.orFilter(typeFilters));
                }
                else if(toSearchType.isEmpty()) {
                    sorter.setRowFilter(RowFilter.orFilter(sourceFilters));
                }
                else {
                    sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(typeFilters), RowFilter.orFilter(sourceFilters))));
                }*/
                int counter =0;
                if(!(sinceDateD == null || toDateD == null)){
                    counter += 1;
                }
                if(!toSearchSource.isEmpty()) {
                    counter+=2;
                }
                if(!toSearchType.isEmpty()) {
                    counter += 4;
                }

                switch(counter){
                    case 1:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))));
                        break;
                    case 2:
                        sorter.setRowFilter(RowFilter.orFilter(sourceFilters));
                        break;
                    case 3:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(sourceFilters))));
                        break;
                    case 4:
                        sorter.setRowFilter(RowFilter.orFilter(typeFilters));
                        break;
                    case 5:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(typeFilters))));
                        break;
                    case 6:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(typeFilters), RowFilter.orFilter(sourceFilters))));
                        break;
                    case 7:
                        sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.andFilter(Arrays.asList(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, sinceDateD),
                                RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, toDateD))), RowFilter.orFilter(typeFilters), RowFilter.orFilter(sourceFilters))));
                        break;
                    default:
                        sorter.setRowFilter(null);
                        break;
                }

                toSearchSource.clear();
                toSearchType.clear();
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
                sourcePanel.setVisible(false);
                typePanel.setVisible(true);
                datePanel.setVisible(false);
                break;
            case "Date":
                sourcePanel.setVisible(false);
                typePanel.setVisible(false);
                datePanel.setVisible(true);
                break;
            case "Monitoring Address":
                sourcePanel.setVisible(true);
                typePanel.setVisible(false);
                datePanel.setVisible(false);
                break;
            case "Name":
                sourcePanel.setVisible(false);
                typePanel.setVisible(false);
                datePanel.setVisible(false);
                break;
            default:
                sourcePanel.setVisible(false);
                typePanel.setVisible(false);
                datePanel.setVisible(false);
                break;
        }
    }
}