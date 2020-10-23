import javax.xml.crypto.dom.DOMCryptoContext;
import java.io.DataOutput;
import java.io.Serializable;

public class Group implements Serializable {
    public Double score;
    public Integer id;
    public String name;
    public String urlname;
    public String status;
    public String city;
    public String country;
    public String localized_location;
    public String state;
    public String visibility;
    public Integer members;
    public Double lat;
    public Double lon;
    public String join_mode;

    public Group() {
    }

    public Group(Double score, Integer id, String name, String urlname, String status, String city, String country, String localized_location, String state, String visibility, Integer members, Double lat, Double lon, String join_mode) {
        this.score = score;
        this.id = id;
        this.name = name;
        this.urlname = urlname;
        this.status = status;
        this.city = city;
        this.country = country;
        this.localized_location = localized_location;
        this.state = state;
        this.visibility = visibility;
        this.members = members;
        this.lat = lat;
        this.lon = lon;
        this.join_mode = join_mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String  getJoin_mode() {
        return join_mode;
    }

    public void setJoin_mode(String  join_mode) {
        this.join_mode = join_mode;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalized_location() {
        return localized_location;
    }

    public void setLocalized_location(String localized_location) {
        this.localized_location = localized_location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
