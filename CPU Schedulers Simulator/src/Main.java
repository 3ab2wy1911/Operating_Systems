import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();
//
//        // Take the details...
//        System.out.println("Enter number of processes : ");
//        int numProcess = scanner.nextInt();
//
//        System.out.println("Enter the Round Robin Time Quantum : ");
//        int quantum = scanner.nextInt();
//
//        System.out.println("Enter the context switching time: ");
//        int contextSwitching = scanner.nextInt(); // don't know how to use it .


//        for (int i =0 ; i<numProcess;i++){
//            System.out.println("================================================================");
//
//            System.out.println("Enter details for Process " + (i + 1) + ":");
//
//            System.out.print("Name: ");
//            String name = scanner.next();
//
//            System.out.print("Color: ");
//            String color = scanner.next();
//
//            System.out.print("Arrival Time: ");
//            int arrivalTime = scanner.nextInt();
//
//            System.out.print("Burst Time: ");
//            int burstTime = scanner.nextInt();
//
//            System.out.print("Priority: ");
//            int priority = scanner.nextInt();
//


            processes.add(new Process("p1", "red",    0, 17, 0, 20));
            processes.add(new Process("p2", "red",    3, 6, 0, 17));
            processes.add(new Process("p3", "red",    4, 10, 0, 16));
            processes.add(new Process("p4", "red",    29, 4, 0, 43));
            Scheduler scheduler1 = new AGScheduling(processes, 4);
            scheduler1.run();
        }

        //----------------------------------------------------------------



}
/*
*   Process (P1) running ...
    P1 has interrupted by P2
    Process (P2) running ...
    P2 has interrupted by P3
    Process (P3) running ...
    P3 spent all its quantum time
    Process (P1) running ...
    P1 has interrupted by P3
    Process (P3) running ...
    P3 spent all its quantum time
    Process (P2) running ...
    P2 has interrupted by P3
    Process (P3) running ...
    P3 finished
    Process (P1) running ...
    P1 has interrupted by P2
    Process (P2) running ...
    P2 finished
    Process (P1) running ...
    P1 spent all its quantum time
    Process (P1) running ...
    P1 finished
    Process (P4) running ...
    P4 spent all its quantum time
    Process (P4) running ...
    P4 finished*/