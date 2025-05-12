# SmartJobTracker

**SmartJobTracker** is a lightweight Java Swing application designed to help students and professionals stay organized during their job search by managing job applications.

---

## Project Overview

SmartJobTracker is an application for tracking job applications through a clean, user-friendly interface. The app allows users to:

- Add, update, and delete job applications.
- Visualize job application statuses in a pie chart.
- Save and load application data to/from JSON files.
- Automatically fetch and cache full job postings for offline review and interview preparation.

---

## Key Features

1. **Add New Job Application**
   - Add details such as job title, company, status, and location.
2. **View & Filter Applications**
   - View applications in a list and filter them by status (e.g., Applied, Interviewing, Rejected).
3. **Update Application Status**
   - Update the status of a job application as it progresses through stages.
4. **Delete Job Application**
   - Remove an application that is no longer relevant.
5. **Visualize Application Pipeline**
   - View a pie chart showing the status breakdown of all job applications.
6. **Fetch & Cache Job Postings**
   - Automatically capture and store job descriptions for offline access.

---

## Tech Stack & Architecture

- **Frontend**  
  - **Java Swing**: Provides a responsive GUI for seamless interactions.
  - **JFreeChart**: Used to visualize the job application pipeline in a pie chart.
  - **Jsoup**: For parsing and extracting job posting details.

- **Backend**  
  - **Java**: Core application logic is written in Java, ensuring portability and ease of maintenance.

- **Data Persistence**  
  - **JSON**: Job application data is stored in a JSON file for easy saving and loading.

---
## Object-Oriented Programming (OOP) Principles

SmartJobTracker follows core OOP principles to ensure maintainability and scalability:

- **Modularity**: The application is divided into distinct classes.
- **Single Responsibility**: Each class has a well-defined task.
- **Encapsulation**: Internal data is hidden, with only necessary methods exposed.
- **Inheritance & Polymorphism**: Common behaviors are inherited, and methods behave differently based on the object context.

---

## User Interface Options

SmartJobTracker provides two interfaces, both using the same backend:

- **Graphical User Interface (GUI)**: A user-friendly experience built with Java Swing.  
  
- **Command-Line Interface (CLI)**: A text-based interface for quick interactions.  

Both interfaces ensure consistency and functionality, with the same data-handling logic.

---

## JSON Data Persistence

The application uses JSON to store and load job application data, ensuring persistence across sessions.

- **Saving**: `JsonWriter.java` handles saving data.
- **Loading**: `JsonReader.java` reads and reconstructs the application state from the saved file.

---

## Unit Testing

Unit tests are written using **JUnit 5** to ensure code reliability and proper functionality:

- **Model Tests**: e.g., `TestJobApplication.java`, `TestJobApplicationList.java`
- **Persistence Tests**: e.g., `JsonReaderTest.java`, `JsonWriterTest.java`

Tests cover all branches and scenarios, ensuring 100% code coverage.

---

## Event Log

An event log tracks significant actions during the session, including:

- Changes to job application data
- Additions, updates, and deletions of job postings

Each entry includes a timestamp and a detailed description of the action, ensuring traceability.

Example log entries:
```
Fri Mar 28 01:54:35 PDT 2025 - Job application for 'Software Engineer' added
Fri Mar 28 01:55:15 PDT 2025 - Job application status changed to 'Applied'
```


## Getting Started Locally

### Prerequisites
- **Java 11+**: Ensure you have Java 11 or higher installed on your machine.
  - Check the version with:
    ```bash
    java --version
    ```
- **Maven or Gradle**: The project can be built using Maven or Gradle. Make sure you have one of these installed.

### 1. Clone the repository
```
git clone https://github.com/ajiang23/SmartJobTracker.git
cd SmartJobTracker
```

### 2. Build & Run
Using the provided launcher:
```
chmod +x run.command
./run.command
```
Alternatively, using Maven:
```
mvn clean package  
java -jar target/SmartJobTracker-1.2.0.jar
```
Or with Gradle:
```
gradle clean shadowJar  
java -jar build/libs/SmartJobTracker-all.jar
```
---

## Future Roadmap
- AI-powered Interview Prep:
Generate practice interview questions from cached job postings and uploaded resumes.

- Advanced Filters:
Implement multi-criteria search (role, location, salary range, etc.).

- Export/Share:
Enable export of job data to PDF/CSV.

- Cross-Platform Installers:
Use jpackage to build .exe, .dmg, and .AppImage installers.

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Built with ❤️ by Alicia Jiang
