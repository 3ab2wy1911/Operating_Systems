package com.cli;

import java.util.Arrays;
import java.util.Scanner;
/*
Subject : Operating Systems Assignment 1 FCAI-CU
Callobrators: Mohamed Ahmed Abd El-Kawy , Omar Mohammed Fayek , Badr Mohamed Ragab , Ahmed Gehad Ahmed. NB : Fe Asmaa Ana Ma2lfha 3adloha we b3d kda ams7o el comment da.
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
        else {
            return false;
        }
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
    static Scanner scanner = new Scanner (System.in);
    static String input;


//----------------------------------------------------------------------------------------------------------------------
public String pwd(){
    return System.getProperty("user.dir");
}
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    // This method will choose the suitable command method to be called
    public void chooseCommandAction(){

        parser = new Parser();

        while (true) {
            System.out.print(">");
            input = scanner.nextLine();

            if (parser.parse(input)) {
                if (parser.getCommandName().equals("pwd")) {
                    System.out.println(pwd());
                } else if (parser.getCommandName().equals("exit")) {
                    System.exit(0);
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
