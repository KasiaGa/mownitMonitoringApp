package database;

public class ResultObject {
    private String id;
    private String result;
    private String timestamp;
    private String monitoringID;
    private String capabilitiesID;

    public String getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMonitoringID() {
        return monitoringID;
    }

    public String getCapabilitiesID() {
        return capabilitiesID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMonitoringID(String monitoringID) {
        this.monitoringID = monitoringID;
    }

    public void setCapabilitiesID(String capabilitiesID) {
        this.capabilitiesID = capabilitiesID;
    }

    @Override
    public String toString() {
        return "ResultObject{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", monitoringID='" + monitoringID + '\'' +
                ", capabilitiesID='" + capabilitiesID + '\'' +
                '}';
    }
}
