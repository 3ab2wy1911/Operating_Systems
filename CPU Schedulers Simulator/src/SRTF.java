import java.util.*;

public class SRTF extends Scheduler {

    public SRTF(List<Process> processes) {
        super(processes);
    }

    // ----------------------------------------------------------------

    public void run() {
        int clock = 0;
        List<Process> readyQueue = new LinkedList<>();
        while (true) {
            // Add processes to ready queue
            for (Process process : processes) {
                if (process.getArrivalTime() == clock) {
                    readyQueue.add(process);
                }
            }

            // Sort ready queue by burst time
            Collections.sort(readyQueue, new Comparator<Process>() {
                @Override
                public int compare(Process p1, Process p2) {
                    return p1.getBurstTime() - p2.getBurstTime();
                }
            });

            // Run process
            if (readyQueue.size() > 0) {
                Process process = readyQueue.get(0);
                process.setBurstTime(process.getBurstTime() - 1);
                if (process.getBurstTime() == 0) {
                    process.setTurnaroundTime(clock + 1 - process.getArrivalTime());
                    readyQueue.remove(0);
                }
            }

            // Increment waiting time for processes in ready queue
            for (Process process : readyQueue) {
                process.setWaitingTime(process.getWaitingTime() + 1);
            }

            // Increment clock
            clock++;

            // Check if all processes are done
            boolean done = true;
            for (Process process : processes) {
                if (process.getBurstTime() > 0) {
                    done = false;
                    break;
                }
            }
            if (done) {
                break;
            }
            
        }
    }

    // ----------------------------------------------------------------
    public void output() {
        System.out.println("================================================");
        System.out.println("SRTF Output:");
        super.output();
    }

    // ----------------------------------------------------------------

}