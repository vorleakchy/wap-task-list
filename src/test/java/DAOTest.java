import db.DAO;
import db.TeamDAO;
import model.Team;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DAOTest {

    DAO dao = new TeamDAO();

    public DAOTest() {

    }

    @Test
    public void testInsert() {

        //dao.insert(new Team(5, "TeamEdwin"));

    }

    @Test
    public void testRead() {

        assertEquals(new Team(),dao.read());
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
