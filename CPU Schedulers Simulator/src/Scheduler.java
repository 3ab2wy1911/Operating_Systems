import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    double avgTurnAroundTime, avgWaitingTime;
    protected List<Process> processes ;
    protected List<Process> newProcesses ;
    protected List<Process> afterProcessing;

    //----------------------------------------------------------------

    public Scheduler(List<Process> processes) {
        this.processes = new ArrayList<>(processes);
        this.newProcesses = new ArrayList<>();
        this.afterProcessing = new ArrayList<>(processes);
    }

    //----------------------------------------------------------------

    public abstract void run();

    //----------------------------------------------------------------

    public void output(){
        // Print processes execution order
        System.out.println("Processes Execution Order:");
        for (Process process : newProcesses) {
            System.out.println(process.getName());
        }


        System.out.println("----------------------------------------------------------------");

        // Print waiting time and turnaround time for each process
        System.out.println("Waiting Time and Turnaround Time for Each Process:");
        for (Process process : newProcesses) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Process " + process.getName());
            System.out.println("Waiting Time = " + process.getWaitingTime());
            System.out.println("Turnaround Time = " + process.getTurnaroundTime());
        }

        System.out.println("----------------------------------------------------------------");

        // Print average waiting time and average turnaround time
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
    }
    public void outputFinalAg(){    // You were able to update the original fn in your code, dirty coder...
        System.out.println("Processes Execution Order:");
        for (Process process : newProcesses) {
            System.out.println(process.getName());
        }

        System.out.println("----------------------------------------------------------------");
        // Print waiting time and turnaround time for each process
        System.out.println("Waiting Time and Turnaround Time for Each Process:");
        for (Process process : afterProcessing) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Process " + process.getName());
            System.out.println("Waiting Time = " + process.getWaitingTime());
            System.out.println("Turnaround Time = " + process.getTurnaroundTime());
        }

        System.out.println("----------------------------------------------------------------");

        // Print average waiting time and average turnaround time
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
    }

    //----------------------------------------------------------------

}