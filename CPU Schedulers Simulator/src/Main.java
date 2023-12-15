import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


          //Take the details...
         System.out.println("Enter number of processes : ");
         int numProcess = scanner.nextInt();

         System.out.println("Enter the Round Robin Time Quantum : ");
         int quantum = scanner.nextInt();

         System.out.println("Enter the context switching time: ");
         int contextSwitching = scanner.nextInt(); // don't know how to use it .

        List<Process> processes = new ArrayList<>();

         for (int i =0 ; i<numProcess;i++){
         System.out.println("================================================================");

         System.out.println("Enter details for Process " + (i + 1) + ":");

         System.out.print("Name: ");
         String name = scanner.next();

         System.out.print("Color: ");
         String color = scanner.next();

         System.out.print("Arrival Time: ");
         int arrivalTime = scanner.nextInt();

         System.out.print("Burst Time: ");
         int burstTime = scanner.nextInt();

         System.out.print("Priority: ");
         int priority = scanner.nextInt();

         processes.add(new Process(name, color, arrivalTime, burstTime, priority, 0));
         }

         //----------------------------------------------------------------

          Scheduler  scheduler1 = new SJF(processes, contextSwitching);
          scheduler1.run();
          scheduler1.output();
         //----------------------------------------------------------------

         Scheduler scheduler2 = new SRTF(processes);
         scheduler2.run();
         scheduler2.output();

        //----------------------------------------------------------------

        Scheduler scheduler3 = new PriorityScheduler(processes);
        scheduler3.run();
        scheduler3.output();

        //----------------------------------------------------------------
            // to be added the function of scheduling...
        Scheduler scheduler4 = new AGScheduling(processes, 4); // just replace 4 with the quantum time
        scheduler4.run();
        scheduler4.outputFinalAg();

        //----------------------------------------------------------------

    }
}