package com.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/*
Subject : Operating Systems Assignment 1 FCAI-CU
Collaborators: Mohamed Ahmed Abd El-Kawy , Omar Mohamed Fayek , Badr Mohamed Ragab , Ahmed Gehad Ahmed. NB : Fe Asmaa Ana Ma2lfha 3adloha we b3d kda ams7o el comment da.
Beginning Date : 28 - 10 - 2023
Ending Date : 02 - 11 -2023
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

//----------------------------------------------------------------------------------------------------------------------

        // Checking the input of the user.
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
            return args != null;
        }
        else if(args == null && commandName.equals("history")){
            return true;
        }
        else if (commandName.equals("cat") && args != null){
            return true;
        }
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
        else if(commandName.equals("cp") )
        {
            return args != null;
        }
        else if (commandName.equals("mkdir") && args != null){
            return true;
        }
        else if (commandName.equals("rmdir")){
            return args != null;
        }
        else return commandName.equals("rm");
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
    private com.cli.Parser parser;
    private final ArrayList<String> History = new ArrayList<>(); // Every correct command will be added to the history
    private static final Scanner scanner = new Scanner (System.in);
    private static String input;
    private static Path currentPath = Paths.get(System.getProperty("user.dir"));


//----------------------------------------------------------------------------------------------------------------------

    public String pwd(){
        return currentPath.toString();
    }

//----------------------------------------------------------------------------------------------------------------------

    public void cd(String[] args){
        if(args==null || args.length ==0)
        {
            currentPath = Paths.get(System.getProperty("user.home"));
            return;
        }

        else {
            if(args[0].equals(".."))
            {
                if(currentPath.getParent() != null){
                    currentPath = currentPath.getParent();
                }
                return;
            }
            Path path = Paths.get(String.join(File.separator, parser.getArgs()));

            if(!path.isAbsolute())
            {
                path = Paths.get(currentPath.toString(), path.toString()).normalize();
            }

            try{
                if(Files.exists(path)){
                    currentPath = path;
                }
                else {
                    System.out.println("Invalid directory");
                }
            } catch (InvalidPathException e) {
                System.out.println("Invalid directory");
            }
        }
        this.parser.setArgs(null);

    }

    //----------------------------------------------------------------------------------------------------------------------
    public static Path ensureAbsolutePaths(Path filePath, String args) {
        if (!filePath.isAbsolute()) {
            filePath = Paths.get(currentPath.toString(), args);
        }
        return filePath;
    }

    //----------------------------------------------------------------------------------------------------------------------
    public void cp(String[] args) throws IOException {
        Path file1 =  Path.of(args[0]);
        Path file2 = Path.of(args[1]);
        file1 = ensureAbsolutePaths(file1, args[0]);
        file2 = ensureAbsolutePaths(file2, args[1]);
        try{
            if(Files.exists(file1)) {
                File file = new File(file2.toString());
                file.createNewFile();
                if(Files.exists(file2))
                {
                    Files.copy(file1, file2, REPLACE_EXISTING);
                }
                else {
                    System.out.println("Enter a Valid Destination File Name");
                }

            }
            else {
                System.out.println("Enter a Valid Source File Name");
            }
        }
        catch (IOException e)
        {
            System.out.println("Error in Copying File " + e);
        }
        this.parser.setArgs(null);
    }

//----------------------------------------------------------------------------------------------------------------------

    public void cp_r(String[] args) {
        Path folder1 =  Path.of(args[1]);
        Path folder2 = Path.of(args[2]);
        folder1 = ensureAbsolutePaths(folder1, args[1]);
        folder2 = ensureAbsolutePaths(folder2, args[2]);
        try {
            if(Files.exists(folder2))
            {
                Path FileName = folder1.getParent().relativize(folder1);

                File newFile = new File(folder2.resolve(String.valueOf(FileName)).toString());
                newFile.mkdir();

                folder2 = folder2.resolve(String.valueOf(FileName));

            }

            Path finalFolder = folder2;
            Path finalFolder1 = folder1;
            Files.walk(folder1).forEach(s->
                    {
                        try {

                            Files.copy(s, finalFolder.resolve(finalFolder1.relativize(s)), REPLACE_EXISTING);

                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }

            );
        }catch (IOException e) {
            System.out.println(e);
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
            if (listOfFile.isFile() || listOfFile.isDirectory()) {
                filesName.add((listOfFile.getName()));  // add the file or the directory name to the list.
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

            FileReader fileReader = new FileReader(String.join(" ", parser.getArgs()));    // get the file name.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lineCount++; // Increment line count for each line in the file.

                String[] words = line.split("\\s+"); // Split the line into words using spaces.
                wordCount += words.length; // Increment word count for each word in the file.

                charCount += line.length(); // Increment character count for the line (including spaces).
            }

            bufferedReader.close();
            return (lineCount + " " + wordCount + " " + charCount + " " + String.join(" ", parser.getArgs()));

        } catch (IOException e) {
            return "Invalid command";
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void touch() throws IOException {

        String path = String.join(File.separator, parser.getArgs()); // Joining the args with the file separator.

        File file = new File(path);

        try {
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
        }catch (IOException e) {
            System.out.println("Directory not found.");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------
    public String echo (){    // just print the input.
        String output = Arrays.toString(parser.getArgs());
        return output.replaceAll("[\\[\\]]", "");
    }

//----------------------------------------------------------------------------------------------------------------------

    public void makeDir(String[] arguments) throws IOException {
        // windows the absolute path starts with [partition letter][:][\]
        // linux the absolute path starts with '/'
        boolean check = false;
        Path newDirectory;
        for (String directory : arguments){
            char letter = directory.charAt(0);
            // check if it absolute path
            if (Character.isLetter(letter) && directory.charAt(1) == ':' && directory.charAt(2) == '\\'){
                check = Paths.get(directory).isAbsolute();
                if (check){
                    newDirectory = Paths.get(directory);
                    try{
                        Files.createDirectory(newDirectory);
                        break;
                    }
                    catch(IOException exception){
                        System.out.println("Invalid Directory");
                        return;
                    }
                }
            }
            else {
                try {
                    String validation = "\\\\/:*?\"<>|";
                    if (!directory.matches(".*[" + validation + "].*")){
                        newDirectory = currentPath.resolve(directory);
                        if (Files.exists(newDirectory)){
                            throw new Exception(directory + " already exists");
                        }
                        else Files.createDirectory(newDirectory);
                    }
                    else throw new Exception("Not a valid name");
                }
                catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

            }
        }
    }

    //----------------------------------------------------------------------------------------------------------------------
    public void rmdir(){
        String path = String.join(" ", parser.getArgs()); // Joining the args with the file separator.
        File file = new File(path);

        if (path.equals("*"))
        {
            File current = new File(".");
            File[] files = current.listFiles();

            if (files != null){
                for (File f : files){
                    if (f.isDirectory() && Objects.requireNonNull(f.list()).length == 0){
                        if (f.delete()){
                            System.out.println(f.getName() + " deleted successfully");
                        }
                    }
                }
            }
        }
        else {
            if (!file.exists() || !file.isDirectory()){
                System.out.println("Directory does not exist");
                return;
            }
            if (file.delete()){
                System.out.println(file.getName() + " deleted successfully");
            }
            else {
                System.out.println("Failed to delete directory");
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    public void remove(String filename) throws Exception{
        try{
            Path p = currentPath.resolve(filename);
            if (Files.exists(p)){
                Files.delete(p);
            }
            else throw new Exception();
        }
        catch (Exception exception){
            System.out.println("No such file or directory");
        }
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
    public void chooseCommandAction() throws Exception {


        while (true) {
            System.out.print(currentPath);
            parser = new com.cli.Parser();
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
                    case "mkdir" -> makeDir(parser.getArgs());
                    case "rmdir" -> rmdir();
                    case "touch" -> touch();
                    case "cp" -> {
                        if (!parser.getArgs()[0].equals("-r")) {
                            this.cp(parser.getArgs());
                        }
                        else if (parser.getArgs()[0].equals("-r")){
                            this.cp_r(parser.getArgs());
                        }
                    }
                    case "rm" -> {
                        if (parser.getArgs().length == 1){
                            remove(parser.getArgs()[0]);
                        }
                    }
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

    public static void main(String[] args) throws Exception {
//  Call the function that will manage the Interpreter.
        com.cli.Terminal terminal = new com.cli.Terminal();
        terminal.chooseCommandAction();

    }
}
//======================================================================================================================
