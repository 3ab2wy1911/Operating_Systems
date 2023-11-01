package com.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/*
Subject : Operating Systems Assignment 1 FCAI-CU
Callobrators: Mohamed Ahmed Abd El-Kawy , Omar Mohamed Fayek , Badr Mohamed Ragab , Ahmed Gehad Ahmed. NB : Fe Asmaa Ana Ma2lfha 3adloha we b3d kda ams7o el comment da.
Beginning Date : 28 - 10 - 2023
Ending Date :
*/

class Parser {
    String commandName;
    String [] args;
    //----------------------------------------------------------------------------------------------------------------------
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
    public boolean parse(String input){
        String[] subInput = input.split("\\s");
        this.commandName = subInput[0];
        if(subInput.length > 1)
        {
            args=new String[subInput.length-1];
            for(int i = 1; i< subInput.length;i++)
            {
                args[i-1] = subInput[i];
            }
        }
        if(commandName.equals("pwd") && args == null)
        {
            return true;
        }
        else if(commandName.equals("exit") && args == null)
        {
            return true;
        }
        else if(commandName.equals("cd"))
        {
            if(args == null) {
                return true;
            }
            else if(args.length > 1)
            {
                return false;
            }
            return true;
        }
        else if(args == null && commandName.equals("history")){
            return true;
        }
        else return false;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public String getCommandName(){
        return commandName;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public String[] getArgs(){
        return args;
    }
//----------------------------------------------------------------------------------------------------------------------

}

//======================================================================================================================

public class Terminal {
    Parser parser;
    ArrayList<String> History = new ArrayList<>(); // Every correct command will be added to the history
    static Scanner scanner = new Scanner (System.in);
    static String input;
    static Path currentPath = Paths.get(System.getProperty("user.dir"));


    //----------------------------------------------------------------------------------------------------------------------
    public String pwd(){
        return System.getProperty("user.dir");
    }
    public void cd(String[] args){
        if(args==null)
        {
            currentPath = Path.of(System.getProperty("user.home"));
        }

        else {
            if(args[0].equals(".."))
            {
                if(currentPath.getParent() != null){
                    currentPath = currentPath.getParent();
                }
                this.parser.args = null;
                return;
            }
            Path path = Paths.get(args[0]);

            if(!path.isAbsolute())
            {
                path = Paths.get(currentPath.toString(), path.toString()).normalize();
            }

            if(Files.exists(path)){
                currentPath = path;
            }
            else {
                System.out.println("Invalid directory");
            }
        }
        this.parser.args = null;

    }
    //----------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    // This method will choose the suitable command method to be called
    public void chooseCommandAction(){

        parser = new Parser();

        while (true) {
            System.out.print(currentPath+">");
            input = scanner.nextLine();

            if (parser.parse(input)) {
                if (parser.getCommandName().equals("pwd")) {
                    System.out.println(pwd());
                    History.add("pwd"); // add that command to the history
                } else if (parser.getCommandName().equals("exit")) {
                    System.exit(0);
                    // add that command to the history
                } else if (parser.getCommandName().equals("history")){
                    int counter = 1;
                    for (String command : History){
                        System.out.println(counter++ + " " + command);
                    }
                }
                else if(parser.getCommandName().equals("cd"))
                {
                    this.cd(parser.getArgs());
                }
            }
            else {
                System.out.println("Invalid command");
            }
        }

        //________________________________________________________________

//        // Getting the command name and its parameters.
//        String commandName = parser.getCommandName();
//        String [] args = parser.getArgs();
    }

//----------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args){
//        // Call the function that will manage the Interpreter.
        Terminal terminal = new Terminal ();
        terminal.chooseCommandAction();
//        terminal.chooseCommandAction();

    }
}
//======================================================================================================================
