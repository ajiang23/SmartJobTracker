package model;

/**
 * Represents a job posting with a title and description.
 */
public class JobPosting {
    private String title;
    private String description;
    private String url;

    // REQUIRES: title and description are non-null
    // MODIFIES: this
    // EFFECTS: constructs a JobPosting with the given title and description
    public JobPosting(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }


    @Override
    public String toString() {
        return "Title: " + title + "\n"
                + "URL: " + url + "\n"
                + "Description:\n" + description;
    }

}