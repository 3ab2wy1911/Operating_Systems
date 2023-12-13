import java.util.*;

public class PriorityScheduler extends Scheduler{
    public PriorityScheduler(List<Process> processes){
        super(processes);
    }

    //----------------------------------------------------------------

    public void run() {
        // Ready Queue & Processes
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));

        int endTime = 0;


        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Filling the ready queue with the processes according to the arrival time.
            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= endTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {

                Process process = Objects.requireNonNull(readyQueue.peek());

                // Execute the process
                endTime += process.getBurstTime();
                Objects.requireNonNull(readyQueue.peek()).setPriority(readyQueue.peek().getPriority()-1);

                // Update Waiting time and Turnaround time
                process.setWaitingTime(endTime - process.getArrivalTime() - process.getBurstTime());    // A - S
                process.setTurnaroundTime(endTime - process.getArrivalTime()); // E - A
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

    public void output() {
        System.out.println("\n\n================================================");
        System.out.println("Priority Scheduler Output:");
        System.out.println("________________________________________________________________");
        super.output();
    }
}
