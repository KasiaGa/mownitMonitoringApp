package database;

public class MonitoringCaseObject {
    String id;
    String monitoringCase;
    String agentAddress;

    public void setId(String id) {
        this.id = id;
    }

    public void setMonitoringCase(String monitoringCase) {
        this.monitoringCase = monitoringCase;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public String getId() {
        return id;
    }

    public String getMonitoringCase() {
        return monitoringCase;
    }

    public String getAgentAddress() {
        return agentAddress;
    }
}
