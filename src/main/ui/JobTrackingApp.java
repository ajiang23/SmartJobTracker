package ui;

import java.util.Scanner;

import model.JobApplicationList;

//Job application tracking app 
public class JobTrackingApp {
    private Scanner input;
    private JobApplicationList newList; 

//EFFECTS: instantiates and runs the app
public JobTrackingApp(){
    input = new Scanner(System.in);
    newList = new JobApplicationList();
    runApp();
}

//EFFECTS: runs the application loop, processes user's input until user chooses to exit
private void runApp(){
    //stub 
}

// EFFECTS: displays menu of options
public void displayMenu() {
    // stub
}

//EFFECTS: processes user's command
public void handleUserCommand(){
    //stub
}

//MODIFIES: newList
//EFFECTS: adds a job application to this list 
public void addJob(){
    //stub 
}

//REQUIRES: job application must exist in this list 
//MODIFIES: newList
//EFFECTS: removes a job application from this list 
public void removeJob(){
    //stub
}

//EFFECTS: displays all exising job applications in this list
public void viewJobList(){
    //stub
}

//REQUIRES: job applicaiton must exist in this list 
//MODIFIES: a job application's status 
//EFFECTS: updates a job application's status 
public void updateStatus() {
    //stub
}

//EFFECTS: filters job applications by status and displays filtered results
public void filterJobByStatus(){
    //stub
}

// EFFECTS: filters job applications by company name and displays filtered results 
public void filterJobByCompany() {
    //stub
}

}
