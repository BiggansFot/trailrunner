package se.iths;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.mockito.*;
import static org.mockito.Mockito.*;

import java.util.*;

public class TestUser 
{
    User user;

    @Mock
    private DatabaseAPI data;

    @BeforeEach
    public void setupAPI()
    {
        data = mock(DatabaseAPI.class);
        user = new User(0, 0, data);
    }

    @Test
    public void testCalculateUserBMI()
    {
        User user1 = new User(72, 180);
        User user2 = new User(64, 150);

        assertEquals(22.2, user1.calculateBMI(), 0.1);
        assertEquals(28.4, user2.calculateBMI(), 0.1);
    }

    @Test
    public void testDBAverageDistance() {
        Run run1 = new Run("run1", 23, 127);
        Run run2 = new Run("run2", 54, 355, LocalDate.of(2020, 11, 10));
        Run run3 = new Run("run3", 76, 45);

        List<String> runIDs = new ArrayList<>();
        runIDs.add("run1");
        runIDs.add("run2");
        runIDs.add("run3");

        when(data.getRecordIDs()).thenReturn(runIDs);

        when(data.readRecord("run1")).thenReturn(run1);
        when(data.readRecord("run2")).thenReturn(run2);
        when(data.readRecord("run3")).thenReturn(run3);

        assertEquals(51, user.averageDistance());
    }

    @Test
    public void testDBTotalDistance() {
        Run run1 = new Run("run1", 23, 127);
        Run run2 = new Run("run2", 54, 355, LocalDate.of(2020, 11, 10));
        Run run3 = new Run("run3", 76, 45);

        List<String> runIDs = new ArrayList<>();
        runIDs.add("run1");
        runIDs.add("run2");
        runIDs.add("run3");

        when(data.getRecordIDs()).thenReturn(runIDs);

        when(data.readRecord("run1")).thenReturn(run1);
        when(data.readRecord("run2")).thenReturn(run2);
        when(data.readRecord("run3")).thenReturn(run3);

        assertEquals(153, user.totalDistance());
    }

    @Test
    public void testDBDisplayRunDetails()
    {
        Run run1 = new Run("run1", 23, 127);
        Run run2 = new Run("run2", 54, 355, LocalDate.of(2020, 11, 10));
        Run run3 = new Run("run3", 18, 3600);

        List<String> runIDs = new ArrayList<>();
        runIDs.add("run1");
        runIDs.add("run2");
        runIDs.add("run3");

        when(data.getRecordIDs()).thenReturn(runIDs);
        when(data.readRecord("run1")).thenReturn(run1);
        when(data.readRecord("run2")).thenReturn(run2);
        when(data.readRecord("run3")).thenReturn(run3);
        String userDetails ="This run had a distance of: 23km. And a time of: 0:2:7 and occured at: 2024-01-08";

        assertEquals(userDetails, user.detailsOfRun("run1"));
    }

    @Test
    public void testDBDeleteRunDetails()
    {
        Run run1 = new Run("run1", 23, 127);
        Run run2 = new Run("run2", 54, 355, LocalDate.of(2020, 11, 10));
        Run run3 = new Run("run3", 18, 3600);

        List<String> runIDs = new ArrayList<>();
        runIDs.add("run1");
        runIDs.add("run2");
        runIDs.add("run3");

        when(data.getRecordIDs()).thenReturn(runIDs);
        when(data.deleteRecord("run2")).thenReturn(true);

        assertTrue(user.removeRun("run2"));
        assertFalse(user.removeRun("run4"));
    }

    @Test
    public void testFilterByDistance() {
        Run run1 = new Run("run1", 7, 3600, LocalDate.of(2022, 5, 14));
        Run run2 = new Run("run2", 11, 3755);
        Run run3 = new Run("run3", 5, 2154);

        List<String> runIDs = new ArrayList<>();
        runIDs.add("run1");
        runIDs.add("run2");
        runIDs.add("run3");

        List<Run> expectedRuns = new ArrayList<>();
        expectedRuns.add(run1);
        expectedRuns.add(run3);

        when(data.getRecordIDs()).thenReturn(runIDs);
        when(data.readRecord("run1")).thenReturn(run1);
        when(data.readRecord("run2")).thenReturn(run2);
        when(data.readRecord("run3")).thenReturn(run3);

        assertEquals(expectedRuns, user.filterByDistance(1, 8));
    }
}
