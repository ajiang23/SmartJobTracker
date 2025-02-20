package ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.JobApplication;
import model.JobApplicationList;
import model.JobStatus;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//Job application tracking app 
public class JobTrackingApp {
    private Scanner input;
    private JobApplicationList newList;

    // EFFECTS: instantiates and runs the app
    public JobTrackingApp() {
        input = new Scanner(System.in);
        newList = new JobApplicationList();
        runApp();
    }

    // EFFECTS: runs the application loop, processes user's input until user chooses
    // to exit
    private void runApp() {
        boolean keepRunning = true;
        Integer command = null;

        while (keepRunning) {
            displayMenu();

            if (input.hasNextInt()) {
                command = input.nextInt();
                input.nextLine();

                if (command == 6) {
                    keepRunning = false;
                } else {
                    handleUserCommand(command);
                }
            } else {
                System.out.println("Invalid selection. Please enter a valid number.");
                input.nextLine();
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\n1 -> View all applications");
        System.out.println("\n2 -> Add an application");
        System.out.println("\n3 -> Remove an application");
        System.out.println("\n4 -> Update an application's status");
        System.out.println("\n5 -> Filter by company name");
        System.out.println("\n6 -> Exit");

    }

    // EFFECTS: processes user's command
    private void handleUserCommand(Integer command) {
        switch (command) {
            case 1:
                viewJobList();
                break;
            case 2:
                addJob();
                break;
            case 3:
                removeJob();
                break;
            case 4:
                updateStatus();
                break;
            case 5:
                filterJobByCompany();
                break;
            default:
                System.out.println("Invalid selection. Please enter a valid number.");
        }
    }

    // MODIFIES: newList
    // EFFECTS: adds a job application to this list
    private void addJob() {
        System.out.println("Enter the company name:");
        String companyName = getRequiredInput();

        System.out.println("Enter the job title:");
        String jobTitle = getRequiredInput();

        System.out.println("Enter the applied date (YYYY-MM-DD):");
        LocalDate appliedDate = processDate();

        System.out.println("Uploade the resume");
        File resume = processResume();

        System.out.println("Enter the job posting url:");
        String postingURL = getRequiredInput();

        JobApplication newJob = new JobApplication(companyName, jobTitle, appliedDate, resume, postingURL);
        newList.addJob(newJob);

        System.out.println("Job application added successfully!");
    }

    // MODIFIES: companyName,jobTitle, postingURL
    // EFFECTS: captures user's input to the fields companyName,jobTitle, postingURL
    // and ensures the input is not blank
    private String getRequiredInput() {
        String userInput = "";

        while (userInput.isBlank()) {
            userInput = input.nextLine().trim();
            if (userInput.isBlank()) {
                System.out.println("This field is required. Please enter a valid input.");
            }
        }
        return userInput;
    }

    // MODIFIES: appliedDate in this job application
    // EFFECTS: ensures user's input is in LocalDate format otherwise change to
    // today's date
    private LocalDate processDate() {
        String dateInput = input.nextLine();
        LocalDate appliedDate;
        try {
            appliedDate = LocalDate.parse(dateInput);
        } catch (DateTimeParseException invalidDate) {
            System.out.println("Invalid date format. Setting to today's date.");
            appliedDate = LocalDate.now();
        }
        return appliedDate;
    }

    // MODIFIES: resume in this job application
    // EFFECTS: ensures user uploads a valid resume file
    private File processResume() {
        File resume = null;
        while (resume == null || !resume.exists()) {
            String resumePath = input.nextLine();
            resume = new File(resumePath);
            if (!resume.exists()) {
                System.out.println("File not found. Please enter a valid file.");
            }
        }
        return resume;
    }

    // EFFECTS: displays all exising job applications in this list
    private void viewJobList() {
        if (newList.getList().isEmpty()) {
            System.out.println("No job applications found.");
            return;
        }

        int currentIndex = 1;
        for (JobApplication jobApp : newList.getList()) {
            System.out.println(currentIndex + "." + jobApp.getCompanyName() + "-" + jobApp.getJobTitle());
            currentIndex += 1;
        }
    }

    // REQUIRES: job application must exist in this list
    // MODIFIES: newList
    // EFFECTS: removes a job application from this list
    private void removeJob() {
        if (newList.getList().isEmpty()) {
            System.out.println("No job applications to remove.");
            return;
        }

        System.out.println("Please select a job to delete by entering its number:");
        viewJobList();

        int userSelection;

        try {
            userSelection = input.nextInt();

            if (userSelection < 1 || userSelection > newList.getList().size()) {
                System.out.println("Invalid selection. Please enter a valid number.");
                return;
            }
            JobApplication removeJob = newList.getList().get(userSelection - 1);
            newList.removeJob(removeJob);
            System.out.println("Invalid input. Please enter a number.");

        } catch (InputMismatchException notValid) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
    }

    // EFFECTS: selects a valid job application from this list otherwise gives error
    // messages
    private JobApplication selectJobApplication() {
        if (newList.getList().isEmpty()) {
            System.out.println("No job applications to update.");
            return null;
        }

        System.out.println("Please select a job to update by entering its number:");
        viewJobList();

        int userSelection;
        try {
            userSelection = input.nextInt();

            if (userSelection < 1 || userSelection > newList.getList().size()) {
                System.out.println("Invalid selection. Please enter a valid number.");
                return null;
            }

            return newList.getList().get(userSelection - 1);

        } catch (InputMismatchException notValid) {
            System.out.println("Invalid input. Please enter a number.");
            return null;
        }
    }

    // MODIFIES: status of a given job application
    // EFFECTS: updates a job application's status to user's input, ensures user's
    // input is valid otherwise gives error messages
    public void updateJobStatus(JobApplication job) {
        if (job == null) {
            return;
        }
        System.out.println("Please choose a new status:");
        for (JobStatus status : JobStatus.values()) {
            System.out.println("- " + status);
        }
        input.nextLine();

        jobStatusHelper(job);
    }

    private void jobStatusHelper(JobApplication job) {
        JobStatus newStatus = null;
        while (newStatus == null) {
            System.out.print("Enter a valid status: ");
            String statusInput = input.nextLine().trim();

            if (statusInput.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a valid status.");
                continue;
            }
            try {
                newStatus = JobStatus.valueOf(statusInput);
                job.setStatus(newStatus);
                System.out.println("Updated successfully: " + job.getCompanyName() + " - "
                        + job.getJobTitle() + " -> " + newStatus);
            } catch (IllegalArgumentException invalidInput) {
                System.out.println("Invalid status. Please enter a valid status from the list provided.");
                return;
            }
        }
    }

    // REQUIRES: job applicaiton must exist in this list
    // MODIFIES: a job application's status
    // EFFECTS: updates a job application's status
    private void updateStatus() {
        updateJobStatus(selectJobApplication());
    }

    // EFFECTS: filters job applications by company name and displays filtered
    // results
    private void filterJobByCompany() {
        if (newList.getList().isEmpty()) {
            System.out.println("No job applicationst to be filtered.");
            return;
        }

        System.out.println("Enter company name to filter by: ");
        String userInput = getRequiredInput();

        ArrayList<JobApplication> filteredList = newList.filterByCompany(userInput);

        if (filteredList.isEmpty()) {
            System.out.println("No job applications found for the company: " + userInput);
            return;
        } else {
            System.out.println("Filtered results:");
            for (JobApplication job : filteredList) {
                System.out.println("-" + job.getCompanyName() + "-" + job.getJobTitle());
            }
        }
    }
}
