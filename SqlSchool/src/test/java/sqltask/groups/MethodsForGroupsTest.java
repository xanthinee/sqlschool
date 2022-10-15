package sqltask.groups;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodsForGroupsTest {

    MethodsForGroups testObj = new MethodsForGroups();

    @Test
    void generateGroupName_whenFormatToCompareIsRight_shouldReturnTrue() {

        String generated = testObj.generateGroupName();
        boolean correctness = generated.matches("\\S{2}--\\d{2}");
        assertTrue(correctness);
    }

    @Test
    void generateGroupName_whenFormatToCompareIsFalseWithLetters_shouldReturnFalse() {
        String generated = testObj.generateGroupName();
        boolean correctness = generated.matches("\\s{2}--\\d{2}");
        assertFalse(correctness);
    }

    @Test
    void generateGroupName_whenFormatToCompareIsFalseWithHyphens_shouldReturnFalse() {
        String generated = testObj.generateGroupName();
        boolean correctness = generated.matches("\\S{2}-\\d{2}");
        assertFalse(correctness);
    }

    @Test
    void printGroupTable_whenOneGroup_shouldCorrectlyMakeString() {

        Group testGroup = new Group(1,"AA--11");
        List<Group> testList = List.of(testGroup);
        String expected = "GROUPS:\n" +
                "1 - AA--11";
        assertEquals(expected, testObj.printGroupsTable(testList));
    }

    @Test
    void printGroupTable_whenOneSeveral_shouldCorrectlyMakeString() {

        Group testGroup = new Group(1,"AA--11");
        Group testGroup1 = new Group(10,"AA--11");
        Group testGroup2 = new Group(100,"AA--11");
        Group testGroup3 = new Group(1000,"AA--11");
        List<Group> testList = List.of(testGroup,testGroup1,testGroup2,testGroup3);
        String expected = """
                GROUPS:
                1     - AA--11
                10    - AA--11
                100   - AA--11
                1000  - AA--11""";
        assertEquals(expected, testObj.printGroupsTable(testList));
    }
}