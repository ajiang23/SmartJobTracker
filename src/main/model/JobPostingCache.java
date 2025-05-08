package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Caches JobPostings using their URLs as keys.
 */
public class JobPostingCache {
    private static JobPostingCache instance;
    private Map<String, JobPosting> postings;

    private JobPostingCache() {
        postings = new HashMap<>();
    }

    // EFFECTS: returns the singleton instance of the cache
    public static JobPostingCache getInstance() {
        if (instance == null) {
            instance = new JobPostingCache();
        }
        return instance;
    }

    // MODIFIES: this
    // EFFECTS: adds a posting if not already cached
    public void cachePosting(String url, JobPosting posting) {
        postings.putIfAbsent(url, posting);
    }

    // EFFECTS: returns the cached posting for the given URL, or null if not present
    public JobPosting getPosting(String url) {
        return postings.get(url);
    }

    public Map<String, JobPosting> getAllPostings() {
        return postings;
    }

    public void clear() {
        postings.clear();
    }
}