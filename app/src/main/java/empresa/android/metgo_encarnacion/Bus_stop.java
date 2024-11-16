package empresa.android.metgo_encarnacion;

public class Bus_stop {

    private String busCode;
    private String busName;
    private int timeArrival;
    private int timeEstimated;

    public Bus_stop(String busCode, String busName, int timeArrival, int timeEstimated) {
        this.busCode = busCode;
        this.busName = busName;
        this.timeArrival = timeArrival;
        this.timeEstimated = timeEstimated;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public int getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(int timeArrival) {
        this.timeArrival = timeArrival;
    }

    public int getTimeEstimated() {
        return timeEstimated;
    }

    public void setTimeEstimated(int timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public void decrementTimes() {
        if (timeArrival > 0) {
            timeArrival --;
        }
        if (timeEstimated > 0) {
            timeEstimated--;
        }
    }

 public String getFormattedArrivalTime() {
        return String.format("%02d:%02d", timeArrival / 60, timeArrival % 60);
    }

    public String getFormattedEstimatedTime() {
        return String.format("%02d:%02d", timeEstimated / 60, timeEstimated % 60);
    }

}
