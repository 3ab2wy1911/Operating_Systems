package com.cli;

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
        // TODO: to be implemented;
        return false;
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
    // TODO: Implement the commands;
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    // This method will choose the suitable command method to be called
    public void chooseCommandAction(){

        parser = new Parser ();
        System.out.println("Enter command : ");
        input = scanner.nextLine();
        while (parser.parse(input) /*TODO: we will add other conditions here.*/){
            System.out.println("Error: Command not found or invalid parameters are entered !"); // Error msg
        }

        //________________________________________________________________

        // Getting the command name and its parameters.
            String commandName = parser.getCommandName();
            String [] args = parser.getArgs();
    }

//----------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args){
        // Call the function that will manage the Interpreter.
        Terminal terminal = new Terminal ();
        terminal.chooseCommandAction();
    }
}
//======================================================================================================================