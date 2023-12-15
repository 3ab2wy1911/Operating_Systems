import java.util.*;

public class SRTF extends Scheduler {
    List<Process> executionProcesses;

    public SRTF(List<Process> processes) {
        super(processes);
        this.executionProcesses = new ArrayList<Process>();
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

            // Decrease biggest burst time by one
            // if (clock % 25 == 0 && clock >= 25) {

            //     for (Process process : processes) {
            //         if (process.getName().equals(tmpQueue.get(tmpQueue.size() - 1).getName())) {
            //             process.setBurstTime(process.getBurstTime() - 5);
            //         }
            //     }
            // }
            // tmpQueue.get(tmpQueue.size()-1).setBurstTime(tmpQueue.get(tmpQueue.size()-1).getBurstTime()-1);

            // setting waiting time for each process
            if (clock != 0 && readyQueue.get(clock - 1).getName() != readyQueue.get(clock).getName()) {
                for (Process process : processes) {
                    int tmp = 0;
                    if (readyQueue.get(clock).getName().equals(process.getName())) {
                        for (Process process2 : tmProcesses) {
                            if (process2.getName().equals(process.getName())) {
                                tmp = process.getBurstTime() - process2.getBurstTime();
                                break;
                            }
                        }
                        process.setWaitingTime(clock - process.getArrivalTime() - tmp);
                        // process.setArrivalTime(clock + 1);
                    }
                }
            }
            if (clock == 0) {
                for (Process process : processes) {
                    if (readyQueue.get(clock).getName().equals(process.getName())) {
                        process.setWaitingTime(0);
                        // process.setArrivalTime(clock + 1);
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
        List<Process> readyQueue2 = new ArrayList<>();
        readyQueue2.addAll(readyQueue);

        Collections.reverse(readyQueue2);

        for (Process process : readyQueue2) {

            if (executionProcesses.isEmpty()) {
                executionProcesses.add(process);
                continue;
            }
            boolean flag = false;
            for (Process p : executionProcesses) {
                if (p.getName().equals(process.getName())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                executionProcesses.add(process);
            }
        }
        Collections.reverse(executionProcesses);
    }

    // ----------------------------------------------------------------
    @Override
    public void output() {
        System.out.println("\n\n================================================");
        System.out.println("SRTF Output:");
        System.out.println("================================================");
        System.out.println("Processes Execution Order:");
        for (Process process : executionProcesses) {
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

    // ----------------------------------------------------------------

}