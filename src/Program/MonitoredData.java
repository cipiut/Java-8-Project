package Program;

public class MonitoredData {
    private String startTime ;
    private String endTime ;
    private String activity ;

    public MonitoredData(String startTime, String endTime, String activity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
    }

    public void print(){
        System.out.println("Start: "+startTime+" End: "+endTime+" Activity: "+activity);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getActivity() {
        return activity;
    }

}