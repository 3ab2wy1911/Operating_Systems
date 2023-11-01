package com.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
            else return args.length <= 1;
        }
        else if(args == null && commandName.equals("history")){
            return true;
        }
        else if (commandName.equals("cat") && args != null){
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
    public void cat(String filename) throws IOException {
        Path fullPath = currentPath.resolve(filename);
        if (Files.exists(fullPath)){
            try{
                StringBuilder content = new StringBuilder();
                File f = new File(filename);
                Scanner sc = new Scanner(f);
                while(sc.hasNextLine()){
                    content.append(sc.nextLine()).append('\n');
                }
                System.out.println(content);
            }
            catch(IOException exception) {
                System.out.println("Error Opening the file");
            }
        }
        else {
            System.out.println("No Such file in directory");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void cat(String filename1, String filename2) throws IOException{
        Path fullPath1 = currentPath.resolve(filename1); // store the path of the first file
        Path fullPath2 = currentPath.resolve(filename2); // store the path of the second file
        if (Files.exists(fullPath1) && Files.exists(fullPath2)){
            try {
                File f = new File(filename1);
                Scanner sc = new Scanner(f);
                while(sc.hasNextLine()){
                    System.out.println(sc.nextLine());
                }
                f = new File(filename2);
                sc = new Scanner(f);
                while(sc.hasNextLine()){
                    System.out.println(sc.nextLine());
                }
            }
            catch(IOException exception){
                System.out.println("Error Opening File!");
            }
        }
        else {
            System.out.println("No Such file in directory");
        }
    }
    // This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {

        while (true) {
            parser = new Parser();
            System.out.print(currentPath+"\n>");
            input = scanner.nextLine();

            if (parser.parse(input)) {
                if (parser.getCommandName().equals("pwd")) {
                    System.out.println(pwd());
                    History.add("pwd"); // add that command to the history
                }
                else if (parser.getCommandName().equals("exit")) {
                    System.exit(0);
                    // add that command to the history
                }
                else if (parser.getCommandName().equals("history")){
                    int counter = 1;
                    for (String command : History){
                        System.out.println(counter++ + " " + command);
                    }
                }
                else if(parser.getCommandName().equals("cd"))
                {
                    this.cd(parser.getArgs());
                }
                else if (parser.getCommandName().equals("cat")){
                    if (parser.getArgs().length == 1) {
                        cat(parser.getArgs()[0]);
                        History.add("cat "+parser.getArgs()[0]);
                    }
                    else if (parser.getArgs().length == 2) {
                        cat(parser.getArgs()[0], parser.getArgs()[1]);
                        History.add("cat "+parser.getArgs()[1] + ' ' + parser.getArgs()[1]);
                    }
                    else System.out.println("More than 2 parameters!");
                }
                else System.out.println("Invalid command");
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

    public static void main(String[] args) throws IOException {
//        // Call the function that will manage the Interpreter.
        Terminal terminal = new Terminal ();
        terminal.chooseCommandAction();
//        terminal.chooseCommandAction();

    }
}
//======================================================================================================================
