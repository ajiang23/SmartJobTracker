package model;

import java.io.File;
import java.time.LocalDate;

//Represents a job application with company name, job title, applied date, 
//application status, uploaded resume, uploaded cover letter (optional), 
//cached job posting and notes (optional)
public class JobApplication {
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
    // title,
    // applied date, uploaded resume and cached job posting
    public JobApplication(String companyName, String jobTitle, LocalDate appliedDate, File resume, String postingURL) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update companyName to given input
    public void setCompanyName(String newName) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update jobTitle to given input
    public void setJobTitle(String newTitle) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update appliedDate to given input
    public void setAppliedDate(LocalDate newDate) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update status to given input
    public void setStatus(JobStatus newStatus) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update resume to given input
    public void setResume(File newResume) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update coverLetter to given input
    public void setCoverLetter(File newCoverLetter) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update postingURL to given input
    public void setPostingURL(String newURL) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: update notes to given input
    public void setNotes(String newNotes) {
        // stub
    }

    // EFFECTS: get this job application's companyName
    public void getCompanyName() {
        // stub
    }

    // EFFECTS: get this job application's jobTitle
    public void getJobTitle() {
        // stub
    }

    // EFFECTS: get this job application's appliedDate
    public void getAppliedDate() {
        // stub
    }

    // EFFECTS: get this job application's status
    public void getStatus() {
        // stub
    }

    // EFFECTS: get this job application's resume
    public void getResume() {
        // stub
    }

    // EFFECTS: get this job application's coverLetter
    public void getCoverLetter() {
        // stub
    }

    // EFFECTS: get this job application's postingURL
    public void getPostingURL() {
        // stub
    }

    // EFFECTS: get this job application's notes
    public void getNotes() {
        // stub
    }

}
