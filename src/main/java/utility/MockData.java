package utility;

import db.DAO;
import db.TaskDAO;
import db.TeamDAO;
import model.Task;
import model.Team;

import java.util.ArrayList;
import java.util.List;


/**
 * utility class to return mock data for testing
 *
 * @author kl
 * @since 11/19/2017
 */
public class MockData {

    public ArrayList<Task> taskList = new ArrayList<>();
    DAO taskDAO = new TaskDAO();
    DAO teamDAO = new TeamDAO();

    public List<Task> retrieveTaskList() {

        return (List<Task>) taskDAO.read();
    }

    public void addTask(Task task) {

        taskDAO.insert(task);

    }

    public List<Team> retrieveTeams() {

        return (List<Team>) teamDAO.read();

    }

}


