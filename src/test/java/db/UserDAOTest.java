package db;

import db.DAO;
import db.UserDAO ;
import model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDAOTest {
    DAO dao = new UserDAO();

    public UserDAOTest() {

    }

    @Test
    public void testInsert() {

        dao.insert(new User(1, "Edwin",1));
        dao.insert(new User(2, "Steven",1));
        dao.insert(new User(3, "Vorleak",2));
        dao.insert(new User(4, "Chy",2));
        dao.insert(new User(5, "Bernal",5));
        dao.insert(new User(6, "Levi",5));
    }

    @Test
    public void testRead() {

        // assertEquals(null,dao.read());
    }

    @Test
    public void testIsEmpty() {

        assertEquals(false, new UserDAO().isEmpty());

    }

    @Test
    void testDAOSize() {

        assertEquals(3, dao.size());
    }

    @Test
    void testUserExists(){

        assertEquals(true,((UserDAO)dao).userExists(new User(1,"Edwin",1)));

    }
}
