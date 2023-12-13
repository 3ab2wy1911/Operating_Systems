import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Scanner scanner = new Scanner(System.in);
        //
        // // Take the details...
        // System.out.println("Enter number of processes : ");
        // int numProcess = scanner.nextInt();
        //
        // System.out.println("Enter the Round Robin Time Quantum : ");
        // int quantum = scanner.nextInt();
        //
        // System.out.println("Enter the context switching time: ");
        // int contextSwitching = scanner.nextInt(); // don't know how to use it .

        List<Process> processes = new ArrayList<>();

        // for (int i =0 ; i<numProcess;i++){
        // System.out.println("================================================================");
        //
        // System.out.println("Enter details for Process " + (i + 1) + ":");
        //
        // System.out.print("Name: ");
        // String name = scanner.next();
        //
        // System.out.print("Color: ");
        // String color = scanner.next();
        //
        // System.out.print("Arrival Time: ");
        // int arrivalTime = scanner.nextInt();
        //
        // System.out.print("Burst Time: ");
        // int burstTime = scanner.nextInt();
        //
        // System.out.print("Priority: ");
        // int priority = scanner.nextInt();
        //
        // processes.add(new Process(name, color, arrivalTime, burstTime, priority));
        // }
        //
        // //----------------------------------------------------------------
        //
        // // Scheduler scheduler1 = new SJF(processes);
        // // scheduler1.run();
        // // scheduler1.output();
        //
        // //----------------------------------------------------------------
        // Scheduler scheduler2 = new SRTF(processes);
        // scheduler2.run();
        // scheduler2.output();
        // processes.add(new Process("p1", "red", 0, 17, 0, 20));
        // processes.add(new Process("p2", "red", 3, 6, 0, 17));
        // processes.add(new Process("p3", "red", 4, 10, 0, 16));
        // processes.add(new Process("p4", "red", 29, 4, 0, 43));
        // Scheduler scheduler1 = new AGScheduling(processes, 4);
        // scheduler1.run();

        // Test Case for SRTF
        List<Process> processes2 = new ArrayList<>();
        processes2.add(new Process("P1", "red", 0, 8, 0, 0));
        processes2.add(new Process("P2", "blue", 1, 4, 0, 0));
        processes2.add(new Process("P3", "green", 2, 9, 0, 0));
        processes2.add(new Process("P4", "yellow", 3, 5, 0, 0));
        Scheduler scheduler2 = new SRTF(processes2);
        scheduler2.run();
        scheduler2.output();

    }
}