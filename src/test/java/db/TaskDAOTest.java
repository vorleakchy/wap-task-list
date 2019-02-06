package db;

import model.Task;
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

        taskDAO.insert(new Task(2,"Create a task",new Date(2019,02,12).toString(),"Tasks",1,2));

    }

    @Test
    void read() {
    }

    @Test
    void size() {

        assertEquals(2,taskDAO.size());

    }

    @Test
    void isEmpty() {
    }

    @Test
    void taskExists() {
    }
}