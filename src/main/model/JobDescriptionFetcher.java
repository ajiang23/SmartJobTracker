package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Fetches job description from a given URL.
 */
public class JobDescriptionFetcher {

    // EFFECTS: Tries to fetch and extract job description text from the given URL
    public static String fetchDescription(String url) {
        try {
            System.out.println("🔍 Trying to fetch from: " + url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .get();

            System.out.println("🔍 Full HTML content:\n" + doc.outerHtml());

            String[] selectors = {
                    "div#JobDescription",
                    "div.job-info-row",
                    "div.job-description",
                    "div.description-section",
                    "div.viewjob-description",
                    "div.content",
                    "section.content",
                    "section.page-centered.description"
            };

            for (String selector : selectors) {
                Elements elements = doc.body().select(selector);
                if (!elements.isEmpty()) {
                    String description = elements.html()

                            .replaceAll("(?i)</h[1-6]>", "\n\n") 
                            .replaceAll("(?i)</(ul|ol)>", "\n") 
                            .replaceAll("(?i)<li>", "• ") 
                            .replaceAll("(?i)</li>", "\n") 
                            .replaceAll("(?i)<br\\s*/?>", "\n") 
                            .replaceAll("(?i)</p>", "\n\n") 
                            .replaceAll("\\<.*?>", "") 
                            .replaceAll("[ \\t]+", " ") 
                            .replaceAll("[ ]*\n[ ]*", "\n") 
                            .replaceAll("\n{3,}", "\n\n") 
                            .trim();

                    System.out.println("✅ Found description using selector: " + selector);
                    System.out.println("📄 Scraped description:\n" + description);
                    return description;
                }
            }

            System.out.println("⚠️ No matching selector found.");
            return "[Job description not found]";

        } catch (IOException e) {
            e.printStackTrace();
            return "[Failed to fetch job description]";
        }
    }
}
