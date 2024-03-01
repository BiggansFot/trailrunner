package se.iths;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

public class TestRuns 
{
    @Mock
    private DatabaseAPI data;

    @BeforeEach
    public void setupAPI()
    {
        data = mock(DatabaseAPI.class);
    }

    //Regular Tests

    @Test
    public void testAverageSpeedOf100KmIn2Hrs()
    {
        Run run1 = new Run("run1", 100, 7200);
        Run run2 = new Run("run2", 200, 5687);

        assertEquals(50, run1.calculateAverageSpeed(), 0.1);
        assertEquals(126.6, run2.calculateAverageSpeed(), 0.1);
        assertEquals(LocalDate.now(), run1.getDate());
    }

    @Test
    public void testUniqueIDOfRuns() {
        Run run1 = new Run("run1", 10, 2500);
        Run run2 = new Run("run2", 10, 2500);

        assertNotEquals(run1.getID(), run2.getID());
    }

    @Test
    public void testMinutePerKilometer() {
        Run run1 = new Run("run1", 10, 2120);
        Run run2 = new Run("run2", 23, 268);

        assertEquals(3.5, run1.averageKilometerTime(), 0.1);
        assertEquals(0.1, run2.averageKilometerTime(), 0.1);
    }

    @Test
    public void testGetDate() {
        Run run1 = new Run("run1", 15, 2350, LocalDate.of(2024, 1, 2));

        assertEquals(LocalDate.of(2024, 1, 2), run1.getDate());
    }

    // DatabaseAPI Mock Tests

//     @Test
//     public void testMockCreateRecordAPI_and_calculateAverageSpeed()
//     {
//         Run calc;
//         data.createRecord("run1", 100, 7200, LocalDate.now());
        
//         calc.calculateAverageSpeed(data.getRecordIDs("run1"));
//     }
}
