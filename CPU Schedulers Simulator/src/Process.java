import java.util.Objects;
import java.util.Random;

public class Process {
    private String name;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private  int currentQuantumTime;

    public int getAGFactor() {
        return AGFactor;
    }

    public int getRandomNumber() {
        return randomNumber;
    }


    private int priority;
    private int AGFactor;
    private int randomNumber;
    private int waitingTime;
    private int turnaroundTime;

    //----------------------------------------------------------------

    public Process(String name, String color, int arrivalTime, int burstTime, int priority, int AGFactor) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.randomNumber =new Random().nextInt(20);
        this.AGFactor = AGFactor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(name, process.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    //----------------------------------------------------------------

    public void setCurrentQuantumTime(int currentQuantumTime) {
        this.currentQuantumTime = currentQuantumTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getCurrentQuantumTime() {
        return currentQuantumTime;
    }

    private int calculateAGFactor()
    {
        if(this.randomNumber < 10)
            return this.randomNumber + this.arrivalTime + this.burstTime;

        else if(this.randomNumber > 10)
            return 10 + this.arrivalTime + this.burstTime;

        else
            return this.priority + this.arrivalTime + this.burstTime;


    }


//----------------------------------------------------------------
}
