package gui;

import database.Connection;
import database.DBObject;
import database.DatabaseObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Logic {

    private Connection connection;
    private static int NUM_OF_COLUMNS = 6;

    public Logic() {
        connection = new Connection();
    }

    Object[][] getTableData() throws ParseException {
        int numOfRecords = connection.count();
        Object[][] data = new Object[numOfRecords][NUM_OF_COLUMNS];
        List<DBObject> databaseObjectList = connection.getData();
        for (int i = 0; i < numOfRecords; i++) {
            data[i][0] = databaseObjectList.get(i).getId();
            data[i][1] = databaseObjectList.get(i).getResult();
            data[i][2] = databaseObjectList.get(i).getTimestamp();
            data[i][3] = databaseObjectList.get(i).getMonitoringCase();
            data[i][4] = databaseObjectList.get(i).getAgentAddress();
            data[i][5] = databaseObjectList.get(i).getName();
        }
        return data;
    }

    HashSet<String> getDistinctMonitoringCase() {
        List<DBObject> databaseObjectList = connection.getData();
        return databaseObjectList.stream().map(DBObject::getMonitoringCase).collect(Collectors.toCollection(HashSet::new));
    }

    HashSet<String> getDistinctNames() {
        List<DBObject> databaseObjectList = connection.getData();
        return databaseObjectList.stream().map(DBObject::getName).collect(Collectors.toCollection(HashSet::new));
    }

    HashSet<String> getDistinctAgentAddress() {
        List<DBObject> databaseObjectList = connection.getData();
        return databaseObjectList.stream().map(DBObject::getAgentAddress).collect(Collectors.toCollection(HashSet::new));
    }
}
