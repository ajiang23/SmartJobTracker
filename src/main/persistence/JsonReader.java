package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONObject;

import model.JobApplicationList;

//Represents a reader that reads from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads job application list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public JobApplicationList read() throws IOException {
        // stub
    }

     // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
       //stub
    }

    // EFFECTS: parses job application list from JSON object and returns
    // it
    private JobApplicationList parseJobApplicationList(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: jl
    // EFFECTS: parses job applications from JSON object and add them to newList
    private void addJobApplications(JobApplicationList jl, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: jl
    // EFFECTS: parses job application fron JSON object and adds it to job application list
    private void addJobApplication(JobApplicationList newList, JSONObject jsonObject) {
        // stub
    }

}
