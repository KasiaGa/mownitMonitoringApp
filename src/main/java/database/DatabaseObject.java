package database;

public class DatabaseObject {

    private int id;
    private String timestamp;
    private String sourceName;
    private int sourceId;
    private String type;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSourceName() {
        return sourceName;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
