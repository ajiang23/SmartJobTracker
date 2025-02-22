package persistence;

import model.JobApplication;
import model.JobApplicationList;
import model.JobStatus;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            JobApplicationList jl = reader.read();
            fail("IOException expected");
        } catch (IOException pass) {
        }
    }

    @Test
    void testReaderEmptyJobAppList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJobAppList.json");
        try {
            JobApplicationList jl = reader.read();
            assertEquals(0, jl.getList().size());
        } catch (IOException notvalid) {
            fail("File reading error.");
        }
    }

    @Test
    void testReaderGeneralJobAppList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJobAppList.json");
        try {
            JobApplicationList jl = reader.read();
            assertEquals(2, jl.getList().size());
            File resume1 = new File ("src/test/resources/Test.pdf");
            File resume2 = new File ("src/test/resources/Test1.pdf");
            File cl = new File ("src/test/resources/Test2.pdf");
            checkJobApplication("Google", "Specialist", LocalDate.of(2025, 01,01), resume1,  "google.ca", null, "", JobStatus.Applied, jl.getList().get(0));
            checkJobApplication("Sony", "Manager", LocalDate.of(2025,02,21), resume2, "sony.ca", cl, "test notes", JobStatus.Rejected, jl.getList().get(1));
        } catch (IOException notValid) {
            fail("File reading error.");
        }
    }
}