package model;

import java.io.File;
import java.time.LocalDate;

import org.json.JSONObject;

import persistence.Writable;

//Represents a job application with company name, job title, applied date, 
//application status, uploaded resume, uploaded cover letter (optional), 
//job posting url and notes (optional)
public class JobApplication implements Writable {
    private String companyName;
    private String jobTitle;
    private LocalDate appliedDate;
    private JobStatus status;
    private File resume;
    private File coverLetter;
    private String postingURL;
    private String notes;

    // REQUIRES: companyName, jobTitle, appliedDate, resume and postingURL are not
    // empty
    // EFFECTS: instantiate a new job application with given company name, job
    // title, applied date, uploaded resume and job posting url;
    // with coverLetter and notes set to empty and job status set to Applied
    public JobApplication(String companyName, String jobTitle, LocalDate appliedDate, File resume, String postingURL) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.appliedDate = appliedDate;
        this.resume = resume;
        this.postingURL = postingURL;
        this.coverLetter = null;
        this.notes = "";
        this.status = JobStatus.Applied;
    }

    // MODIFIES: this
    // EFFECTS: update companyName to given input
    public void setCompanyName(String newName) {
        this.companyName = newName;
    }

    // MODIFIES: this
    // EFFECTS: update jobTitle to given input
    public void setJobTitle(String newTitle) {
        this.jobTitle = newTitle;
    }

    // MODIFIES: this
    // EFFECTS: update appliedDate to given input
    public void setAppliedDate(LocalDate newDate) {
        this.appliedDate = newDate;
    }

    // MODIFIES: this
    // EFFECTS: update status to given input
    public void setStatus(JobStatus newStatus) {
        this.status = newStatus;
    }

    // MODIFIES: this
    // EFFECTS: update resume to given input
    public void setResume(File newResume) {
        this.resume = newResume;
    }

    // MODIFIES: this
    // EFFECTS: update coverLetter to given input
    public void setCoverLetter(File newCoverLetter) {
        this.coverLetter = newCoverLetter;
    }

    // MODIFIES: this
    // EFFECTS: update postingURL to given input
    public void setPostingURL(String newURL) {
        this.postingURL = newURL;
    }

    // MODIFIES: this
    // EFFECTS: update notes to given input
    public void setNotes(String newNotes) {
        this.notes = newNotes;
    }

    // EFFECTS: get this job application's companyName
    public String getCompanyName() {
        return this.companyName;
    }

    // EFFECTS: get this job application's jobTitle
    public String getJobTitle() {
        return jobTitle;
    }

    // EFFECTS: get this job application's appliedDate
    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    // EFFECTS: get this job application's status
    public JobStatus getStatus() {
        return status;
    }

    // EFFECTS: get this job application's resume
    public File getResume() {
        return resume;
    }

    // EFFECTS: get this job application's coverLetter
    public File getCoverLetter() {
        return coverLetter;
    }

    // EFFECTS: get this job application's postingURL
    public String getPostingURL() {
        return postingURL;
    }

    // EFFECTS: get this job application's notes
    public String getNotes() {
        return notes;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("companyName", companyName);
        json.put("jobTitle", jobTitle);
        json.put("appliedDate", appliedDate.toString());
        json.put("resume", resume.getPath());
        json.put("postingURL", postingURL);
        json.put("coverLetter", coverLetter != null ? coverLetter.getPath() : JSONObject.NULL);
        json.put("notes", notes);
        json.put("status", status.toString());
        return json;
    }


    @Override
    public String toString() {
        return "<html><b>Company:</b> " + companyName
                + "<br/><b>Job Title:</b> " + jobTitle
                + "<br/><b>Applied Date:</b> " + appliedDate
                + "<br/><b>Status:</b> " + status
                + "<br/><b>Resume:</b> " + resume
                + "<br/><b>Cover Letter:</b> " + (coverLetter != null ? coverLetter.getPath() : "None")
                + "<br/><b>Posting URL:</b> " + postingURL
                + "<br/><b>Notes:</b> " + (notes.isEmpty() ? "None" : notes)
                + "</html>";
    }

}
