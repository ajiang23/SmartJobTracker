package persistence;

import java.io.PrintWriter;
import model.JobApplicationList;

//Represents a writer writing JSON representation of job applications to file
public class JsonWriter {
    private static final int TAB = 4; 
    private PrintWriter writer; 
    private String destination; 
    
    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        //stub
    }

    //MODIFIES: this
    //EFFECTS: open writer and prints errors if file fails to open
    public void open(){
        //stub
    }

    //MODIFIES: this 
    //EFFECTS: writes JSON representation of job application list to file
    public void write(JobApplicationList jobList){
        //stub
    }

    //MODIFIES: this
    //EFFECTS: writes string to file and ensures it's saved
    public void saveToFile(String json) {
        //stub
    }

    //MODIFIES: this
    //EFFECTS: closes writer safely 
    public void close(){
        //stub
    }
}
