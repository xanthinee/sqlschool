package sqltask.helpers;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomFileReaderTest {

    CustomFileReader testObj = new CustomFileReader();
    @Test
    void readFile_whenFileContainsSeveralLines_shouldReturnCorrectStream() {
        String a = "test1";
        String b = "test2";
        String c = "test3";
        String d = "test4";
        Stream<String> testStream = Stream.of(a,b,c,d);
        assertEquals(testStream.toList(), testObj.readFile("testdata/forCustomFileReader.txt").toList());
    }
}