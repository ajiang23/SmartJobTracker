package persistence;

import model.JobApplication;
import model.JobStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.LocalDate;

public class JsonTest {
    protected void checkJobApplication(String companyName, String jobTitle, LocalDate appliedDate, File resume,
            String postingURL, File coverLetter, String notes, JobStatus status, JobApplication jobApp) {
        assertEquals(companyName, jobApp.getCompanyName());
        assertEquals(jobTitle, jobApp.getJobTitle());
        assertEquals(appliedDate, jobApp.getAppliedDate());
        assertEquals(resume, jobApp.getResume());
        assertEquals(postingURL, jobApp.getPostingURL());
        assertEquals(coverLetter, jobApp.getCoverLetter());
        assertEquals(notes, jobApp.getNotes());
        assertEquals(status, jobApp.getStatus());
    }
}
