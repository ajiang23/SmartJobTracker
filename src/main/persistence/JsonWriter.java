package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import model.JobApplicationList;
import java.io.File;
import org.json.JSONObject;

//Represents a writer writing JSON representation of job applications to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: open writer and prints errors if file fails to open
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of job application list to file
    public void write(JobApplicationList jobList) {
        JSONObject json = jobList.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file and ensures it's saved
    public void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer safely
    public void close() {
        writer.close();
    }
}
