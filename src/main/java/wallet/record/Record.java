package wallet.record;

import java.util.Date;

public abstract class Record {
    private String description;
    private Date createdDate;

    public Record(String description, Date createdDate) {
        this.description = description;
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}