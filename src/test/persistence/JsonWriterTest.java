package persistence;

import model.JobApplication;
import model.JobApplicationList;
import model.JobStatus;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JobApplicationList jl = new JobApplicationList();
            JsonWriter writer = new JsonWriter("./data/invalidFile.json");
            writer.open();
            fail("IOException expected.");
        } catch (FileNotFoundException pass) {
        }
    }

    @Test
    void testWriterEmptyJobAppList() {
        try {
            JobApplicationList jl = new JobApplicationList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJobAppList.json");
            writer.open();
            writer.write(jl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJobAppList.json");
            jl = reader.read();
            assertEquals(0, jl.getList().size());
        } catch (IOException notValid) {
            fail("File writing error.");
        }
    }

    @Test
    void testWriterGeneralJobAppList() {
        try {
            JobApplicationList jl = new JobApplicationList();
            File resume1 = new File ("src/test/resources/Test.pdf");
            File resume2 = new File ("src/test/resources/Test1.pdf");
            File cl = new File ("src/test/resources/Test2.pdf");
            JobApplication ja1 = new JobApplication ("Amazon", "Developer", LocalDate.of(2024, 11,05), resume1,  "amazon.ca"); 
            ja1.setStatus(JobStatus.Final_interview);
            jl.addJob(ja1);
            JobApplication ja2 = new JobApplication ("Github", "Specialist", LocalDate.of(2025,01,07), resume2, "github.ca");
            ja2.setCoverLetter(cl);
            ja2.setNotes("This is test note for ja2.");
            jl.addJob(ja2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJobAppList.json");
            writer.open();
            writer.write(jl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJobAppList.json");
            jl = reader.read();
            assertEquals(2, jl.getList().size());
            checkJobApplication("Amazon", "Developer", LocalDate.of(2024, 11, 05), resume1, "amazon.ca", null, "",
                    JobStatus.Final_interview, jl.getList().get(0));
            checkJobApplication("Github", "Specialist", LocalDate.of(2025, 01, 07), resume2, "github.ca", cl, "This is test note for ja2.",
                    JobStatus.Applied, jl.getList().get(1));
        } catch (IOException e) {
            fail("File writing error.");
        }
    }
}
