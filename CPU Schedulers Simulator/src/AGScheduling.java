import java.util.*;


public class AGScheduling extends Scheduler {
    private final int quantumTime;
    private int start;
    private int remainingBurstTime; // for keeping track of the remaining burst
    private List<Integer> endTimeProcesses = new ArrayList<>();
    private final Queue<Process> readyQueue = new LinkedList<>();
    private final List<Process> currentProcesses = new LinkedList<>();
    private final List<Process> dieList = new LinkedList<>();

    public AGScheduling(List<Process> processes, int quantumTime) {
        super(processes);
        this.quantumTime = quantumTime;
        for (Process process : processes) {
            process.setCurrentQuantumTime(quantumTime);
        }
    }

    @Override
    public void run() {
        int runningTime = 0;
        Process currentProcess;

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int time = processes.get(0).getArrivalTime();
        this.start = time;

        addToCurrentProcessesBasedOnTime(time);

        currentProcess = leastAGFactor(); // now the process has the cpu

        int currentQuantumTime = currentProcess.getCurrentQuantumTime();


        time+=nonPreemptiveAG(currentProcess);

        currentQuantumTime-=(time - currentProcess.getArrivalTime());

        runningTime = nonPreemptiveAG(currentProcess);
        currentProcess.setBurstTime(currentProcess.getBurstTime() - runningTime);
        remainingBurstTime = currentProcess.getBurstTime();


        addToCurrentProcessesBasedOnTime(time);

        while (processes.size() > 0) {

            if(currentProcess != leastAGFactor() || currentProcess == null)
            {
                if(currentProcess != null){
//                    System.out.println(currentProcess.getName() + " running\n" + "intrupted By " + leastAGFactor().getName());
                    QuantumUpdated.add(currentProcess.getCurrentQuantumTime());
                    updateQuantumAndBurst(currentProcess, currentQuantumTime);
                    endTimeProcesses.add(time);
                    newProcesses.add(currentProcess);
                    readyQueue.add(currentProcess);
                }

                currentProcess = leastAGFactor();

                currentQuantumTime = currentProcess.getCurrentQuantumTime();

                time += nonPreemptiveAG(currentProcess);

                addToCurrentProcessesBasedOnTime(time);

                runningTime=nonPreemptiveAG(currentProcess);

                currentProcess.setBurstTime(currentProcess.getBurstTime() - runningTime);
                remainingBurstTime = currentProcess.getBurstTime();
                currentQuantumTime -=runningTime;
            }
            else if(currentProcess.getBurstTime() <= 0)
            {
//                System.out.println(currentProcess.getName()+" running");
//                System.out.println("burstTime Finished " + currentProcess.getName());
                dieList.add(currentProcess);


                if(newProcesses.getLast() != currentProcess) {

                    QuantumUpdated.add(currentProcess.getCurrentQuantumTime());
                    newProcesses.add(currentProcess);
                    time += remainingBurstTime;
                    endTimeProcesses.add(time);

                }
                else if (newProcesses.getLast() == currentProcess) {
                    if (currentProcess.getBurstTime() < 0){
                        endTimeProcesses.set(endTimeProcesses.size() - 1, time +  remainingBurstTime);
                    }
                    else {
                        endTimeProcesses.set(endTimeProcesses.size() - 1, time);
                    }

                }
                processes.remove(currentProcess);
                currentProcesses.remove(currentProcess);


                while(readyQueue.contains(currentProcess))
                {
                    readyQueue.remove(currentProcess);
                }
                currentProcess = readyQueue.poll();


                if(currentProcess == null)
                {
                    continue;
                }
                currentQuantumTime =  currentProcess.getCurrentQuantumTime();
                time+=nonPreemptiveAG(currentProcess);
                addToCurrentProcessesBasedOnTime(time);

                runningTime = nonPreemptiveAG(currentProcess);

                currentProcess.setBurstTime(currentProcess.getBurstTime() - runningTime);

                currentQuantumTime-=runningTime;


            }
            else if(currentQuantumTime == 0)
            {
//                System.out.println(currentProcess.getName()+" running");
//                System.out.println("Finish his quantum time " + currentProcess.getName());

                QuantumUpdated.add(currentProcess.getCurrentQuantumTime());
                updateQuantumAndBurst(currentProcess, (int) (Math.ceil(0.1 * meanOfProcesses())));

                newProcesses.add(currentProcess);
                endTimeProcesses.add(time);

                readyQueue.add(currentProcess);

                currentProcess = readyQueue.poll();

                if(currentProcess == null)
                {
                    continue;
                }
                currentQuantumTime =  currentProcess.getCurrentQuantumTime();

                time+=nonPreemptiveAG(currentProcess);

                addToCurrentProcessesBasedOnTime(time);

                runningTime =nonPreemptiveAG(currentProcess);
                currentProcess.setBurstTime(currentProcess.getBurstTime() - runningTime);
                remainingBurstTime = currentProcess.getBurstTime();
                currentQuantumTime-=runningTime;
            }


            else {
                time += 1;
                runningTime+=1;
                currentQuantumTime--;
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
                remainingBurstTime = currentProcess.getBurstTime();
                addToCurrentProcessesBasedOnTime(time);

            }
        }
        waitingTime(start);

    }
    public int meanOfProcesses()
    {
        int sumOfQuantum= 0;
        for (int i = 0; i < processes.size();i++)
        {
            sumOfQuantum+=processes.get(i).getCurrentQuantumTime();
        }
        return (sumOfQuantum / processes.size());

    }
    public void updateQuantumAndBurst(Process currentProcess,int quantumTime)
    {
        for(int i = 0; i < processes.size();i++)
        {
            if(currentProcess == processes.get(i))
            {
                processes.get(i).setCurrentQuantumTime(processes.get(i).getCurrentQuantumTime() + quantumTime);
                processes.get(i).setBurstTime(currentProcess.getBurstTime());
                //remainingBurstTime =  processes.get(i).getBurstTime();
                break;
            }
        }
    }
    public Process leastAGFactor()
    {
        currentProcesses.sort(Comparator.comparingInt(Process::getAGFactor));
        return currentProcesses.get(0);

    }
    public int nonPreemptiveAG(Process p) {
        return (int) Math.ceil(p.getCurrentQuantumTime() * 0.5);
    }
    // gets all process at the time given which arrived
    public void addToCurrentProcessesBasedOnTime(int time)
    {
        currentProcesses.clear();
        for (int i = 0; i < processes.size(); i++)
        {
            if(processes.get(i).getArrivalTime() <=time)
            {
                currentProcesses.add(processes.get(i));
            }
        }
    }
    public void waitingTime(int start) {
        boolean firstProcess = true;
        List<List<Integer>> temp = new ArrayList<>();
        List<Integer> startOfEachProcess = new ArrayList<>();
        startOfEachProcess.add(start);
        for (int i = 1; i < newProcesses.size(); i++) {
            startOfEachProcess.add(Math.min(endTimeProcesses.get(i), endTimeProcesses.get(i - 1)));
        }
        for (Process processed : afterProcessing) {
            for (int i = 0; i < newProcesses.size(); i++) {
                List<Integer> lst = new ArrayList<>();
                if (processed.getName().equals(newProcesses.get(i).getName())) {
//                    System.out.println(startOfEachProcess.get(i) + " " + endTimeProcesses.get(i));
                    lst.add(startOfEachProcess.get(i));
                    lst.add(endTimeProcesses.get(i));
                    temp.add(lst);
                }
            }
            processed.setWaitingTime(calcWaitingTime(temp, processed.getArrivalTime(), this.start, firstProcess));
            processed.updateTurnaround(calcWaitingTime(temp, processed.getArrivalTime(), this.start, firstProcess));
            temp.clear();
            firstProcess = false;
        }
        this.avgWaitingTime = getAvgWaiting(afterProcessing);
        this.avgTurnAroundTime = getAvgTurnaround(afterProcessing);
    }
    public int calcWaitingTime(List<List<Integer>> lst, int arrival, int start, boolean firstProcess){
        int waiting = 0;
        if (firstProcess) waiting = 0;
        else waiting += lst.get(0).get(0) - arrival;
        for (int i = 1; i < lst.size(); i++){
            waiting += (lst.get(i).get(0) - lst.get(i - 1).get(1));
        }
        return waiting;
    }
    public double getAvgWaiting(List<Process> process){
        double sum = 0;
        for (Process p : process){
            sum += p.getWaitingTime();
        }
        return sum / process.size();
    }
    public double getAvgTurnaround(List<Process> process){
        double sum = 0;
        for (Process p : process){
            sum += p.getTurnaroundTime();
        }
        return sum / process.size();
    }
}