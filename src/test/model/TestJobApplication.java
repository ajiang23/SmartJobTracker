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

}
