import java.util.Date;

public class Event {
    public Long created;
    public Long duration;
    public String id;
    public String name;
    public Integer rsvp_limit;
    public String status;
    public Long time;
    public Date dt_time;//not coming from api
    public String st_time;//not coming from api
    public String local_date;
    public String local_time;
    public Integer waitlist_count;
    public Integer yes_rsvp_count;
    public Boolean is_online_event;
    public String visibility;

    //fields from Group.
    public Integer group_id;
    public String urlname;
    public String state;

    public Event() {
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRsvp_limit() {
        return rsvp_limit;
    }

    public void setRsvp_limit(Integer rsvp_limit) {
        this.rsvp_limit = rsvp_limit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Date getDt_time() {
        return dt_time;
    }

    public void setDt_time(Date dt_time) {
        this.dt_time = dt_time;
    }

    public String getSt_time() {
        return st_time;
    }

    public void setSt_time(String st_time) {
        this.st_time = st_time;
    }

    public String getLocal_date() {
        return local_date;
    }

    public void setLocal_date(String local_date) {
        this.local_date = local_date;
    }

    public String getLocal_time() {
        return local_time;
    }

    public void setLocal_time(String local_time) {
        this.local_time = local_time;
    }

    public Integer getWaitlist_count() {
        return waitlist_count;
    }

    public void setWaitlist_count(Integer waitlist_count) {
        this.waitlist_count = waitlist_count;
    }

    public Integer getYes_rsvp_count() {
        return yes_rsvp_count;
    }

    public void setYes_rsvp_count(Integer yes_rsvp_count) {
        this.yes_rsvp_count = yes_rsvp_count;
    }

    public Boolean getIs_online_event() {
        return is_online_event;
    }

    public void setIs_online_event(Boolean is_online_event) {
        this.is_online_event = is_online_event;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }
}