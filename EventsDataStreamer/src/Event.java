import java.util.Date;

public class Event {
    public Date created;

    public Event() {
    }

    public Event(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
