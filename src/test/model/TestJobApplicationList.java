package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.io.File;

public class TestJobApplicationList {
    private JobApplicationList testList;
    LocalDate date1 = LocalDate.of(2025, 01, 01);
    LocalDate date2 = LocalDate.of(2025, 01, 02);
    LocalDate date3 = LocalDate.of(2025, 01, 03);
    String url1 = "url1";
    String url2 = "url2";
    String url3 = "url3";
    File resume1 = new File("src/test/resources/Test.pdf");
    File resume2 = new File("src/test/resources/Test2.pdf");
    File resume3 = new File("src/test/resources/Test3.pdf");
    JobApplication jp1 = new JobApplication("a", "a", date1, resume1, url1);
    JobApplication jp2 = new JobApplication("a", "b", date2, resume2, url2);
    JobApplication jp3 = new JobApplication("b", "c", date3, resume3, url3);

    @BeforeEach
    void runBefore() {
        testList = new JobApplicationList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testList.getList().size());
    }

    @Test
    void testAddJob() {
        testList.addJob(jp1);
        assertEquals(1, testList.getList().size());
        assertEquals(jp1, testList.getList().get(0));
        testList.addJob(jp2);
        assertEquals(2, testList.getList().size());
        assertEquals(jp1, testList.getList().get(0));
        assertEquals(jp2, testList.getList().get(1));
        testList.addJob(jp3);
        assertEquals(3, testList.getList().size());
        assertEquals(jp1, testList.getList().get(0));
        assertEquals(jp2, testList.getList().get(1));
        assertEquals(jp3, testList.getList().get(2));
    }

    @Test
    void testRemoveJob() {
        testList.addJob(jp1);
        testList.addJob(jp2);
        testList.addJob(jp3);

        testList.removeJob(jp1);

        assertEquals(2, testList.getList().size());
        assertEquals(jp2, testList.getList().get(0));
        assertEquals(jp3, testList.getList().get(1));

        testList.removeJob(jp3);
        assertEquals(1, testList.getList().size());
        assertEquals(jp2, testList.getList().get(0));

        testList.removeJob(jp2);
        assertEquals(0, testList.getList().size());
    }

    @Test
    void testFilterByCompany() {
        testList.addJob(jp1);
        testList.addJob(jp2);
        testList.addJob(jp3);

        ArrayList<JobApplication> filteredList = testList.filterByCompany("a");
        assertEquals(2, filteredList.size());
        assertEquals(jp1, filteredList.get(0));
        assertEquals(jp2, filteredList.get(1));

        ArrayList<JobApplication> filteredList2 = testList.filterByCompany("b");
        assertEquals(1, filteredList2.size());
        assertEquals(jp3, filteredList2.get(0));

        ArrayList<JobApplication> filteredList3 = testList.filterByCompany("c");
        assertEquals(0, filteredList3.size());
    }

}
