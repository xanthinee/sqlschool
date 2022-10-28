package sqltask.helpers;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomFileReaderTest {

    CustomFileReader testObj = new CustomFileReader();
    @Test
    void readFile_whenFileContainsSeveralLines_shouldReturnCorrectStream() {

        List<String> expected = Arrays.asList("test1", "test2", "test3", "test4");
        assertEquals(expected, testObj.readFile("testdata/forCustomFileReader.txt").toList());
    }
}