package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new JobTrackingApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run app: file not found.");
        }
    }
}
