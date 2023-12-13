import java.util.*;

public class SRTF extends Scheduler {

    public SRTF(List<Process> processes) {
        super(processes);
    }

    // ----------------------------------------------------------------

    public void run() {
        int clock = 0;
        List<Process> readyQueue = new LinkedList<>();
        List<Process> tmProcesses = new LinkedList<>();
        for (Process process : processes) {
            tmProcesses.add(new Process(process.getName(), process.getColor(), process.getArrivalTime(),
                    process.getBurstTime(), process.getPriority(), process.getAGFactor()));
        }
        while (true) {
            List<Process> tmpQueue = new LinkedList<>();
            
            // Add processes to ready queue
            for (Process process : tmProcesses) {
                if (process.getArrivalTime() <= clock) {
                    tmpQueue.add(process);
                }
            }
            Collections.sort(tmpQueue, new Comparator<Process>() {
                @Override
                public int compare(Process p1, Process p2) {
                    return p1.getBurstTime() - p2.getBurstTime();
                }
            });
            readyQueue.add(tmpQueue.get(0));

            // setting waiting time for each process
            if (clock != 0 && readyQueue.get(clock - 1).getName() != readyQueue.get(clock).getName()) {
                for (Process process : processes) {
                    if (readyQueue.get(clock).getName().equals(process.getName())) {
                        process.setWaitingTime(clock - process.getArrivalTime());
                        process.setArrivalTime(clock + 1);
                    }
                }
            }
            if (clock == 0) {
                for (Process process : processes) {
                    if (readyQueue.get(clock).getName().equals(process.getName())) {
                        process.setWaitingTime(clock - process.getArrivalTime());
                        process.setArrivalTime(clock + 1);
                    }
                }
            }

            // decrease burst time of the process by one
            tmpQueue.get(0).setBurstTime(tmpQueue.get(0).getBurstTime() - 1);
            if (tmpQueue.get(0).getBurstTime() == 0) {
                for (Process process : tmProcesses) {
                    if (process.getName() == tmpQueue.get(0).getName()) {
                        tmProcesses.remove(process);
                        break;
                    }

                }
            }

            // Increment clock
            clock++;

            // Check if all processes are done
            boolean done = true;
            for (Process process : tmProcesses) {
                if (process.getBurstTime() > 0) {
                    done = false;
                    break;
                }
            }
            if (done) {
                break;
            }
        }

        // Calculate turnaround times
        for (Process process : processes) {
            process.setTurnaroundTime(process.getBurstTime() + process.getWaitingTime());
        }
        // Calculate averages
        for (Process process : processes) {
            avgWaitingTime += process.getWaitingTime();
            avgTurnAroundTime += process.getTurnaroundTime();
            newProcesses.add(process);
        }
        avgWaitingTime /= newProcesses.size();
        avgTurnAroundTime /= newProcesses.size();

    }

    // ----------------------------------------------------------------
    public void output() {
        System.out.println("================================================");
        System.out.println("SRTF Output:");
        super.output();
    }

    // ----------------------------------------------------------------

}