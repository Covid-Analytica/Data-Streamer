public class Distance implements Comparable{
    public int index;
    public int cases;
    public int rsvps;
    public double distance;

    public Distance() {
    }

    public Distance(int index, int cases, int rsvps, double distance) {
        this.index = index;
        this.cases = cases;
        this.rsvps = rsvps;
        this.distance = distance;
    }

    public Distance(int index, int cases, double distance) {
        this.index = index;
        this.cases = cases;
        this.distance = distance;
    }

    public int getRsvps() {
        return rsvps;
    }

    public void setRsvps(int rsvps) {
        this.rsvps = rsvps;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "index=" + index +
                ", cases=" + cases +
                ", rsvps=" + rsvps +
                ", distance=" + distance +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (this.distance == ((Distance) o).distance) {
            return 0;
        } else if (this.distance > ((Distance) o).distance) {
            return 1;
        }
        return -1;
    }
}