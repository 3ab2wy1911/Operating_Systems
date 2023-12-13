import java.util.*;

public class AGScheduling extends Scheduler {
    private int quantumTime;

    private final Queue<Process> readyQueue = new LinkedList<>();
    private final List<Process> currentProcesses = new LinkedList<>();
    private final List<Process> dieList = new LinkedList<>();

    public AGScheduling(List<Process> processes, int quantumTime) {
        super(processes);
        this.quantumTime = quantumTime;
        for(int i = 0; i< processes.size();i++)
        {
            processes.get(i).setCurrentQuantumTime(quantumTime);
        }
    }

    @Override
    public void run() {

        int runningTime = 0;
        Process currentProcess;

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int time = processes.get(0).getArrivalTime();

        addToCurrentProcessesBasedOnTime(time);

        currentProcess = leastAGFactor();

        int currentQuantumTime = currentProcess.getCurrentQuantumTime();

        time+=nonPreemptiveAG(currentProcess);

        currentQuantumTime-=time;
        runningTime += time;

        currentProcess.setBurstTime(currentProcess.getBurstTime() - runningTime);

        addToCurrentProcessesBasedOnTime(time);

        while (processes.size() > 0) {

            if(currentProcess != leastAGFactor() || currentProcess == null)
            {
                if(currentProcess != null){
//                    System.out.println(currentProcess.getName() + " running\n" + "intrupted By " + leastAGFactor().getName());
                    updateQuantumAndBurst(currentProcess, currentQuantumTime);
                    newProcesses.add(currentProcess);

                    readyQueue.add(currentProcess);
                }

                currentProcess = leastAGFactor();

                currentQuantumTime = currentProcess.getCurrentQuantumTime();

                time+=nonPreemptiveAG(currentProcess);

                addToCurrentProcessesBasedOnTime(time);

                runningTime=nonPreemptiveAG(currentProcess);

                currentProcess.setBurstTime(currentProcess.getBurstTime() - runningTime);

                currentQuantumTime -=runningTime;
            }

            else if(currentQuantumTime == 0)
            {
//                System.out.println(currentProcess.getName()+" running");
//                System.out.println("Finish his quantum time " + currentProcess.getName());

                updateQuantumAndBurst(currentProcess, (int) (Math.ceil(0.1 * meanOfProcesses())));

                newProcesses.add(currentProcess);


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

                currentQuantumTime-=runningTime;
            }

            else if(currentProcess.getBurstTime() <= 0)
            {
//                System.out.println(currentProcess.getName()+" running");
//                System.out.println("burstTime Finished " + currentProcess.getName());
                dieList.add(currentProcess);


                if(newProcesses.getLast() != currentProcess) {
                    newProcesses.add(currentProcess);
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
             else {
                time += 1;

                runningTime+=1;

                currentQuantumTime--;
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
                addToCurrentProcessesBasedOnTime(time);

            }
        }
        for (int i =0 ; i < newProcesses.size();i++)
        {
            System.out.println(newProcesses.get(i).getName());
        }
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

}