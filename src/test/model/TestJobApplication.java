package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJobApplication {
    private JobApplication jobApplication;

    @BeforeEach
    void runBefore() {
        LocalDate appliedDate = LocalDate.of(2025, 01, 01);
        File resume = new File("src/test/resources/Test.pdf");
        String url = "https://www.sonypicturesjobs.com/search-jobs/audio/22978/1?glat=49.26539993286133&glon=-123.25499725341797";

        jobApplication = new JobApplication("Sony", "Digital Cinema Technician", appliedDate, resume, url);
    }

    @Test
    void testConstructor() {
        assertEquals("Sony", jobApplication.getCompanyName());
        assertEquals("Digital Cinema Technician", jobApplication.getJobTitle());
        assertEquals(LocalDate.of(2025, 01, 01), jobApplication.getAppliedDate());
        File resume = new File("src/test/resources/Test.pdf");
        assertEquals(resume, jobApplication.getResume());
        assertEquals(
                "https://www.sonypicturesjobs.com/search-jobs/audio/22978/1?glat=49.26539993286133&glon=-123.25499725341797",
                jobApplication.getPostingURL());
        assertNull(jobApplication.getCoverLetter());
        assertEquals(JobStatus.Applied, jobApplication.getStatus());
        assertEquals("", jobApplication.getNotes());
    }

    @Test
    void testSetters() {
        jobApplication.setCompanyName("Marvel");
        assertEquals("Marvel", jobApplication.getCompanyName());
        jobApplication.setJobTitle("testTitle");
        assertEquals("testTitle", jobApplication.getJobTitle());
        LocalDate newDate = LocalDate.of(2025, 01, 02);
        jobApplication.setAppliedDate(newDate);
        assertEquals(newDate, jobApplication.getAppliedDate());
        jobApplication.setStatus(JobStatus.Rejected);
        assertEquals(JobStatus.Rejected, jobApplication.getStatus());
        File newResume = new File("src/test/resources/Test2.pdf");
        jobApplication.setResume(newResume);
        assertEquals(newResume, jobApplication.getResume());
        File newCL = new File("src/test/resources/Test3.pdf");
        jobApplication.setCoverLetter(newCL);
        assertEquals(newCL, jobApplication.getCoverLetter());
        jobApplication.setPostingURL("new link");
        assertEquals("new link", jobApplication.getPostingURL());
        jobApplication.setNotes("new notes");
        assertEquals("new notes", jobApplication.getNotes());
    }

    @Test
    void testToStringNull() {
        String companyName = "ABC Corp";
        String jobTitle = "Software Engineer";
        LocalDate appliedDate = LocalDate.of(2025, 01, 02);
        String status = "Applied";
        File resume = new File("src/test/resources/Test.pdf");
        File coverLetter = null;
        String postingURL = "https://www.jobposting.com";
        String notes = "";

        JobApplication app = new JobApplication(companyName, jobTitle, appliedDate, resume, postingURL);
        app.setAppliedDate(appliedDate);
        app.setCoverLetter(coverLetter);
        app.setNotes(notes);

        String expected = "<html><b>Company:</b> " + companyName
                + "<br/><b>Job Title:</b> " + jobTitle
                + "<br/><b>Applied Date:</b> " + appliedDate
                + "<br/><b>Status:</b> " + status
                + "<br/><b>Resume:</b> " + resume.getPath()
                + "<br/><b>Cover Letter:</b> None"
                + "<br/><b>Posting URL:</b> " + postingURL
                + "<br/><b>Notes:</b> None"
                + "</html>";

        assertEquals(expected, app.toString());
    }

    @Test
    void testToString() {
        String companyName = "ABC Corp";
        String jobTitle = "Software Engineer";
        LocalDate appliedDate = LocalDate.of(2025, 01, 02);
        String status = "Applied";
        File resume = new File("src/test/resources/Test.pdf");
        File coverLetter = new File("src/test/resources/Test1.pdf");
        String postingURL = "https://www.jobposting.com";
        String notes = "Great opportunity, looking forward to it.";

        JobApplication app = new JobApplication(companyName, jobTitle, appliedDate, resume, postingURL);
        app.setAppliedDate(appliedDate);
        app.setCoverLetter(coverLetter);
        app.setNotes(notes);

        String expected = "<html><b>Company:</b> " + companyName
                + "<br/><b>Job Title:</b> " + jobTitle
                + "<br/><b>Applied Date:</b> " + appliedDate
                + "<br/><b>Status:</b> " + status
                + "<br/><b>Resume:</b> " + resume.getPath()
                + "<br/><b>Cover Letter:</b> " + coverLetter.getPath()
                + "<br/><b>Posting URL:</b> " + postingURL
                + "<br/><b>Notes:</b> " + notes
                + "</html>";

        assertEquals(expected, app.toString());
    }

    @Test
    public void testUpdateStatusLogsEvent() {
        jobApplication.updateStatus(JobStatus.First_interview);

        boolean found = false;
        for (Event e : EventLog.getInstance()) {
            if (e.getDescription().equals("Updated status to: First_interview for Digital Cinema Technician in Sony")) {
                found = true;
                break;
            }
        }

        assertTrue(found, "Expected event was not logged.");
        assertEquals(JobStatus.First_interview, jobApplication.getStatus());
    }

}
