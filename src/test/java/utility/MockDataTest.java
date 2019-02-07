package utility;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MockDataTest {

    MockData mockData=new MockData();

    @Test
    void retrieveTasks() {

        assertEquals(new ArrayList<>(),mockData.retrieveTaskList());

    }
}