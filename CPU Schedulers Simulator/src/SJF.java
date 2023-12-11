import java.util.*;

public class SJF extends Scheduler {

    public SJF(List<Process> processes) {
        super(processes);
    }

    //----------------------------------------------------------------

    public void run() {
        // Ready Queue & Processes
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        List<Process> readyQueue = new LinkedList<>();

        int endTime = 0;


        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Filling the ready queue with the processes according to the arrival time.
            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= endTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {
                readyQueue.sort(Comparator.comparingInt(Process::getBurstTime));

                Process process = readyQueue.remove(0);

                // Execute the process
                endTime += process.getBurstTime();

                // Update Waiting time and Turnaround time
                process.setWaitingTime(endTime - process.getArrivalTime() - process.getBurstTime());    // A - S
                process.setTurnaroundTime(endTime + process.getArrivalTime()); // E - A
                avgTurnAroundTime +=  process.getTurnaroundTime();
                avgWaitingTime += process.getWaitingTime();

                newProcesses.add(process);

            }
            else {
                endTime++;  // No Process at this time.
            }
        }
        avgWaitingTime /= newProcesses.size();
        avgTurnAroundTime /= newProcesses.size();
    }

    //----------------------------------------------------------------
    public void output() {
        System.out.println("================================================");
        System.out.println("SJF Output:");
        super.output();
    }

    //----------------------------------------------------------------

}