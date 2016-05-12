package gui;

import database.Connection;
import database.DatabaseObject;

import java.util.HashSet;
import java.util.List;

public class Logic {

    private Connection connection;
    private static int NUM_OF_COLUMNS = 6;

    public Logic() {
        connection = new Connection();
    }

    Object[][] getTableData() {
        int numOfRecords = connection.count();
        Object[][] data = new Object[numOfRecords][NUM_OF_COLUMNS];
        List<DatabaseObject> databaseObjectList = connection.getData();
        for (int i = 0; i < numOfRecords; i++) {
            data[i][0] = databaseObjectList.get(i).getId();
            data[i][1] = databaseObjectList.get(i).getTimestamp();
            data[i][2] = databaseObjectList.get(i).getSourceId();
            data[i][3] = databaseObjectList.get(i).getSourceName();
            data[i][4] = databaseObjectList.get(i).getType();
            data[i][5] = databaseObjectList.get(i).getComment();
        }
        return data;
    }

    HashSet<String> getDistinctSources() {
        List<DatabaseObject> databaseObjectList = connection.getData();
        HashSet<String> sources = new HashSet<>();
        for(DatabaseObject object : databaseObjectList) {
            sources.add(object.getSourceName());
        }
        return sources;
    }

    HashSet<String> getDistinctTypes() {
        List<DatabaseObject> databaseObjectList = connection.getData();
        HashSet<String> types = new HashSet<>();
        for(DatabaseObject object : databaseObjectList) {
            types.add(object.getType());
        }
        return types;
    }
}
