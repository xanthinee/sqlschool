package sqltask.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void stringCentre_whenOddNumOfIndents_shouldReturnCorrectString() {
        String expected = "   a   ";
        assertEquals(expected, Utils.stringCentre("a", 7));
    }

    @Test
    void stringCentre_whenEvenNumOfIndents_shouldReturnCorrectString() {
        String expected = "   a   ";
        assertEquals(expected, Utils.stringCentre("a", 6));
    }
}