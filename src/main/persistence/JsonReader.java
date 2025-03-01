package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.json.*;

import java.io.File;

import model.JobApplication;
import model.JobApplicationList;
import model.JobStatus;

//Represents a reader that reads from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads job application list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public JobApplicationList read() throws IOException {
        String jsonString = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonString);
        return parseJobApplicationList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return Files.readString(Paths.get(source), StandardCharsets.UTF_8);
    }

    // EFFECTS: parses job application list from JSON object and returns
    // it
    private JobApplicationList parseJobApplicationList(JSONObject jsonObject) {
        JobApplicationList jl = new JobApplicationList();
        addJobApplications(jl, jsonObject);
        return jl;
    }

    // MODIFIES: jl
    // EFFECTS: parses job applications from JSON object and add them to newList
    private void addJobApplications(JobApplicationList jl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("job applications");
        for (Object nextJobApp : jsonArray) {
            JSONObject jobApp = (JSONObject) nextJobApp;
            addJobApplication(jl, jobApp);
        }

    }

    // MODIFIES: jl
    // EFFECTS: parses job application fron JSON object and adds it to job
    // application list
    private void addJobApplication(JobApplicationList jl, JSONObject jsonObject) {
        String companyName = jsonObject.getString("companyName");
        String jobTitle = jsonObject.getString("jobTitle");
        LocalDate appliedDate = LocalDate.parse(jsonObject.getString("appliedDate"));
        File resume = new File(jsonObject.getString("resume"));
        String postingURL = jsonObject.getString("postingURL");
        String coverLetterPath = jsonObject.optString("coverLetter", "");
        File coverLetter = coverLetterPath.isEmpty() ? null : new File(coverLetterPath);
        String notes = jsonObject.optString("notes", "");
        JobStatus status = JobStatus.valueOf(jsonObject.getString("status"));
        JobApplication jobApp = new JobApplication(companyName, jobTitle, appliedDate, resume, postingURL);
        jobApp.setCoverLetter(coverLetter);
        jobApp.setNotes(notes);
        jobApp.setStatus(status);
        jl.addJob(jobApp);
    }

}