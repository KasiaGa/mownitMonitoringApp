package database;

public class DBObject {

    String id;
    String result;
    String timestamp;
    String monitoringCase;
    String agentAddress;
    String name;
    String type;

    public String getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMonitoringCase() {
        return monitoringCase;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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

    public void setMonitoringCase(String monitoringCase) {
        this.monitoringCase = monitoringCase;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
