package model;

import java.util.ArrayList;

//Represents a list of job applications
public class JobApplicationList {
private ArrayList<JobApplication> newList;

//Effects: instatiates an empty list of job applications
public JobApplicationList(){
    //stub
}

//MODIFIES: this
//EFFECTS: add a new job application to the list 
public void addJob(JobApplication newJob){
    //stub 
}

//REQUIRES: job application to be removed is part of the list 
//MODIFIES: this 
//EFFECTS: remove an existing job application from the list
public void removeJob(JobApplication existingJob){
    //stub 
}

//EFFECTS: filter current list by status
public ArrayList<JobApplication> filterByStatus(JobStatus status){
    return new ArrayList<>(); //stub
}

// EFFECTS: filter current list by companyName
public ArrayList<JobApplication> filterByCompany(String company) {
    return new ArrayList<>(); // stub
}

}
