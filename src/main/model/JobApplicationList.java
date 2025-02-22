package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

//Represents a list of job applications
public class JobApplicationList implements Writable {
    private ArrayList<JobApplication> newList;

    // Effects: instatiates an empty list of job applications
    public JobApplicationList() {
        newList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a new job application to the end of the list
    public void addJob(JobApplication newJob) {
        newList.add(newJob);
    }

    // REQUIRES: job application to be removed is part of the list
    // MODIFIES: this
    // EFFECTS: remove an existing job application from the list
    public void removeJob(JobApplication existingJob) {
        newList.remove(existingJob);
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

    // EFFECTS: return this job application list
    public ArrayList<JobApplication> getList() {
        return newList;
    }

    @Override
    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("job applications", jobAppToJson());
        return json;
    }

    //EFFECTS: returns job applications in this job application list as a JSON array
    private JSONArray jobAppToJson(){
        JSONArray jsonArray = new JSONArray();
        for (JobApplication nextJobApp : newList){
            jsonArray.put(nextJobApp.toJson());
        }

        return jsonArray;
    }

}
