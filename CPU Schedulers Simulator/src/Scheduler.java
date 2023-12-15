import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    double avgTurnAroundTime, avgWaitingTime;
    protected List<Process> processes ;
    protected List<Process> newProcesses ;
    protected List<Process> afterProcessing;
    protected List<Integer> QuantumUpdated;
    List<String> order;
    //----------------------------------------------------------------

    public Scheduler(List<Process> processes) {
        this.processes = new ArrayList<>(processes);
        this.newProcesses = new ArrayList<>();
        this.afterProcessing = new ArrayList<>(processes);
        this.order = new ArrayList<>();
        this.QuantumUpdated = new ArrayList<>();
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
    public void outputFinalAg(){
        System.out.println("----------------------------------------------------------------");
        System.out.println("AG Scheduling : ");

        System.out.println("----------------------------------------------------------------");
        System.out.println("Switching Processes in Cpu:");
        for (Process process : newProcesses) {
            System.out.print(process.getName() + " ");
        }

        System.out.println("\n----------------------------------------------------------------");
        System.out.println("Processes Execution Order: ");
        for (int i = newProcesses.size() - 1; i >= 0; i--){
            if (findIfExists(newProcesses.get(i).getName())){
                order.add(newProcesses.get(i).getName());
            }
        }
        for (int i = order.size() - 1; i >= 0; i--){
            System.out.print(order.get(i) + " ");
        }

        System.out.println("\n----------------------------------------------------------------");
        System.out.println("Quantum Time: ");
        for (int i = 0; i < newProcesses.size(); i++){
            System.out.println(newProcesses.get(i).getName() + " " + QuantumUpdated.get(i));
        }
        System.out.println("\n----------------------------------------------------------------");

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
    public boolean findIfExists(String process){
        for (String str : order){
            if (process.equals(str)){
                return false;
            }
        }
        return true;
    }

    //----------------------------------------------------------------

}