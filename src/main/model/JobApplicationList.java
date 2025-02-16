package model;

import java.util.ArrayList;

//Represents a list of job applications
public class JobApplicationList {
private ArrayList<JobApplication> newList;

//Effects: instatiates an empty list of job applications
public JobApplicationList(){
    newList = new ArrayList<>();
}

//MODIFIES: this
//EFFECTS: add a new job application to the end of the list  
public void addJob(JobApplication newJob){
    newList.add(newJob);
}

//REQUIRES: job application to be removed is part of the list 
//MODIFIES: this 
//EFFECTS: remove an existing job application from the list
public void removeJob(JobApplication existingJob){
    newList.remove(existingJob);
}

//EFFECTS: filter current list by status
public ArrayList<JobApplication> filterByStatus(JobStatus status){
    ArrayList<JobApplication> filteredList = new ArrayList<>();

    for(JobApplication nextJob:newList){
        if (nextJob.getStatus() == status){
            filteredList.add(nextJob);
        }
        }
        return filteredList;
    }


// EFFECTS: filter current list by companyName
public ArrayList<JobApplication> filterByCompany(String company) {
    ArrayList<JobApplication> filteredList = new ArrayList<>();

    for (JobApplication nextJob : newList) {
        if (nextJob.getCompanyName().equals(company)) {
            filteredList.add(nextJob);
        }
    }
    return filteredList;
}

//EFFECTS: return this job application list 
public ArrayList<JobApplication> getList(){
    return newList; 
}

}
