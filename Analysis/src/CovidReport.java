public class CovidReport {

    public Integer id;
    public int date;
    public String state;
    public Integer cases;
    public Integer rsvp;

    public CovidReport() {
    }

    public CovidReport(Integer id, int date, String state, Integer cases, Integer rsvp) {
        this.id = id;
        this.date = date;
        this.state = state;
        this.cases = cases;
        this.rsvp = rsvp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getRsvp() {
        return rsvp;
    }

    public void setRsvp(Integer rsvp) {
        this.rsvp = rsvp;
    }

    @Override
    public String toString() {
        return "CovidReport{" +
                "id=" + id +
                ", date=" + date +
                ", state='" + state + '\'' +
                ", cases=" + cases +
                ", rsvp=" + rsvp +
                '}';
    }
}
