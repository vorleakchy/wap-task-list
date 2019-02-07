package utility;

import db.DAO;
import db.TaskDAO;
import model.*;
import model.Task;

import java.util.ArrayList;
import java.util.List;


/**
 * utility class to return mock data for testing
 *
 * @author kl
 * @since 11/19/2017
 */
public class MockData {

    public ArrayList<User> userList = new ArrayList<>();
    public ArrayList<Task> taskList = new ArrayList<>();

    DAO taskDAO = new TaskDAO();

    public List<Task> retrieveTaskList() {

        return (List<Task>) taskDAO.read();
    }

    /**
     * --Delegation--
     * Adds a single task to the datastore
     * @param task
     */
    public void addTask(Task task) {

        taskDAO.insert(task);

    }

    /**
     * --Delegation--
     * Adds a list of tasks to the datastore
     * @param tasks
     */
    public void addTasks(List<Task> tasks) {

        ((TaskDAO)taskDAO).clear();

        tasks.forEach(task -> addTask(task));

    }


}


