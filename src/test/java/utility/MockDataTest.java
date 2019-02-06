package utility;

import db.DAO;
import db.TeamDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockDataTest {

    MockData mockData=new MockData();

    @Test
    void retrieveTeams() {

        assertEquals(null,mockData.retrieveTeams());

    }
}