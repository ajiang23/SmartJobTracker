package ui;

import model.JobApplication;
import model.JobApplicationList;
import model.JobStatus;
import persistence.JsonReader;
import persistence.JsonWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;
import java.text.DecimalFormat;
import java.text.AttributedString;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// JobTrackingGUI Application 
public class JobTrackingGUI extends JFrame {
    private static final String JSON_STORE = "./data/jobApplication.json";
    private JobApplicationList jobList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel mainPanel;
    // private JButton viewButton, addButton, removeButton, updateButton,
    // filterButton, saveButton,
    // loadButton, exitButton,
    // pieChartButton, deleteButton;
    private DefaultListModel<JobApplication> jobListModel;
    private JList<JobApplication> jobJList;
    // private DefaultListModel<JobApplication> filteredModel;
    private JComboBox<String> filterDropdown;
    // private DefaultTableModel jobTableModel;
    // private JTable jobTable;

    // Constructs GUI
    public JobTrackingGUI() {
        super("Job Application Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        jobList = new JobApplicationList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setupMainUI(); // Start with the main menu
        setVisible(true);

        // Prompt user after UI loads
        boolean loadData = getUserConfirmation("Would you like to load your previous job applications?");
        if (loadData) {
            loadJobAppList();
        }
    }

    private boolean getUserConfirmation(String message) {
        int confirm = JOptionPane.showConfirmDialog(this, message, "Confirmation",
                JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }

    @SuppressWarnings ("methodlength")
    private void setupMainUI() {
        mainPanel = new JPanel(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem viewChartItem = new JMenuItem("View Status Breakdown");
        JMenuItem saveItem = new JMenuItem("Save Applications");
        JMenuItem loadItem = new JMenuItem("Load Applications");
        JMenuItem exitItem = new JMenuItem("Exit");

        viewChartItem.addActionListener(e -> showStatusPieChart());
        saveItem.addActionListener(e -> saveJobAppList());
        loadItem.addActionListener(e -> loadJobAppList());
        exitItem.addActionListener(e -> exitApplication());

        menu.add(viewChartItem);
        menu.add(saveItem);
        menu.add(loadItem);
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel topPanel = new JPanel();

        JButton addButton = new JButton("➕ Add Job");
        String[] filterOptions = { "Filter by: None", "Filter by: Company Name" };
        JComboBox<String> filterDropdown = new JComboBox<>(filterOptions);

        addButton.addActionListener(e -> openAddJobWindow());
        filterDropdown.addActionListener(e -> handleFilterSelection(filterDropdown));

        topPanel.add(addButton);
        topPanel.add(filterDropdown);

        jobListModel = new DefaultListModel<>();
        jobJList = new JList<>(jobListModel);
        jobJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(jobJList);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomButtonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton deleteButton = new JButton("Delete Selected");
        JButton updateStatusButton = new JButton("Update Status");

        deleteButton.addActionListener(e -> removeJob());
        updateStatusButton.addActionListener(e -> updateJobStatus());

        bottomButtonPanel.add(deleteButton);
        bottomButtonPanel.add(updateStatusButton);

        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    private void handleFilterSelection(JComboBox<String> filterDropdown) {
        String selectedOption = (String) filterDropdown.getSelectedItem();

        if ("Filter by: Company Name".equals(selectedOption)) {
            filterJobByCompany();
        } else {
            resetJobList();
        }
    }

    // Removes a job application from the list
    private void removeJob() {
        int selectedIndex = jobJList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job application to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JobApplication selectedJob = jobJList.getSelectedValue();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this application?\n" + selectedJob.getCompanyName() + " - "
                        + selectedJob.getJobTitle(),
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            jobList.getList().remove(selectedJob);
            refreshJobList();
            JOptionPane.showMessageDialog(this, "Job application deleted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @SuppressWarnings("methodlength")
    private void openAddJobWindow() {
        JTextField companyField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JTextField urlField = new JTextField();
        JTextField notesField = new JTextField();

        JTextField resumeField = new JTextField();
        resumeField.setEditable(false);
        JButton resumeButton = new JButton("Upload Resume");

        JTextField coverLetterFieldPath = new JTextField();
        coverLetterFieldPath.setEditable(false);
        JButton coverLetterButton = new JButton("Upload Cover Letter");

        resumeButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                resumeField.setText(selectedFile.getAbsolutePath());
            }
        });

        coverLetterButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                coverLetterFieldPath.setText(selectedFile.getAbsolutePath());
            }
        });

        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.add(new JLabel("<html><b>Company Name:</b> <font color='red'>*</font></html>"));
        panel.add(companyField);
        panel.add(new JLabel("<html><b>Job Title:</b> <font color='red'>*</font></html>"));
        panel.add(titleField);
        panel.add(new JLabel("<html><b>Applied Date:</b> <font color='red'>*</font></html>"));
        panel.add(dateField);
        panel.add(new JLabel("<html><b>Job Posting URL:</b> <font color='red'>*</font></html>"));
        panel.add(urlField);
        panel.add(new JLabel("<html><b>Resume:</b> <font color='red'>*</font></html>"));
        panel.add(resumeField);
        panel.add(new JLabel(""));
        panel.add(resumeButton);
        panel.add(new JLabel("<html><b>Cover Letter (Optional):</b></html>"));
        panel.add(coverLetterFieldPath);
        panel.add(new JLabel(""));
        panel.add(coverLetterButton);
        panel.add(new JLabel("<html><b>Notes (Optional):</b></html>"));
        panel.add(notesField);

        boolean validInput = false;

        while (!validInput) {
            int result = JOptionPane.showConfirmDialog(this, panel, "Add Job Application",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                return;
            }

            String company = companyField.getText().trim();
            String title = titleField.getText().trim();
            String dateString = dateField.getText().trim();
            String resumePath = resumeField.getText().trim();
            String url = urlField.getText().trim();
            String coverLetterPath = coverLetterFieldPath.getText().trim();
            String notes = notesField.getText().trim();

            if (company.isEmpty() || title.isEmpty() || dateString.isEmpty() || resumePath.isEmpty() || url.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All required fields (marked with *) must be filled.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            LocalDate appliedDate;
            try {
                appliedDate = LocalDate.parse(dateString);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Setting to today's date.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                appliedDate = LocalDate.now();
            }

            File resume = new File(resumePath);
            if (!resume.exists()) {
                JOptionPane.showMessageDialog(this, "Resume file not found! Please select a valid file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            File coverLetter = null;
            if (!coverLetterPath.isEmpty()) {
                coverLetter = new File(coverLetterPath);
                if (!coverLetter.exists()) {
                    JOptionPane.showMessageDialog(this, "Cover letter file not found! Please select a valid file.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            }

            JobApplication newJob = new JobApplication(company, title, appliedDate, resume, url);
            if (coverLetter != null) {
                newJob.setCoverLetter(coverLetter);
            }
            newJob.setNotes(notes);
            jobList.addJob(newJob);

            refreshJobList();

            JOptionPane.showMessageDialog(this, "Job Application Added Successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            validInput = true;
        }
    }

    private void refreshJobList() {
        jobListModel.clear();
        for (JobApplication job : jobList.getList()) {
            jobListModel.addElement(job);
        }

        jobJList.setModel(jobListModel);
        jobJList.repaint();
    }

    // Displays a pie chart of job application statuses with percentage labels.
    @SuppressWarnings("methodlength")
    private void showStatusPieChart() {
        if (jobList.getList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No job applications available to display.");
            return;
        }

        Map<JobStatus, Integer> statusCount = new HashMap<>();
        for (JobApplication job : jobList.getList()) {
            statusCount.put(job.getStatus(), statusCount.getOrDefault(job.getStatus(), 0) + 1);
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<JobStatus, Integer> entry : statusCount.entrySet()) {
            dataset.setValue(entry.getKey().name(), entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Job Application Status Breakdown",
                dataset, true, true, false);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSimpleLabels(true);
        plot.setLabelGenerator(new PieSectionLabelGenerator() {
            @Override
            public String generateSectionLabel(PieDataset dataset, Comparable key) {
                if (dataset == null) {
                    return null;
                }
                Number value = dataset.getValue(key);
                double total = 0;
                for (int i = 0; i < dataset.getItemCount(); i++) {
                    total += dataset.getValue(i).doubleValue();
                }
                double percentage = (value.doubleValue() / total) * 100;
                return key + " (" + new DecimalFormat("#.##").format(percentage) + "%)";
            }

            @Override
            public AttributedString generateAttributedSectionLabel(PieDataset dataset, Comparable key) {
                return null;
            }
        });

        ChartPanel chartPanel = new ChartPanel(pieChart);
        JFrame chartFrame = new JFrame("Job Status Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(500, 400);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }

    private void updateJobStatus() {
        int selectedIndex = jobJList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job application to update.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JobApplication selectedJob = jobJList.getSelectedValue();

        JobStatus[] statusOptions = JobStatus.values();
        String[] statusStrings = new String[statusOptions.length];
        for (int i = 0; i < statusOptions.length; i++) {
            statusStrings[i] = statusOptions[i].name();
        }

        String selectedStatus = (String) JOptionPane.showInputDialog(
                this, "Select new status:", "Update Status",
                JOptionPane.PLAIN_MESSAGE, null, statusStrings, selectedJob.getStatus().name());

        if (selectedStatus != null) {
            selectedJob.setStatus(JobStatus.valueOf(selectedStatus));
            refreshJobList();
            JOptionPane.showMessageDialog(this, "Job status updated successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Resets the job list to display all applications
    private void resetJobList() {
        DefaultListModel<JobApplication> allJobsModel = new DefaultListModel<>();
        for (JobApplication job : jobList.getList()) {
            allJobsModel.addElement(job);
        }
        jobJList.setModel(allJobsModel);
        filterDropdown.setSelectedIndex(0);
    }

    // Filters job applications by company name and displays results directly in the
    // main panel
    private void filterJobByCompany() {
        if (jobList.getList().isEmpty()) {
            return;
        }

        String companyName = JOptionPane.showInputDialog(this, "Enter company name to filter:");
        if (companyName == null || companyName.trim().isEmpty()) {
            return;
        }
        DefaultListModel<JobApplication> filteredModel = new DefaultListModel<>();
        for (JobApplication job : jobList.filterByCompany(companyName.trim())) {
            filteredModel.addElement(job);
        }

        jobJList.setModel(filteredModel);
    }

    // Saves job applications to file
    private void saveJobAppList() {
        try {
            jsonWriter.open();
            jsonWriter.write(jobList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Successfully saved job applications!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: Unable to save job applications.");
        }
    }

    private void loadJobAppList() {
        try {
            jobList = jsonReader.read();
            refreshJobList();

            JOptionPane.showMessageDialog(this, "Successfully loaded job applications!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: Unable to load job applications.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Exits the application
    private void exitApplication() {
        int saveConfirm = JOptionPane.showConfirmDialog(this,
                "Would you like to save your job applications before exiting?", "Save Data",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (saveConfirm == JOptionPane.YES_OPTION) {
            saveJobAppList();
            System.exit(0);
        } else if (saveConfirm == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

}
