package com.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/*
Subject : Operating Systems Assignment 1 FCAI-CU
Collaborators: Mohamed Ahmed Abd El-Kawy , Omar Mohamed Fayek , Badr Mohamed Ragab , Ahmed Gehad Ahmed. NB : Fe Asmaa Ana Ma2lfha 3adloha we b3d kda ams7o el comment da.
Beginning Date : 28 - 10 - 2023
Ending Date :
*/

class Parser {
    private String commandName;
    private String [] args;
    private String input ;
    //----------------------------------------------------------------------------------------------------------------------
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
    public boolean parse(String input){
        this.input = input;
        // Removing the spaces from the string and then dividing it into command and args.
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
//        System.out.println(commandName);
//        if (args != null)
//        for ( String arg : args)
//            System.out.println(arg);
//----------------------------------------------------------------------------------------------------------------------
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

        //----------------------------------------------------------------

        else if (commandName.equals("ls") &&( args == null || Objects.equals(args[0], "-r"))){
            return true;
        }
        else if (commandName.equals("touch") && args != null){
            return true;
        }
        else if (commandName.equals("wc") && args != null){
            return true;
        }
        else if (commandName.equals("help") && args == null){
            return true;
        }
        else if (commandName.equals("echo") && args!= null){
            return true;
        }
        //----------------------------------------------------------------
        // Will Implement the rest here...
        else return false;  // better to type return and the last condition...
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
    public void setArgs(String[]args){
        this.args = args;
    }
//----------------------------------------------------------------------------------------------------------------------
    public String getInput() {
    return input;
    }
    //----------------------------------------------------------------------------------------------------------------------

}

//======================================================================================================================

public class Terminal {
    private Parser parser;
    private ArrayList<String> History = new ArrayList<>(); // Every correct command will be added to the history
    private static Scanner scanner = new Scanner (System.in);
    private static String input;
    private static Path currentPath = Paths.get(System.getProperty("user.dir"));


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
                this.parser.setArgs(null);
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
        this.parser.setArgs(null);

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
//----------------------------------------------------------------------------------------------------------------------

    public List<String> ls (){
        File[] listOfFiles = currentPath.toFile().listFiles();  // get the list of files in the current directory.
        List<String> filesName = new ArrayList<>(); // the list of files' names.

        if (listOfFiles == null)
        {
            System.out.println("No files in directory");
            return filesName;
        }

        if (parser.getArgs() != null) { // if the user wants ls -r .
            Arrays.sort(listOfFiles, Collections.reverseOrder());   // reverse the list.
        }

        else Arrays.sort(listOfFiles);    // sort the list.

        for (File listOfFile : Objects.requireNonNull(listOfFiles)) {
            if (listOfFile.isFile()) {
                filesName.add((listOfFile.getName()));  // add the file name to the list.
            }
        }
        return filesName;
    }

//----------------------------------------------------------------------------------------------------------------------

    public String wc (){

        try {
            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;

            FileReader fileReader = new FileReader(parser.getArgs()[0]);    // get the file name.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lineCount++; // Increment line count for each line in the file.

                String[] words = line.split("\\s+"); // Split the line into words using spaces.
                wordCount += words.length; // Increment word count for each word in the file.

                charCount += line.length(); // Increment character count for the line (including spaces).
            }

            bufferedReader.close();
            return (lineCount + " " + wordCount + " " + charCount + " " + Arrays.toString(parser.getArgs()));

        } catch (IOException e) {
            return "Invalid command";
        }
    }
//----------------------------------------------------------------------------------------------------------------------
public void touch() throws IOException {

    String path = String.join(File.separator, parser.getArgs()); // Joining the args with the file separator.

    File file = new File(path);

    if (file.exists()) {    // Check if the file exists.
        System.out.println("File already exists.");
        return; // Exit method if the file already exists.
    }

    // check if the path is correct or no.
    File parentDirectory = file.getParentFile();
    if (parentDirectory != null && !parentDirectory.exists()) {
        System.out.println("The system cannot find this path.");
        return; // Exit method if the path is not found
    }

    // Try to create the file.
    if (file.createNewFile()) {
        System.out.println("File created successfully.");
    }
    else {
        System.out.println("Failed to create the file.");
    }
}
//----------------------------------------------------------------------------------------------------------------------
    public String echo (){    // just print the input.
        String output = Arrays.toString(parser.getArgs());
        return output.replaceAll("[\\[\\]]", "");
    }

//----------------------------------------------------------------------------------------------------------------------

    public String help (){
        return """
                echo    Takes 1 argument and prints it.
                pwd     Takes no arguments and prints the current path.
                cd      Change the directory.
                ls      Lists the contents of the current directory sorted alphabetically.
                ls -r   Lists the contents of the current directory in reverse order.
                mkdir   Creates a new directory.
                rmdir   Remove a directory.
                touch   Create a new file.
                cp      Copy from a file to a file.
                cp -r   Copy from a directory to a directory.
                rm      Removes a file.
                cat     Takes 1 argument and prints the file’s content or takes 2 arguments and concatenates the content of the 2 files and prints it.
                wc      Used for counting processes of the file.
                history Displays an enumerated list with the commands you’ve used in the past.
                exit    Exit the program.
                """;
    }
//----------------------------------------------------------------------------------------------------------------------


    // This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {

        System.out.println(currentPath);
        while (true) {
            parser = new Parser();
            System.out.print(">");
            input = scanner.nextLine();
            if (parser.parse(input)) {
                History.add(parser.getInput());
                switch (parser.getCommandName()) {
                    case "help" -> System.out.println(help());
                    case "echo" -> System.out.println(echo());
                    case "pwd" -> System.out.println(pwd());
                    case "cd" -> this.cd(parser.getArgs());
                    case "ls" -> {
                        for (String filename : ls()){
                            System.out.println(filename);
                        }
                    }
                    case "touch" -> touch();
                    case "cat" -> {
                        if (parser.getArgs().length == 1) {
                            cat(parser.getArgs()[0]);
                            History.add("cat " + parser.getArgs()[0]);
                        } else if (parser.getArgs().length == 2) {
                            cat(parser.getArgs()[0], parser.getArgs()[1]);
                        } else System.out.println("More than 2 parameters!");
                    }
                    case "wc" -> System.out.println(wc());
                    case "history" -> {
                        int counter = 1;
                        for (String command : History) {
                            System.out.println(counter++ + " " + command);
                        }
                    }
                    case "exit" -> System.exit(0);
                }
            }
            else System.out.println("Invalid command");
        }

        //________________________________________________________________

    }

//----------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws IOException {
//  Call the function that will manage the Interpreter.
        Terminal terminal = new Terminal ();
        terminal.chooseCommandAction();

    }
}
//======================================================================================================================
