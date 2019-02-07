package db;

import model.Task;
import model.Team;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskDAOTest {

    TaskDAO taskDAO=new TaskDAO();

    @BeforeEach
    void setUp() {


    }

    @Test
    void insert() {

        taskDAO.insert(new Task(2,"Create another task",new Date(2019,02,12).toString(),"Tasks",1,new User(1,"Steven",new Team(1,"green"))));

    }

    @Test
    void read() {
    }

    @Test
    void size() {

        assertEquals(1,taskDAO.size());

    }

    @Test
    void isEmpty() {

        assertEquals(false,taskDAO.isEmpty());
    }

    @Test
    void taskExists() {
    }
}