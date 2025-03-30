package model;

// import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event testEvent;
    private Date testDate;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        testEvent = new Event("New job application added."); // (1)
        testDate = Calendar.getInstance().getTime(); // (2)
    }

    // @Test
    // public void testEvent() {
    // assertEquals("New job application added.", e.getDescription());
    // assertEquals(d, e.getDate());
    // }

    @Test
    public void testGetDateAndDescription() {
        assertNotNull(testEvent.getDate());
        assertEquals("New job application added.", testEvent.getDescription());
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(testEvent.equals(testEvent));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(testEvent.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(testEvent.equals("string"));
    }

    @Test
    public void testEqualsDifferentEvent() {
        Event different = new Event("Different description");
        assertFalse(testEvent.equals(different));
    }

    @Test
    public void testEqualsSameValuesManuallySet() {
        Date date = new Date();
        Event e1 = new Event("Same");
        Event e2 = new Event("Same");

        try {
            java.lang.reflect.Field dateField = Event.class.getDeclaredField("dateLogged");
            dateField.setAccessible(true);
            dateField.set(e1, date);
            dateField.set(e2, date);
        } catch (Exception ex) {
            fail("Shouldn't be thrown.");
        }

        assertTrue(e1.equals(e2));
    }

    @Test
    public void testHashCode() {
        assertEquals(
                13 * testEvent.getDate().hashCode() + testEvent.getDescription().hashCode(),
                testEvent.hashCode());
    }

    @Test
    public void testEqualsDifferentDateSameDescription() {
        Event e1 = new Event("Same");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // pass
        }
        Event e2 = new Event("Same");
        assertFalse(e1.equals(e2));
    }

    @Test
    public void testToString() {
        assertEquals(testDate.toString() + "\n" + "New job application added.", testEvent.toString());
    }
}