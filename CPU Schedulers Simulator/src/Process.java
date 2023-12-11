public class Process {
    private String name;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    private int waitingTime;
    private int turnaroundTime;

    //----------------------------------------------------------------

    public Process(String name, String color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;

        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    //----------------------------------------------------------------

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    //----------------------------------------------------------------

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }


//----------------------------------------------------------------
}
