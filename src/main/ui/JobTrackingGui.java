package ui;

import model.EventLog;
import model.JobApplication;
import model.JobApplicationList;
import model.JobDescriptionFetcher;
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
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import model.JobPosting;
import model.JobPostingCache;
import model.JobDescriptionFetcher;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

// JobTrackingGUI Application 
public class JobTrackingGui extends JFrame {

    /**
     * Returns the folder where the running JAR is located.
     * Falls back to the current working directory if not running from a JAR.
     */
    private File getApplicationDir() {
        try {
            // Get path to the JAR file (or .class files when running in IDE)
            CodeSource src = JobTrackingGui.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                File jarFile = new File(src.getLocation().toURI().getPath());
                return jarFile.getParentFile();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // Fallback
        return new File(".");
    }

    // private static final String JSON_STORE = "./data/jobApplication.json";
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
    // EFFECTS: Initializes the graphical user interface and loads the saved job
    // applications if user chooses to.
    public JobTrackingGui() {
        super("Job Application Tracker");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        File appDir = getApplicationDir();
        File dataDir = new File(appDir, "data");
        // Ensure it exists
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        File jsonFile = new File(dataDir, "jobApplication.json");

        jobList = new JobApplicationList();
        jsonWriter = new JsonWriter(jsonFile.getPath());

        jsonReader = new JsonReader(jsonFile.getPath());
        // jsonReader = new JsonReader(JSON_STORE);
        // Determine the 'data' directory next to the JAR

        setupMainUI();
        setVisible(true);

        boolean loadData = getUserConfirmation("Would you like to load your previous job applications?");
        if (loadData) {
            loadJobAppList();
        }

        // Print event log to console when exiting app
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("\nEvent Log:");
                for (model.Event event : model.EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
                System.exit(0);
            }
        });

    }

    // EFFECTS: Prompts user to choose whether to load the previously saved state of
    // the application and returns user's choice.
    private boolean getUserConfirmation(String message) {
        int confirm = JOptionPane.showConfirmDialog(this, message, "Confirmation",
                JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }

    // EFFECTS: Initializes and arranges UI components in the frame.
    @SuppressWarnings("methodlength")
    private void setupMainUI() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf.");
        }

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JButton addButton = new JButton("Add Job");
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

        jobJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10), // Padding
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY) // Border
                ));
                return label;
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new BoxLayout(bottomButtonPanel, BoxLayout.LINE_AXIS));
        bottomButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomButtonPanel.add(Box.createHorizontalGlue());

        JButton deleteButton = new JButton("Delete Selected");
        JButton updateStatusButton = new JButton("Update Status");
        JButton viewPostingButton = new JButton("View Job Posting");

        deleteButton.addActionListener(e -> removeJob());
        updateStatusButton.addActionListener(e -> updateJobStatus());
        viewPostingButton.addActionListener(e -> {
            JobApplication selected = jobJList.getSelectedValue();

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Please select a job application.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            JobPosting jp = selected.getJobPosting();

            if (jp == null
                    || jp.getDescription() == null
                    || jp.getDescription().equals("[Job description not found]")
                    || jp.getDescription().equals("[Failed to fetch job description]")) {

                JTextArea inputArea = new JTextArea(20, 60);
                inputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                inputArea.setLineWrap(true);
                inputArea.setWrapStyleWord(true);
                inputArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

                JScrollPane bigPane = new JScrollPane(inputArea);

                JDialog dlg = new JDialog(this, "Paste Job Description", true);
                dlg.setLayout(new BorderLayout(10, 10));
                dlg.add(new JLabel(
                        "Could not fetch job description. Please paste it manually:"),
                        BorderLayout.NORTH);
                dlg.add(bigPane, BorderLayout.CENTER);

                JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
                JButton ok = new JButton("OK");
                JButton cancel = new JButton("Cancel");
                buttons.add(ok);
                buttons.add(cancel);
                dlg.add(buttons, BorderLayout.SOUTH);

                final boolean[] confirmed = { false };
                ok.addActionListener(ev -> {
                    confirmed[0] = true;
                    dlg.dispose();
                });
                cancel.addActionListener(ev -> dlg.dispose());

                dlg.setSize(800, 400);
                dlg.setLocationRelativeTo(this);
                dlg.setResizable(true);
                dlg.setVisible(true);

                if (confirmed[0] && !inputArea.getText().trim().isEmpty()) {
                    String manual = inputArea.getText().trim();
                    jp = new JobPosting(selected.getJobTitle(), manual, selected.getPostingURL());
                    selected.setJobPosting(jp);
                }

                if (jp == null) {
                    return;
                }
            }

            JTextArea textArea = new JTextArea(jp.toString());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));
            textArea.setMargin(new Insets(10, 10, 10, 10));
            JScrollPane jpScrollPane = new JScrollPane(textArea);
            jpScrollPane.setViewportBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel content = new JPanel(new BorderLayout());
            content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            content.add(jpScrollPane, BorderLayout.CENTER);

            JFrame descriptionFrame = new JFrame("Job Posting Details");
            descriptionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            descriptionFrame.setSize(800, 600);
            descriptionFrame.setLocationRelativeTo(this);
            descriptionFrame.add(content);
            descriptionFrame.setVisible(true);
        });

        bottomButtonPanel.add(deleteButton);
        bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomButtonPanel.add(updateStatusButton);
        bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomButtonPanel.add(viewPostingButton);

        mainPanel.add(bottomButtonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    // private void showCachedPostings() {
    // JobPostingCache cache = JobPostingCache.getInstance();
    // StringBuilder sb = new StringBuilder();

    // if (cache.getAllPostings().isEmpty()) {
    // sb.append("No cached job postings.");
    // } else {
    // for (Map.Entry<String, JobPosting> entry : cache.getAllPostings().entrySet())
    // {
    // sb.append("URL: ").append(entry.getKey()).append("\n");
    // sb.append(entry.getValue()).append("\n\n");
    // }
    // }

    // JTextArea textArea = new JTextArea(sb.toString());
    // textArea.setLineWrap(true);
    // textArea.setWrapStyleWord(true);
    // textArea.setEditable(false);
    // JScrollPane jpScrollPane = new JScrollPane(textArea);
    // JDialog dialog = new JDialog(this, "Job Posting Details", true);
    // dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    // dialog.setLayout(new BorderLayout());
    // dialog.add(jpScrollPane, BorderLayout.CENTER);
    // dialog.setSize(800, 600);
    // dialog.setResizable(true);
    // dialog.setLocationRelativeTo(this);
    // dialog.setVisible(true);
    // }

    // EFFECTS: Handles selection of filter options.
    private void handleFilterSelection(JComboBox<String> filterDropdown) {
        String selectedOption = (String) filterDropdown.getSelectedItem();

        if ("Filter by: Company Name".equals(selectedOption)) {
            filterJobByCompany();
        } else {
            resetJobList();
        }
    }

    // EFFECTS: Removes the selected job application.
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
            jobList.removeJob(selectedJob);
            refreshJobList();
            JOptionPane.showMessageDialog(this, "Job application deleted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: jobList
    // EFFECTS: Adds a new job application with user's input and updates the UI.
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

            JobPostingCache cache = JobPostingCache.getInstance();

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }

            JobPosting posting;
            String description = JobDescriptionFetcher.fetchDescription(url);

            if (description == null
                    || description.trim().isEmpty()
                    || description.equals("[Job description not found]")
                    || description.equals("[Failed to fetch job description]")) {

                JTextArea inputArea = new JTextArea(20, 60);
                inputArea.setLineWrap(true);
                inputArea.setWrapStyleWord(true);
                inputArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

                JScrollPane bigPane = new JScrollPane(inputArea);
                bigPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JDialog dlg = new JDialog(this, "Paste Job Description", true);
                dlg.setLayout(new BorderLayout(10, 10));

                dlg.add(new JLabel("Could not fetch job description. Please paste it manually:"), BorderLayout.NORTH);

                dlg.add(bigPane, BorderLayout.CENTER);

                JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
                JButton ok = new JButton("OK");
                JButton cancel = new JButton("Cancel");
                buttons.add(ok);
                buttons.add(cancel);
                dlg.add(buttons, BorderLayout.SOUTH);

                final boolean[] confirmed = { false };
                ok.addActionListener(e -> {
                    confirmed[0] = true;
                    dlg.dispose();
                });
                cancel.addActionListener(e -> dlg.dispose());

                dlg.setSize(800, 400);
                dlg.setLocationRelativeTo(this);
                dlg.setResizable(true);
                dlg.setVisible(true);

                if (confirmed[0]) {
                    String manual = inputArea.getText().trim();
                    if (!manual.isEmpty()) {
                        description = manual;
                    } else {
                        description = "[No description available]";
                    }
                } else {
                    description = "[No description available]";
                }
            }

            posting = new JobPosting(title, description.trim(), url);
            cache.cachePosting(url, posting);

            JobApplication newJob = new JobApplication(company, title, appliedDate, resume, url);
            if (coverLetter != null) {
                newJob.setCoverLetter(coverLetter);
            }
            newJob.setNotes(notes);
            JobPosting postingb = JobPostingCache.getInstance().getPosting(url);
            newJob.setJobPosting(postingb);
            jobList.addJob(newJob);

            refreshJobList();

            JOptionPane.showMessageDialog(this, "Job Application Added Successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            validInput = true;
        }

    }

    // MODIFIES: jobList
    // EFFECTS: Displays all job applications.
    private void refreshJobList() {
        jobListModel.clear();
        for (JobApplication job : jobList.getList()) {
            jobListModel.addElement(job);
        }

        jobJList.setModel(jobListModel);
        jobJList.repaint();
    }

    // EFFECTS: Displays a pie chart of job application statuses with percentage.
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

    // MODIFIES: jobList
    // EFFECTS: Changes the selected job application's status and updates the UI.
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
            selectedJob.updateStatus(JobStatus.valueOf(selectedStatus));
            refreshJobList();
            JOptionPane.showMessageDialog(this, "Job status updated successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: Resets the job list to display all applications
    private void resetJobList() {
        DefaultListModel<JobApplication> allJobsModel = new DefaultListModel<>();
        for (JobApplication job : jobList.getList()) {
            allJobsModel.addElement(job);
        }
        jobJList.setModel(allJobsModel);
        filterDropdown.setSelectedIndex(0);
    }

    // EFFECTS: Filters job applications by company name and displays results
    // directly in the
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

    // EFFECTS: Saves the current job application list to a JSON file.
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

    // MODIFIES: jobList
    // EFFECTS: Loads job applications from a JSON file and updates the UI.
    private void loadJobAppList() {
        try {
            jobList.suppressLogging(true);
            jobList = jsonReader.read();
            jobList.suppressLogging(false);
            refreshJobList();

            JOptionPane.showMessageDialog(this, "Successfully loaded job applications!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to load job applications.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: Saves the current session's data if user chooses to and terminates
    // the application.
    private void exitApplication() {
        int saveConfirm = JOptionPane.showConfirmDialog(this,
                "Would you like to save your job applications before exiting?", "Save Data",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (saveConfirm == JOptionPane.YES_OPTION) {
            saveJobAppList();
        }
        // now really close:
        dispose();
        System.exit(0);
        // dispatchEvent(new java.awt.event.WindowEvent(this,
        // java.awt.event.WindowEvent.WINDOW_CLOSING));
        // } else if (saveConfirm == JOptionPane.NO_OPTION) {
        // dispatchEvent(new java.awt.event.WindowEvent(this,
        // java.awt.event.WindowEvent.WINDOW_CLOSING));
        // }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JobTrackingGui::new);
    }

}
