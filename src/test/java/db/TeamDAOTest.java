package db;

import db.DAO;
import db.TeamDAO;
import model.Team;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamDAOTest {

    DAO dao = new TeamDAO();

    public TeamDAOTest() {

    }

    @Test
    public void testInsert() {

        dao.insert(new Team(1, "TeamVorleak"));

    }

    @Test
    public void testRead() {

       // assertEquals(null,dao.read());
    }

    @Test
    public void testIsEmpty() {

        assertEquals(false, new TeamDAO().isEmpty());

    }

    @Test
    void testDAOSize() {

        assertEquals(3, dao.size());
    }

    @Test
    void testTeamExists(){

        assertEquals(true,((TeamDAO)dao).teamExists(new Team(5,"TeamEdwin")));

    }
}
