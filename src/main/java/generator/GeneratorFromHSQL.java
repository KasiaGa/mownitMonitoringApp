package generator;

import database.CapabilitiesObject;
import database.DBObject;
import database.MonitoringCaseObject;
import database.ResultObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneratorFromHSQL {

    private List<ResultObject> resultObjects;
    private List<CapabilitiesObject> capabilitiesObjects;
    private List<MonitoringCaseObject> monitoringCaseObjects;
    public List<DBObject> dbObjects;

    public GeneratorFromHSQL() {
        resultObjects = new ArrayList<>();
        capabilitiesObjects = new ArrayList<>();
        monitoringCaseObjects = new ArrayList<>();
        dbObjects = new ArrayList<>();
    }

    private void readFile() {
        String line;
        int index1, index2, isString;
        try {
            Scanner s = new Scanner(new File("src/main/resources/monitorings.log"));
            while (s.hasNext()) {
                line = s.nextLine();
                if (line.startsWith("INSERT INTO MONITORING_CASE VALUES")) {
                    MonitoringCaseObject monitoringCaseObject = new MonitoringCaseObject();
                    index1 = line.indexOf('(');
                    index2 = line.indexOf(',', index1 + 1);
                    monitoringCaseObject.setId(line.substring(index1 + 1, index2));

                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    monitoringCaseObject.setAgentAddress(line.substring(index1 + 1, index2));

                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    monitoringCaseObject.setMonitoringCase(line.substring(index1 + 1, index2));

                    monitoringCaseObjects.add(monitoringCaseObject);
                } else if (line.startsWith("INSERT INTO MEASURABLE_CAPABILITIES VALUES")) {
                    CapabilitiesObject capabilitiesObject = new CapabilitiesObject();
                    index1 = line.indexOf('(');
                    index2 = line.indexOf(',', index1 + 1);
                    capabilitiesObject.setId(line.substring(index1 + 1, index2));

                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    capabilitiesObject.setName(line.substring(index1 + 1, index2));

                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    capabilitiesObject.setType(line.substring(index1 + 1, index2));

                    capabilitiesObjects.add(capabilitiesObject);
                } else if (line.startsWith("INSERT INTO MONITORING_RESULT VALUES")) {
                    ResultObject resultObject = new ResultObject();
                    index1 = line.indexOf('(');
                    index2 = line.indexOf(',', index1 + 1);
                    resultObject.setId(line.substring(index1 + 1, index2));

                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    resultObject.setMonitoringID(line.substring(index1 + 1, index2));

                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    resultObject.setCapabilitiesID(line.substring(index1 + 1, index2));

                    index1 = index2;
                    isString = line.indexOf('\'', index1);
                    if (isString == -1) {
                        index2 = line.indexOf(',', index1 + 1);
                        resultObject.setResult(line.substring(index1 + 1, index2));
                    } else {
                        index1 = isString;
                        index2 = line.indexOf('\'', index1 + 1);
                        resultObject.setResult(line.substring(index1, index2 + 1));
                        index2++;
                    }

                    index1 = index2;
                    index2 = line.indexOf(',', index1 + 1);
                    resultObject.setTimestamp(line.substring(index1 + 1, index2));

                    resultObjects.add(resultObject);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        readFile();
        for (ResultObject result : resultObjects) {
            System.out.println(result.toString());
        }
    }

    public void joinData() {
        readFile();
        for (ResultObject result : resultObjects) {
            database.DBObject dbObject = new database.DBObject();
            dbObject.setId(result.getId());
            dbObject.setResult(result.getResult());
            dbObject.setTimestamp(result.getTimestamp());
            for (MonitoringCaseObject mcObjecct : monitoringCaseObjects) {
                if (mcObjecct.getId().equals(result.getMonitoringID())) {
                    dbObject.setMonitoringCase(mcObjecct.getMonitoringCase());
                    dbObject.setAgentAddress(mcObjecct.getAgentAddress());
                    break;
                }
            }
            for (CapabilitiesObject cObject : capabilitiesObjects) {
                if (cObject.getId().equals(result.getCapabilitiesID())) {
                    dbObject.setName(cObject.getName());
                    dbObject.setType(cObject.getType());
                    break;
                }
            }
            dbObjects.add(dbObject);
        }
    }
}
